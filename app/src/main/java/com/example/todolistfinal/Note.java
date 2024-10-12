package com.example.todolistfinal;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private int priority;

    public Note(int id, int priority, String text) {
        this.id = id;
        this.priority = priority;
        this.text = text;
    }



    @Ignore
    public Note(int priority, String text) {
        this(0, priority, text);
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getText() {
        return text;
    }
}
