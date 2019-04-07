package iut.flappy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import iut.flappy.R;

public class LoseWindow extends AppCompatActivity {

    public static final String BEST_SCORE = "bestScore";
    public static final String SCORE_GAME = "scoreGame";
    private SharedPreferences optionGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_window);
        optionGame = getSharedPreferences(BEST_SCORE, 0);
        int actualScore = getIntent().getIntExtra("score", 0);
        if(optionGame.getInt(SCORE_GAME,0) < actualScore){
            SharedPreferences.Editor editor = optionGame.edit();
            editor.putInt(SCORE_GAME, actualScore);
            editor.commit();
        }
    }

    public void restartGame(View view) {
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();
    }

    public void continueToRegisterScore(View view) {
        Intent intent = new Intent(this, RegisterScoreForPlayer.class);
        intent.putExtra("score",optionGame.getInt(SCORE_GAME,0));
        startActivity(intent);
        finish();
    }
}
