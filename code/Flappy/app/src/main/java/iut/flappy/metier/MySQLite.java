package iut.flappy.metier;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iut.flappy.model.PlayerManager;

public class MySQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context){
        if(sInstance ==  null) { sInstance = new MySQLite(context); }
        return sInstance;
    }

    private MySQLite(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlayerManager.CREATE_TABLE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
