package iut.flappy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import java.util.Observable;
import java.util.Observer;

import iut.flappy.data.TextureLoader;
import iut.flappy.data.LockScreen;
import iut.flappy.metier.DetectNoise;
import iut.flappy.metier.DetectShake;
import iut.flappy.view.DrawViewGame;
import iut.flappy.model.GameLoop;
import iut.flappy.model.GameManager;
import iut.flappy.data.ParamPlayer;

public class StartGame extends Activity implements Observer {

    private GameManager manager;
    private DrawViewGame drawViewGame;
    private GameLoop gameLoop;

    private DetectShake detectShake = null;
    private DetectNoise detectNoise = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ParamPlayer.loadParam(this);
        TextureLoader.load(this);
        createModGame();

        manager = new GameManager(detectNoise,detectShake);
        drawViewGame = new DrawViewGame(this,manager);
        manager.addObserver(drawViewGame);
        manager.addObserver(this);
        gameLoop = new GameLoop();
        gameLoop.addObserver(manager);
        gameLoop.startLoop();
        LockScreen.lockScreen(this);
        setContentView(drawViewGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(manager.isGameState() || manager.isDie()){
            finish();
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        //On libère le maintient de l'écran allumé
        LockScreen.unlockScreen();
        if(ParamPlayer.isVoiceMod() && detectNoise != null){
            manager.stopDetectNoise();
        }
        manager.playerLeaveGame();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(manager.isLeavingGame()){
            goToScoreWindow();
        }
    }

    //Détection d'un tap sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(ParamPlayer.isTapMod()){
            manager.onTouchEvent(event);
        }
        return true;
    }

    private void createModGame(){
        //On instancie les classes uniquement si le joueur a choisi ces manières de jouer
        if(ParamPlayer.isShakeMod()){
            detectShake = new DetectShake(this);
        }
        if (ParamPlayer.isVoiceMod()){
            detectNoise = new DetectNoise();
        }
    }

    private void goToScoreWindow(){
        finish();
        Intent intent = new Intent(this, LoseWindow.class);
        intent.putExtra("score", manager.getScore());
        startActivity(intent);
    }
}
