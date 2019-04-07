package iut.flappy.model;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;

import java.util.Observable;
import java.util.Observer;

import iut.flappy.data.TextureLoader;
import iut.flappy.metier.Bird;
import iut.flappy.metier.DetectNoise;
import iut.flappy.metier.DetectShake;
import iut.flappy.metier.Pipe;
import iut.flappy.data.ParamPlayer;

public class GameManager extends Observable implements Observer {
    private static final int GAP = 400;
    private static final int NUMBER_OF_TUBES = 4;
    private static final int TUBE_VELOCITY = 8;
    private static final int NUMBER_OF_FRAME = 2;
    private static final int SON_DETECT = -50;

    //Permet d'avoir les tailles de l'écran et la zone de jeu
    private int width, height;
    private Rect rect;
    private Rect rectGround;

    private int birdFrame = 0;

    //Utilisé pour l'affichage du score du joueur
    private Paint scorePlayerPaint;

    //Variable pour gérer la physique du jeu
    private int velocity = 0;

    //Variable qui permet de gérer l'état du jeu
    private boolean gameState = false;

    //Permet de savoir si le joueur a perdu ou non
    private boolean isDie = false;

    //Position du tuyau où le joueur est mort
    private int tuyau;

    //Score du joueur
    private int score = 0;

    private int groundHeight;

    //Classe métier
    private Pipe pipe;
    private Bird bird;

    //Variable permettant de gérer la sensibilité de la détection du son
    private DetectNoise detectNoise;

    //Variable permettant la détection de la secousse
    private DetectShake detectShake;

    //Variable indiquant la fin de la partie et la redirection vers la page de défaite
    private boolean isLeavingGame = false;


    public GameManager (DetectNoise detectNoise, DetectShake detectShake){
        //On récupère la taille de l'écran
        Display display = TextureLoader.getDisplay();

        //Fixe l'écran de jeu
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;
        groundHeight = height - (height/7);
        rect = new Rect(0,0,width,height);
        rectGround = new Rect(0,groundHeight,width, height);

        //gère la position de l'oiseau sur l'écran
        bird = new Bird(width/2 - TextureLoader.getBirdsFrame(0).getWidth()/2, height/2 - TextureLoader.getBirdsFrame(0).getHeight()/2);


        //on gère les valeurs des tubes et leurs position
        pipe = new Pipe(GAP, NUMBER_OF_TUBES, TUBE_VELOCITY, width*3/4, groundHeight);

        //on génère aléatoirement un nombre pour faire varier la position des tubes
        pipe.generateRandomTubePosition(width);

        //Gère l'affichage du score à l'écran, ici on fixe sa position en fonction de la taille de l'écran
        scorePlayerPaint = new Paint();
        scorePlayerPaint.setTextSize(width/3);


        //Création des données pour la détection du son
        this.detectNoise = detectNoise;
        if(detectNoise != null){
            startDetectNoise();
        }

        //Début des données pour la détection de la secousse du téléphone
        this.detectShake = detectShake;
    }

    public Rect getRect() {
        return rect;
    }

    public Rect getRectGround() {
        return rectGround;
    }

    public int getBirdFrame() {
        return birdFrame;
    }

    public Paint getScorePlayerPaint() {
        return scorePlayerPaint;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public boolean isDie() {
        return isDie;
    }

    public int getTubeOfDeath(){
        return tuyau;
    }

    public int getPipeGap(){
        return pipe.getGap();
    }

    public int getBirdPositionX(){
        return bird.getBirdX();
    }

    public int getBirdPositionY(){
        return bird.getBirdY();
    }

    public int[] getTubeX(){
        return pipe.getTubeX();
    }

    public int[] getTopTubeY(){
        return pipe.getTubeY();
    }

    public int getNumberOfTubes(){
        return pipe.getNumberOfTubes();
    }

    public boolean isLeavingGame(){
        return isLeavingGame;
    }

    public boolean isGameState(){
        return gameState;
    }

    private void actionGame(){
        //On change la frame de l'oiseau
        if(birdFrame == NUMBER_OF_FRAME){
            birdFrame = 0;
        }
        else{
            birdFrame++;
        }

        //On regarde si le son est détecté
        onVoiceDetectEvent();

        //On regarde si le téléphone est secoué
        onShakeDetectEvent();

        if(gameState && !isDie){
            if(bird.getBirdY() < groundHeight - TextureLoader.getBirdsFrame(0).getHeight() || velocity <= 0
            || bird.getBirdY() > height){
                int gravity = 3;
                velocity += gravity; // Get faster when it falls
                bird.addVelocity(velocity);
            }

            for(int i = 0; i< pipe.getNumberOfTubes(); i++){

                pipe.advanceTubeAtPos(i);

                //On avance le tube lorsqu'il sort de l'écran
                if(pipe.getTubeX()[i] < -TextureLoader.getTopTube().getWidth()){
                    pipe.addTubeToAPosition(i);
                    pipe.generateRandomToTubeY(i);
                    pipe.setScoreBoolTubeToPos(i, false);
                }

                //On test si le joueur a passé le tuyau et on ajoute un point
                //Le boolean vérifie si le tuyau a déjà attribué un point au joueur ou non
                if(pipe.getTubeX()[i]+ TextureLoader.getTopTube().getWidth()/2 <= bird.getBirdX() && !pipe.getScoreBoolTube()[i]){
                    score ++;
                    pipe.setScoreBoolTubeToPos(i, true);
                    pipe.increaseDifficult(score);
                }

                //On test la collision que si l'oiseau se trouve entre 2 tuyau
                if(pipe.getTubeX()[i] - TextureLoader.getTopTube().getWidth()/2 <= bird.getBirdX() &&
                        pipe.getTubeX()[i]+ TextureLoader.getTopTube().getWidth()>= bird.getBirdX()){
                    if(testCollision(i)){
                        gameState = false;
                        isDie = true;
                        tuyau = i;
                    }
                }
            }
        }
        else if (!gameState && isDie){
            bird.birdDie();
            finishGame();
        }
        //On avertit les classes qui observent le manager
        setChanged();
        notifyObservers();
    }

    //Méthode qui permet de tester les collisions entre l'oiseau et les tuyaux
    private boolean testCollision(int pos){
        return bird.getBirdY() <= pipe.getTubeY()[pos] ||
                bird.getBirdY() >= pipe.getGap() + pipe.getTubeY()[pos] - TextureLoader.getBirdsFrame(birdFrame).getHeight();
    }


    //Méthode appellée lors de la détection d'un tap sur l'écran
    public void onTouchEvent(MotionEvent event){
        if(ParamPlayer.isTapMod()){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN && !isDie){ //Tap détecté
                birdUp();
            }
        }
    }

    private void onVoiceDetectEvent(){
        if(ParamPlayer.isVoiceMod() && !isDie && detectNoise != null){
            double checkSon = detectNoise.getAmplitude();

            //On regarde si le un son detecté est assez fort pour faire bouger l'oiseau
            if(checkSon > SON_DETECT){
                birdUp();
            }
        }
    }

    private void onShakeDetectEvent(){
        if(ParamPlayer.isShakeMod() && !isDie && detectShake != null){
            if (detectShake.isShake()){
                birdUp();
            }
        }
    }


    //Méthode appellée à chaque nouvelle boucle
    @Override
    public void update(Observable o, Object arg) {
        if(!isLeavingGame){
            actionGame();
        }
    }

    //Fonction pour la mise en place du son
    private void startDetectNoise() {
        stopDetectNoise();
        detectNoise.start();

    }
    public void stopDetectNoise() {
        detectNoise.stop();
    }

    private void birdUp(){
        //On fait monter l'oiseau et on démarre le jeu
        velocity = -30;
        gameState = true;
    }

    private void finishGame(){
        if(bird.getBirdY() > height && !isLeavingGame){
            playerLeaveGame();
        }
    }

    public void playerLeaveGame(){
        isLeavingGame = true;
    }

}
