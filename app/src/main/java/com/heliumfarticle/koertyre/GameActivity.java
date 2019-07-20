package com.heliumfarticle.koertyre;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    Bundle bundle;
    ScrollView scrollViewP1, scrollViewP2;
    TextView textViewInfo, textViewScrollP1, textViewScrollP2, textViewDisplay;
    EditText editText;
    AlertDialog.Builder builder;
    AlertDialog alert;
    RelativeLayout relativeLayout;

    private String numP1,numP2;
    private int state = 0, count = 0, max_length = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bundle = getIntent().getExtras();
        numP1 = bundle.getString("inputP1");
        numP2 = bundle.getString("inputP2");
        max_length = bundle.getInt("length");

        editText = findViewById(R.id.editText_activity_game);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});

        textViewInfo = findViewById(R.id.textView_activity_game);
        textViewScrollP1 = findViewById(R.id.textViewScrollP1_activity_game);
        textViewScrollP2 = findViewById(R.id.textViewScrollP2_activity_game);

        scrollViewP1 = findViewById(R.id.scrollViewP1_activity_game);
        scrollViewP2 = findViewById(R.id.scrollViewP2_activity_game);
        scrollViewP2.setVisibility(View.INVISIBLE);

        textViewDisplay = findViewById(R.id.textViewDisplayNum_activity_game);
        textViewDisplay.append(numP1);

        builder = new AlertDialog.Builder(GameActivity.this);

        relativeLayout = findViewById(R.id.relative_game);
    }

    //on click Check Button
    public void getGuessNum(View view) {
        String outP1, outP2;
        if(!checkEmpty()) {
            if (state == 0) {
                if (count != 0) {
                    Toast.makeText(GameActivity.this, R.string.count_error, Toast.LENGTH_SHORT).show();
                } else {
                    outP1 = editText.getText().toString();
                    textViewScrollP1.append(outP1 + " : " + checkGuess(numP2, outP1));
                    count++;
                }
            } else if (state == 1) {
                if (count != 0) {
                    Toast.makeText(GameActivity.this, R.string.count_error, Toast.LENGTH_SHORT).show();
                } else {
                    outP2 = editText.getText().toString();
                    textViewScrollP2.append(outP2 + " : " + checkGuess(numP1, outP2));
                    count++;
                }
            }
        }
    }

    //on click End Button
    public void endTurn(View view) {
        if(count!=0) {
            showAlertDialog();
        }
        else
            Toast.makeText(GameActivity.this,R.string.not_played,Toast.LENGTH_SHORT).show();
    }

    private String checkGuess(String original, String guess){
        Map<Character, Integer> map = new HashMap<>();
        int bulls = 0,cows = 0;
        if (original.equals(guess)){
            callWinActivity();
        }
        else {
            for (int i = 0, n = original.length(); i < n; i++) {
                map.put(original.charAt(i), i);
            }
            bulls = 0;
            cows = 0;
            for (int i = 0, n = guess.length(); i < n; i++) {
                if (map.containsKey(guess.charAt(i))) {
                    if (map.get(guess.charAt(i)) == i) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        return bulls + " Bulls & " + cows + " Cows\n";
    }

    private void callWinActivity(){
        Intent intent = new Intent(GameActivity.this,WinActivity.class);
        intent.putExtra("winKey",state);
        intent.putExtra("numP1",numP1);
        intent.putExtra("numP2",numP2);
        startActivity(intent);
        finish();
    }

    private boolean checkEmpty(){
        if(TextUtils.isEmpty(editText.getText().toString())){
            Toast.makeText(GameActivity.this,R.string.empty_text,Toast.LENGTH_SHORT).show();
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
        if (state == 0) {
            scrollViewP1.setVisibility(View.INVISIBLE);
            scrollViewP2.setVisibility(View.VISIBLE);
            textViewInfo.setText(R.string.player_two);
            textViewDisplay.setText(R.string.display_num);
            textViewDisplay.append(numP2);
            editText.setText("");
            count = 0;
            state = 1;
        } else if (state == 1) {
            scrollViewP2.setVisibility(View.INVISIBLE);
            scrollViewP1.setVisibility(View.VISIBLE);
            textViewInfo.setText(R.string.player_one);
            textViewDisplay.setText(R.string.display_num);
            textViewDisplay.append(numP1);
            editText.setText("");
            count = 0;
            state = 0;
        }
    }
}
