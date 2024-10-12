package com.example.todolistfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notes = new ArrayList<>();

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public ArrayList<Note> getNotes() {

        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private OnNoteClickListener onNoteClickListener;

//    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
//        this.onNoteClickListener = onNoteClickListener;
//    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        int colorId;
        switch (note.getPriority()){
            case 0:
                colorId = android.R.color.holo_green_light;
                break;
            case 1:
                colorId = android.R.color.holo_orange_dark;
                break;
            default:
                colorId = android.R.color.holo_red_light;
                break;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorId);
        viewHolder.textView.setBackgroundColor(color);
        viewHolder.textView.setText(note.getText());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickListener != null) {
                    onNoteClickListener.OnNoteClick(note);
                }
            }
        });
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }
    class NotesViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item);
        }
    }
    public interface OnNoteClickListener{
        void OnNoteClick(Note note);
    }
}
