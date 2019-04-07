package iut.flappy.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iut.flappy.metier.MySQLite;
import iut.flappy.metier.Player;

public class PlayerManager implements BaseColumns {

    private static final String TABLE_NAME = "Player";
    private static final String KEY_ID_PLAYER = "id_player";
    private static final String KEY_NAME_PLAYER = "name";
    private static final String KEY_SCORE_PLAYER = "score";

    public static final String CREATE_TABLE_PLAYER ="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
            " (" + KEY_ID_PLAYER + " INTEGER primary key," +
            KEY_NAME_PLAYER + " TEXT," +
            KEY_SCORE_PLAYER + " INTEGER" +
            ");";

    private static MySQLite mySqlDatabase;
    private static SQLiteDatabase db;

    public static void create(Context context){
        mySqlDatabase = MySQLite.getInstance(context);
    }

    public static void open(){
        db = mySqlDatabase.getWritableDatabase();
    }

    public static void close(){
        db.close();
    }

    public static void addPlayer(Player p)throws Exception{

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_PLAYER,p.getName());
        values.put(KEY_SCORE_PLAYER, p.getScore());
        int i=(int) db.insert(TABLE_NAME,null,values);
        if (i == -1) {
            throw new Exception("Exception while inserting score");
        }
    }

    public static Cursor getPlayers(){
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }


}
