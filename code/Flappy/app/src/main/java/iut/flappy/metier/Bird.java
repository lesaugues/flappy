package iut.flappy.metier;

public class Bird {
    private int birdX, birdY;

    public Bird(int birdX, int birdY){
        this.birdX = birdX;
        this.birdY = birdY;
    }

    public int getBirdY() {
        return birdY;
    }

    public int getBirdX() {
        return birdX;
    }

    public void addVelocity(int val){
        birdY += val;
    }

    public int birdDie(){
        return birdY += 25;
    }


}
