package com.heliumfarticle.koertyre;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class UserInput extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText editText;
    Bundle bundle;
    AlertDialog.Builder builder;
    AlertDialog alert;
    RelativeLayout relativeLayout;

    private String numP1,numP2;
    private int state = 0;
    private int max_length = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        textView = findViewById(R.id.textView_user_input);
        editText = findViewById(R.id.editText_user_input);
        button = findViewById(R.id.button_user_input);

        bundle = getIntent().getExtras();
        max_length = bundle.getInt("length");
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});

        builder = new AlertDialog.Builder(UserInput.this);

        relativeLayout = findViewById(R.id.relative_input);
    }

    //Get NumP1
    public void getStartNum(View view) {
        if(!checkEmpty()) {
           showAlertDialog();
        }

    }

    private void startGame(){
        Intent intent = new Intent(UserInput.this,GameActivity.class);
        intent.putExtra("inputP1",numP1);
        intent.putExtra("inputP2",numP2);
        intent.putExtra("length",max_length);
        startActivity(intent);
        finish();
    }

    private boolean checkEmpty(){
        if(TextUtils.isEmpty(editText.getText().toString())){
            Toast.makeText(UserInput.this,R.string.empty_text,Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    private void showAlertDialog(){
        //Alert Dialog
        builder.setMessage(R.string.player_switch)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        changePlayer();
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                });
        alert = builder.create();
        alert.setTitle(R.string.switch_player_dialog);
        alert.show();

        relativeLayout.setVisibility(View.INVISIBLE);
    }

    private void changePlayer(){
        if(state == 0) {
            numP1 = editText.getText().toString();
            state++;
            textView.setText(R.string.player_two);
            editText.setText("");
            button.setText(R.string.start_button);
        }
        else{
            numP2 = editText.getText().toString();
            startGame();
        }
    }
}
