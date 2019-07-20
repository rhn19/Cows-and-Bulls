package com.heliumfarticle.koertyre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    TextView textView, textViewAbout;
    Bundle bundle;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        int key;
        String numP1, numP2;

        textViewAbout = findViewById(R.id.textViewAbout_win);
        button = findViewById(R.id.buttonAbout_win);

        textView = findViewById(R.id.textView_win);
        bundle = getIntent().getExtras();
        key = bundle.getInt("winKey");
        key = key + 1;
        numP1 = bundle.getString("numP1");
        numP2 = bundle.getString("numP2");

        textView.setText("Player " + key + " won!\n\n" + "Player 1 :" + numP1 + "\nPlayer 2 :" + numP2);
    }

    public void exitGame(View view) {
        finishAffinity();
    }

    public void playAgain(View view) {
        Intent intent = new Intent(WinActivity.this,MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void showAbout(View view) {
        button.setVisibility(View.INVISIBLE);
        textViewAbout.setVisibility(View.VISIBLE);
        textViewAbout.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
