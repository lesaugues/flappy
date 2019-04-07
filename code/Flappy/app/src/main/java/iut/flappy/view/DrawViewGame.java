package iut.flappy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import iut.flappy.data.TextureLoader;
import iut.flappy.model.GameManager;

public class DrawViewGame extends View implements Observer {

    private GameManager manager;



    public DrawViewGame(Context context, GameManager manager) {
        super(context);
        this.manager = manager;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Dessiner l'arrière plan du jeu
        canvas.drawBitmap(TextureLoader.getBackground(), null, manager.getRect(), null);
        canvas.drawBitmap(TextureLoader.getGround(), null, manager.getRectGround(), null);



        for(int i=0; i < manager.getNumberOfTubes(); i++){
            drawAction(canvas,i,manager.getBirdFrame());
        }

        //Texte affichant le score du joueur
        canvas.drawText(String.valueOf(manager.getScore()),
                manager.getWidth()/8,
                manager.getHeight()/4,manager.getScorePlayerPaint());

        //Si le joueur est mort
        if(manager.isDie()){
            drawAction(canvas, manager.getTubeOfDeath(), 0);
            canvas.drawBitmap(TextureLoader.getGameOver(),
                    manager.getWidth()/2- TextureLoader.getGameOver().getWidth()/2,
                    manager.getHeight()/2- TextureLoader.getGameOver().getHeight()/2,null);
        }

    }

    public void drawAction(Canvas canvas, int position, int birdFrame){
        canvas.drawBitmap(TextureLoader.getTopTube(), manager.getTubeX()[position],
                manager.getTopTubeY()[position] - TextureLoader.getTopTube().getHeight(), null);
        canvas.drawBitmap(TextureLoader.getBottomTube(), manager.getTubeX()[position],
                manager.getTopTubeY()[position] + manager.getPipeGap(), null);
        canvas.drawBitmap(TextureLoader.getBirdsFrame(birdFrame), manager.getBirdPositionX(),
                manager.getBirdPositionY(),null);
    }

    //On appelle cette méthode lorsque le manager a fini ses calculs pour dessiner
    @Override
    public void update(Observable o, Object arg) {
        invalidate();
    }
}
