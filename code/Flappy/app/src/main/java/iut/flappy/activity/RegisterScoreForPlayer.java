package iut.flappy.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import iut.flappy.R;
import iut.flappy.metier.Player;
import iut.flappy.model.PlayerManager;

public class RegisterScoreForPlayer extends AppCompatActivity{

    public static final String BEST_SCORE = "bestScore";
    public static final String SCORE_GAME = "scoreGame";


    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_score_for_player);

        SharedPreferences optionGame = getSharedPreferences(BEST_SCORE, 0);
        SharedPreferences.Editor editor = optionGame.edit();
        editor.putInt(SCORE_GAME, 0);
        editor.commit();


        TextView textViewScore = findViewById(R.id.textViewScore);
        editText = findViewById(R.id.editTextPlayerInput);
        Button button = findViewById(R.id.buttonRegister);
        textViewScore.setText(String.valueOf(getIntent().getIntExtra("score",0)));
    }

    public void validName(View view) {
        //Sauvegarder le score avec le nom
        Player player = new Player(editText.getText().toString(), getIntent().getIntExtra("score", 0));
        try {
            PlayerManager.addPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
