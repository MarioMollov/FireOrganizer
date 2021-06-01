package uni.fmi.masters.fireorganizer.ui.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import uni.fmi.masters.fireorganizer.R;

public class EditNoteActivity extends AppCompatActivity {

    Intent data;
    EditText editNoteTitleET, editNoteContentET;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    ProgressBar progressBar;
    String userID, noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        db= FirebaseFirestore.getInstance();

        data = getIntent();
        editNoteTitleET = findViewById(R.id.editNoteTitleET);
        editNoteContentET = findViewById(R.id.editNoteContentEditText);
        progressBar = findViewById(R.id.editNoteProgressBar);

        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");
        noteID = data.getStringExtra("noteID");

        editNoteTitleET.setText(noteTitle);
        editNoteContentET.setText(noteContent);

        FloatingActionButton fab = findViewById(R.id.saveEditedNoteFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nTitle = editNoteTitleET.getText().toString();
                String nContent = editNoteContentET.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(EditNoteActivity.this, "Can not save note with empty field/s", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // save the note
                DocumentReference documentReference = db.collection(AddNoteActivity.FIREBASE_COLLECTION_NOTES).document(userID)
                        .collection(AddNoteActivity.FIREBASE_COLLECTION_MYNOTES).document(noteID);
                Map<String,Object> note = new HashMap<>();
                note.put(AddNoteActivity.FIREBASE_NOTE_TITLE, nTitle);
                note.put(AddNoteActivity.FIREBASE_NOTE_CONTENT, nContent);

                documentReference.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditNoteActivity.this, "Note was changed", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditNoteActivity.this, "Error, Try again", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}