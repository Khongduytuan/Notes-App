package com.example.notesapp;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.index.qual.PolyUpperBound;

import java.text.SimpleDateFormat;

public class Utility {
    public static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(user.getUid())
                .collection("my_notes");
    }

    public static String timeStampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy")
                .format(timestamp.toDate());
    }
}
