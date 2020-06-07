package com.example.pankaj.farmguide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PhotoPicker extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int RC_PHOTO_PICKER = 101;
    Button btnFromCamera;
    FloatingActionButton fabbtnFromGallery,fabDislike,fabLike,fabOpenshops;
    TextView tvPrediction, tvPredictionSolution;
    private ProgressDialog progressDialog;
    ImageView imgViewDisCrop;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("DiseaseCase");

    String lang;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);


        imgViewDisCrop = (ImageView) findViewById(R.id.imgViewDisCrop);
        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference().child("plant_photos");
        fabOpenshops = (FloatingActionButton) findViewById(R.id.fabOpenshops);

        fabOpenshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PhotoPicker.this, "Going to ask location", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(PhotoPicker.this, OpenShopActivity.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        String crop = i.getStringExtra("crop");
        if(crop!=null)
            Log.d("crop", crop);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "I don't have camera permission", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            }

        }


        //btnFromCamera = (Button) findViewById(R.id.btnFromCamera);
        fabbtnFromGallery = (FloatingActionButton) findViewById(R.id.fabbtnFromGallery);
        fabDislike = (FloatingActionButton) findViewById(R.id.fabDislike);
        fabLike = (FloatingActionButton)findViewById(R.id.fabLike);
        tvPrediction = (TextView) findViewById(R.id.tvPrediction);
        tvPredictionSolution = (TextView) findViewById(R.id.tvPredictionSolution);

//        btnFromCamera.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////
//
//            }
//        });
        fabLike.setEnabled(false);

        fabDislike.setEnabled(false);


        fabbtnFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "complete action using"), RC_PHOTO_PICKER);
            }
        });

        fabDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabLike.setEnabled(false);
                Toast.makeText(PhotoPicker.this, "Your response has been recorded. We will try to improve results.", Toast.LENGTH_SHORT).show();
            }
        });
        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabDislike.setEnabled(true);
                Toast.makeText(PhotoPicker.this, "Nice. Results seems promising. :)", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == MY_CAMERA_REQUEST_CODE) {


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                //Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {

            progressDialog.setMessage("Uploading Image....");
            progressDialog.show();

            Uri imageUri = data.getData();
            Log.d("Image_url", String.valueOf(imageUri));

            final StorageReference photoReference = storageReference.child(imageUri.getLastPathSegment());
            Log.d("Image_url", photoReference.toString());

            //upload file to firebase
            photoReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            progressDialog.dismiss();
                            Toast.makeText(PhotoPicker.this, "Upload Finished", Toast.LENGTH_SHORT).show();
                            Log.d("Image_url", uri.toString());
                            final String image_url = uri.toString();

                            Glide.with(PhotoPicker.this)
                                    .load(image_url)
                                    .into(imgViewDisCrop);


                            String URL = new String("http://farmguide.eastus.cloudapp.azure.com:5000/predict");
                            JSONObject json = new JSONObject();
                            try {
                                //String image_url = "https://firebasestorage.googleapis.com/v0/b/fir-authui-9c7da.appspot.com/o/crop_photo%2F1b32ba52-1a78-4030-9317-ea5d2ea66cd7___Mary_HL%209232.JPG?alt=media&token=23888b0b-7c5b-48fe-97b7-f3d357203e9e";
                                json.put("image_url", image_url);
                                Log.d("Response", json.toString());
                            } catch (JSONException e) {

                                Log.d("Response", e.toString());
                            }
                            RequestQueue requestQueue = Volley.newRequestQueue(PhotoPicker.this);
                            JsonObjectRequest objectRequest = new JsonObjectRequest(
                                    Request.Method.POST,
                                    URL,
                                    json,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            Log.d("Response_success", response.toString());

                                            JSONObject mainObject = null;
                                            try {
                                                mainObject = new JSONObject(response.toString());

                                                Log.d("Response_mainObject",mainObject.toString());

                                                String prediction = mainObject.getString("prediction");
                                                String measures = mainObject.getString("measures");
                                                Log.d("Response_success", prediction);
                                                SharedPreferences sharedPreferences1 = getSharedPreferences("Mypref", MODE_PRIVATE);
                                                lang = sharedPreferences1.getString("lang","");
                                                if(!lang.equals("en"))
                                                {
                                                    prediction = Translate.translate("en","hi",prediction,getApplicationContext());
                                                    measures = Translate.translate("en","hi",measures,getApplicationContext());
                                                    tvPrediction.setText(prediction);
                                                    tvPredictionSolution.setText(measures);
                                                }
                                                else {
                                                    tvPrediction.setText(prediction);
                                                    tvPredictionSolution.setText(measures);
                                                }

                                                fabLike.setEnabled(true);
                                                fabDislike.setEnabled(true);

                                                SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
                                                String latitude = sharedPreferences.getString("latitude","");
                                                String longitude = sharedPreferences.getString("longitude","");
                                                String user_id = sharedPreferences.getString("userid","");

                                                DiseaseCase diseaseCase = new DiseaseCase(user_id,latitude,longitude,prediction,image_url);
                                                collectionReference.add(diseaseCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {

                                                        Log.d("DiseaseCaseUpload","success");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("DiseaseCaseUpload","failure");

                                                    }
                                                });



                                            } catch (JSONException e) {
                                                Log.d("Response_success", e.toString());
                                            }


                                            //Toast.makeText(PhotoPicker.this, response.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            tvPrediction.setText(error.toString());
                                            Log.d("Response", error.toString());

                                        }
                                    }

                            );
                            requestQueue.add(objectRequest);

                        }
                    });

                }
            });
        }


    }
}
