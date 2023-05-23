package com.example.notesapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.Utility;
import com.example.notesapp.model.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class AddNoteActivity extends AppCompatActivity {
    private ImageView image_addNote;
    private EditText editText_TitleNote, editText_ContentNote;
    private TextView textAddNote, textView_deleteNote;
    private Boolean checkEditNote = false;
    String tilte, content, docId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        image_addNote = findViewById(R.id.image_addNote);
        editText_TitleNote = findViewById(R.id.editText_TitleNote);
        editText_ContentNote = findViewById(R.id.editText_ContentNote);
        textAddNote = findViewById(R.id.textAddNote);
        textView_deleteNote = findViewById(R.id.textView_deleteNote);

        tilte = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (tilte != null && !docId.isEmpty()){
            checkEditNote = true;
            textAddNote.setText("Edit Your Note");
            textView_deleteNote.setVisibility(View.VISIBLE);
            editText_TitleNote.setText(tilte);
            editText_ContentNote.setText(content);
        }

        textView_deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });
        image_addNote.setOnClickListener(v -> {
            if (editText_TitleNote.getText().toString().trim().isEmpty()){
                Toast.makeText(AddNoteActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();

            }
            else {
                Note note = new Note();
                note.setTitle(editText_TitleNote.getText().toString());
                note.setContent(editText_ContentNote.getText().toString());
                note.setTimestamp(Timestamp.now());
                saveAndUpdateNote(note);
            }
        });

    }

    private  void deleteNote() {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(AddNoteActivity.this, "Delete Your Note Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(AddNoteActivity.this, "Delete Your Note Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AddNoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void saveAndUpdateNote(Note note) {

        DocumentReference documentReference;
        if (checkEditNote){
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }
        else {
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(AddNoteActivity.this, "Add Your Note Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(AddNoteActivity.this, "Add Your Note Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}