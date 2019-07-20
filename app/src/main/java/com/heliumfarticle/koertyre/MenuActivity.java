package com.heliumfarticle.koertyre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    Spinner spinner;
    int max_length;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button = findViewById(R.id.buttonInstruction_menu);
        textView = findViewById(R.id.textViewInstruction_menu);

        spinner = findViewById(R.id.spinner_menu);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               max_length = position + 4;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MenuActivity.this,R.string.no_selection,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDifficulty(View view) {
        Intent intent = new Intent(MenuActivity.this,UserInput.class);
        intent.putExtra("length",max_length);
        startActivity(intent);
        finish();
    }

    public void showInstructions(View view) {
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
    }
}
