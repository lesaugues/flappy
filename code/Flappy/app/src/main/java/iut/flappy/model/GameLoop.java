package iut.flappy.model;

import android.os.Handler;

import java.util.Observable;

public class GameLoop extends Observable {

    private static final int UPDATE_MILLIS = 30;

    private Handler handler;
    private Runnable loop;


    public GameLoop (){
        //on crée le handler qui servira à envoyer le runnable
        handler = new Handler();
        loop = new Runnable() {
            @Override
            public void run() {
                //On préviens le manager qu'il faut relancer la boucle
                setChanged();
                notifyObservers();

                //On renvoie le runnable après un temps donné
                handler.postDelayed(loop, UPDATE_MILLIS);
            }
        };
    }

    //Appelé dans le StartGame pour démarrer la partie
    public void startLoop (){
        handler.postDelayed(loop, UPDATE_MILLIS);
    }



}
