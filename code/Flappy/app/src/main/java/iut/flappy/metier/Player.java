package iut.flappy.metier;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int score;

    public Player (String name, int score){
        this.name=name;
        this.score=score;
    }


    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }


    public void setName(String name){
        this.name=name;
    }



}
