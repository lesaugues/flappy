package iut.flappy.metier;

import java.util.Random;

import iut.flappy.data.ParamPlayer;

public class Pipe {
    private int gap; //gap between 2 tubes
    private int minTubeOffset, maxTubeOffset;
    private int numberOfTubes;
    private int distanceBetweenTubes;
    private int [] tubeX;
    private int [] tubeY;
    private boolean [] tubeScore;
    private int tubeVelocity;
    private Random random;

    public Pipe(int gap, int numberOfTubes, int tubeVelocity, int distanceBetweenTubes, int heightDisplay){
        this.gap = gap;
        this.numberOfTubes = numberOfTubes;
        tubeX = new int[numberOfTubes];
        tubeY = new  int[numberOfTubes];
        tubeScore = new boolean[numberOfTubes];
        this.tubeVelocity = tubeVelocity;
        this.distanceBetweenTubes = distanceBetweenTubes;
        minTubeOffset = gap/2;
        maxTubeOffset = heightDisplay - minTubeOffset - gap;
        random = new Random();



    }

    public int getGap() {
        return gap;
    }

    public int[] getTubeX() {
        return tubeX;
    }

    public int[] getTubeY() {
        return tubeY;
    }

    public int getNumberOfTubes() {
        return numberOfTubes;
    }

    public void generateRandomTubePosition(int widthDisplay){
        //on génère aléatoirement un nombre pour faire varier la position des tubes
        random = new Random();
        for(int i = 0; i < numberOfTubes; i++){
            tubeX[i] = widthDisplay + i*distanceBetweenTubes;
            generateRandomToTubeY(i);
        }
    }

    public void generateRandomToTubeY(int pos){
        tubeY[pos] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset);
    }

    public void addTubeToAPosition(int pos){
        tubeX[pos] += numberOfTubes * distanceBetweenTubes;
    }

    public void advanceTubeAtPos(int pos){
        tubeX[pos] -= tubeVelocity;
    }

    public boolean[] getScoreBoolTube (){
        return tubeScore;
    }

    public void setScoreBoolTubeToPos(int pos, boolean val){
        tubeScore[pos] = val;
    }

    public void increaseDifficult(int score){
        switch (ParamPlayer.getLvlDifficult()){
            case 1:
                if(score % 7 == 0){
                    tubeVelocity++;
                }
                break;
            case 2:
                if(score % 5 == 0){
                    tubeVelocity++;
                }
                break;
            case 3:
                if(score % 3 == 0){
                    tubeVelocity++;
                }
                break;
            case 4:
                tubeVelocity++;
                distanceBetweenTubes += 10;
        }

    }

}
