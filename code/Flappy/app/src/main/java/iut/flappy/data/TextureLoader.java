package iut.flappy.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;

import iut.flappy.R;

public class TextureLoader {
    private static Display display;

    //Bitmap utilisé pour affiché l'image à l'écran
    private  static Bitmap background, ground;
    private  static Bitmap topTube, bottomTube;
    private  static Bitmap gameOver;
    private  static Bitmap[] birds;

    public static Display getDisplay() {
        return display;
    }

    public static Bitmap getBackground() {
        return background;
    }

    public static Bitmap getGround() {
        return ground;
    }

    public static Bitmap getTopTube() {
        return topTube;
    }

    public static Bitmap getBottomTube() {
        return bottomTube;
    }

    public static Bitmap getGameOver() {
        return gameOver;
    }

    public static Bitmap getBirdsFrame(int frame) {
        return birds[frame];
    }

    public static void load (Context context){
        //On récupère les images et on les charge pour pouvoir les dessiner
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.paysage);
        ground = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground_game);
        topTube = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuyau_haut);
        bottomTube = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuyau);
        gameOver = BitmapFactory.decodeResource(context.getResources(),R.drawable.gameover);
        display = ((Activity) context).getWindowManager().getDefaultDisplay();

//        //gère les animations de l'oiseau
        birds = new Bitmap[3];
        birds[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oiseau1);
        birds[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oiseau2);
        birds[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oiseau3);
    }

}
