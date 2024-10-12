package com.example.todolistfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private Button addNoteButton;

    private EditText editText;

    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;

    private AddNoteViewModel addNoteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();





        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldClose().observe(
                this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            finish();
                        }
                    }
                }
        );
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNote();
            }
        });
    }
    private void initViews(){
        addNoteButton = findViewById(R.id.AddNoteButton);

        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonLow.setChecked(true);

        editText = findViewById(R.id.typeNoteEditText);

    }
    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }
    private void setNote(){
        String text = editText.getText().toString().trim();
        if (text.isEmpty()){
            Toast.makeText(this, "Please, type a note", Toast.LENGTH_SHORT).show();
        }
        int priority = getPriority();
        Note note = new Note(priority, text);
        addNoteViewModel.add(note);


    }
    private int getPriority(){
        if (radioButtonLow.isChecked()) return 0;
        if (radioButtonMedium.isChecked()) return 1;
        else return 2;

    }
}


















