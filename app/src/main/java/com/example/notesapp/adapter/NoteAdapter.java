package com.example.notesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.Utility;
import com.example.notesapp.activity.AddNoteActivity;
import com.example.notesapp.model.Note;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.textView_titleItem.setText(note.getTitle());
        holder.textView_contentItem.setText(note.getContent());
        holder.textView_timeStamp.setText(Utility.timeStampToString(note.getTimestamp()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddNoteActivity.class);
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView textView_titleItem, textView_contentItem, textView_timeStamp;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_titleItem = itemView.findViewById(R.id.textView_titleItem);
            textView_contentItem = itemView.findViewById(R.id.textView_contentItem);
            textView_timeStamp = itemView.findViewById(R.id.textView_timeStamp);
        }
    }
}
