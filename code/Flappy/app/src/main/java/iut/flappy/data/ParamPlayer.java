package iut.flappy.data;

import android.content.Context;
import android.content.SharedPreferences;

public class ParamPlayer {
    //Variable permettant de gérer les options de déclehements de mouvements en fonction
    //des paramètres données par l'utilisateur dans les options
    private static boolean isTapMod = false, isVoiceMod = false, isShakeMod = false;
    private static int lvlDifficult = 1;
    private static final String GAME_MODE = "gameMode";
    private static final String SHAKE_MOD = "shakeMod";
    private static final String VOICE_MOD = "voiceMod";
    private static final String TAP_MOD = "tapMod";
    private static final String LVL_DIFFICULT = "lvlDifficult";

    public static void loadParam(Context context){
        //Données pour la gestion des paramètres utilisateurs
        SharedPreferences optionGame = context.getSharedPreferences(GAME_MODE, 0);
        isTapMod = optionGame.getBoolean(TAP_MOD, true);
        isVoiceMod = optionGame.getBoolean(VOICE_MOD, false);
        isShakeMod = optionGame.getBoolean(SHAKE_MOD, false);
        lvlDifficult = optionGame.getInt(LVL_DIFFICULT,1);
    }

    public static boolean isTapMod(){
        return isTapMod;
    }

    public static boolean isVoiceMod(){
        return isVoiceMod;
    }

    public static boolean isShakeMod(){
        return isShakeMod;
    }

    public static int getLvlDifficult(){
        return lvlDifficult;
    }

}
