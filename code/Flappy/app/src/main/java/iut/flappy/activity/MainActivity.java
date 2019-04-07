package iut.flappy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import iut.flappy.R;
import iut.flappy.metier.Player;
import iut.flappy.model.PlayerManager;

public class  MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlayerManager.create(this);
        PlayerManager.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void startGame(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }
        else {
            Intent intent = new Intent(this, StartGame.class);
            startActivity(intent);
        }

    }

    public void goParam(View view){
        Intent intent = new Intent(this, ParamGameActivity.class);
        startActivity(intent);
    }

    public void goScore(View view) {
        Intent intent2 = new Intent(this, ScoresActivity.class);
        startActivity(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerManager.close();
    }
}
