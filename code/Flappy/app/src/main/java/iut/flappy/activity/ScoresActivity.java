package iut.flappy.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import iut.flappy.R;
import iut.flappy.metier.Player;
import iut.flappy.model.PlayerManager;
import iut.flappy.view.PlayerAdapter;

public class ScoresActivity extends AppCompatActivity {

    private static final String KEY_NAME_PLAYER = "name";
    private static final String KEY_SCORE_PLAYER = "score";

    private ListView listScores;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        listScores=findViewById(R.id.listScores);
        placeScore();
    }

    private void placeScore(){
        Cursor cursor = PlayerManager.getPlayers();
        List<Player> playerList = new ArrayList<Player>();
        while (cursor.moveToNext()){
            playerList.add(new Player(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME_PLAYER)),
                                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE_PLAYER))));
        }

        //On trie la liste de joueur selon le score
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o2.getScore()-o1.getScore();
            }
        });

        //On ne prend que les 10 meilleurs scores
        if(playerList.size() > 10){
            playerList = playerList.subList(0,10);
        }

        PlayerAdapter adapter = new PlayerAdapter(this, R.layout.player_list_view_item,playerList);
        listScores.setAdapter(adapter);

    }

}
