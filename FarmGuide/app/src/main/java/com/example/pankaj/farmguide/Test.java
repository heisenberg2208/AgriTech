package com.example.pankaj.farmguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class Test extends AppCompatActivity {

    EditText etTitle, etDescription, etPriority, etTags;
    Button btnSave, btnLoad, btnUpdate;
    TextView tvResult;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Notebook");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etPriority = (EditText) findViewById(R.id.etPriority);
        etTags = (EditText) findViewById(R.id.etTags);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        tvResult = (TextView) findViewById(R.id.tvResult);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String str_priority = etPriority.getText().toString();
                int priority = 0;
                if (str_priority.length() != 0)
                    priority = Integer.parseInt(str_priority);
                String[] tagArray = etTags.getText().toString().split("\\s*,\\s*");
                List<String> tags = Arrays.asList(tagArray);


                Note note = new Note(title, description, priority, tags);

                collectionReference.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Test.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.whereArrayContains("tags", "tag5")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String documentId = note.getDocumentId();
                            String title = note.getTitle();
                            String description = note.getDescription();
                            int priority = note.getPriority();
                            List<String> tags = note.getTags();

                            data += "ID: " + documentId + "\nTitle: " + title + "\nDescription: " + description + "\nPriority: " + priority + "\n";
                            for (String tag : tags) {
                                data += "-" + tag + "\n";
                            }


                        }

                        tvResult.setText(data);
                    }
                });
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                collectionReference.document("zLZjlWSOwoppOZtcPVtJ")
                        .update("tags", FieldValue.arrayRemove("new_tag"));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//
//                if(e!=null)
//                {
//                    Log.d("Test_Exception",e.toString());
//                    return;
//                }
//                String data = "";
//                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
//                {
//                    Note note = documentSnapshot.toObject(Note.class);
//                    note.setDocumentId(documentSnapshot.getId());
//
//                    String documentId = note.getDocumentId();
//                    String title = note.getTitle();
//                    String description = note.getDescription();
//                    int priority = note.getPriority();
//                    List<String> tags = note.getTags();
//
//                    data += "ID: " + documentId + "\nTitle: " + title + "\nDescription: " + description +"\nPriority: " + priority + "\n\n";
//                    for(String tag: tags)
//                    {
//                        data += "\n-" + tag;
//                    }
//
//                }
//
//                tvResult.setText(data);
//            }
//        });
    }
}
