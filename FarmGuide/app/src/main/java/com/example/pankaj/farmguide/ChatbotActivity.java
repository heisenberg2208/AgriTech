package com.example.pankaj.farmguide;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Locale;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIService;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ChatbotActivity extends AppCompatActivity implements AIListener {
    EditText editText;
    ImageView addBtn;
    MessageListAdapter adapter;
    AIService aiService;
    ListView messageListView;
    ArrayList<FriendlyMessage> arrayList;
    String message,lang;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        int permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 105);


        }
        SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        lang = sharedPreferences.getString("lang","");



        messageListView = findViewById(R.id.CBmessageListView);

        editText = (EditText) findViewById(R.id.CBeditText1);
        addBtn = findViewById(R.id.CBfab_img);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        arrayList = new ArrayList<>();
        adapter = new  MessageListAdapter(this, R.layout.card_view, arrayList);
        messageListView.setAdapter(adapter);
// Texttospeech code
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    if(!lang.equals("en"))
                        textToSpeech.setLanguage(new Locale("hin"));
                    else
                        textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

//Dialog FLow code
        final AIConfiguration config = new AIConfiguration("b2535e749e1c435c94ef62d1ec87ae84", AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);


        aiService = AIService.getService(this, config);
        aiService.setListener(this);


        final AIDataService aiDataService = new AIDataService(this,config);

        final AIRequest aiRequest = new AIRequest();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                message = editText.getText().toString().trim();

                if (!message.equals("")) {
                    //user is typing
                    FriendlyMessage cmessage;
                    //Translation Code
                    if(lang.equals("hi")) {
                        // if not english translate

                        cmessage = new FriendlyMessage(message, "Me",null);
                        message = Translate.translate("hi", "en", message, getApplicationContext());

                        Toast.makeText(ChatbotActivity.this, message, Toast.LENGTH_SHORT).show();

                    }
                    else if(lang.equals("mr"))
                    {
                        cmessage = new FriendlyMessage(message, "Me",null);
                        message = Translate.translate("mr", "en", message, getApplicationContext());

                        Toast.makeText(ChatbotActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    else if(lang.equals("pa"))
                    {
                        cmessage = new FriendlyMessage(message, "Me",null);
                        message = Translate.translate("pa", "en", message, getApplicationContext());

                        Toast.makeText(ChatbotActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        cmessage = new FriendlyMessage(message,"Me",null);
                    }
                  //  cmessage = new FriendlyMessage(message,"User",null);
                    aiRequest.setQuery(message);

                    adapter.add(cmessage);
                    // adapter.notifyDataSetChanged();

                    Log.d("Message", message);
                    new AsyncTask<AIRequest, Void, AIResponse>() {

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                Result result = response.getResult();
                                String reply = result.getFulfillment().getSpeech();
                                if (reply.equals("") || reply.isEmpty()) {
                                    Toast.makeText(ChatbotActivity.this, "No Response", Toast.LENGTH_SHORT).show();



                                } else {
                                    //Translation Code
                                    if(lang.equals("hi"))
                                    {
                                        reply = Translate.translate("en","hi",reply,getApplicationContext());
                                    }
                                    if(lang.equals("mr"))
                                    {
                                        reply = Translate.translate("en","mr",reply,getApplicationContext());
                                    }
                                    if(lang.equals("pa"))
                                    {
                                        reply = Translate.translate("en","pa",reply,getApplicationContext());
                                    }
                                    FriendlyMessage message = new FriendlyMessage(reply, "bot",null);
                                    adapter.add(message);
                                    //adapter.notifyDataSetChanged();
                                    textToSpeech.speak(message.getText(), TextToSpeech.QUEUE_FLUSH, null,null);
                                    // recyclerView.scrollToPosition(arrayList.size() - 1);
                                }
                            }
                        }
                    }.execute(aiRequest);


                } else {
                    //ai service nhi google se lenge
                    //aiService.startListening();
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("hi", "IN"));

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 11);
                    } else {
                        Toast.makeText(ChatbotActivity.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                    }


                }

                editText.setText("");


            }
        });


    }

    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();

        String message = result.getResolvedQuery();

        FriendlyMessage chatMessage0 = new FriendlyMessage(message, "Me",null);
        adapter.add(chatMessage0);
        // ref.child("chat").push().setValue(chatMessage0);
        //adapter.notifyDataSetChanged();

        String reply = result.getFulfillment().getSpeech();
        if (reply.equals("") || reply.isEmpty()) {
            Toast.makeText(ChatbotActivity.this, "No Response", Toast.LENGTH_SHORT).show();


//            checkQnA(message);
        } else {
            FriendlyMessage chatMessage = new FriendlyMessage(reply, "bot",null);
            textToSpeech.speak(chatMessage.getText(), TextToSpeech.QUEUE_FLUSH, null,null);
            adapter.add(chatMessage);
        }
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("sst", result.get(0));
                    editText.setText(result.get(0));
                }
                break;
        }

    }


}
