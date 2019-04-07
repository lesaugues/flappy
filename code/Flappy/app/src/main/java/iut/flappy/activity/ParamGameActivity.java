package iut.flappy.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import iut.flappy.R;

public class ParamGameActivity extends AppCompatActivity {


    public static final String GAME_MODE = "gameMode";
    public static final String SHAKE_MOD = "shakeMod";
    public static final String VOICE_MOD = "voiceMod";
    public static final String TAP_MOD = "tapMod";
    public static final String LVL_DIFFICULT = "lvlDifficult";
    private CheckBox checkTapMod, checkVoiceMod, checkShakeMod;

    private RadioButton modeFacile, modeNormal, modeDifficile, modeImpossible;
    private RadioGroup selectDifficult;
    private int lvlDifficult;

    private SharedPreferences optionGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_game);
        optionGame = getSharedPreferences(GAME_MODE, 0);

        checkTapMod = findViewById(R.id.checkBoxTap);
        checkVoiceMod = findViewById(R.id.checkBoxVoiceDetect);
        checkShakeMod = findViewById(R.id.checkBoxShakeDetect);

        modeFacile = findViewById(R.id.buttonEasy);
        modeNormal = findViewById(R.id.buttonNormal);
        modeDifficile = findViewById(R.id.buttonDifficile);
        modeImpossible = findViewById(R.id.buttonImpossible);

        selectDifficult = findViewById(R.id.buttonSelectDifficult);

        whatButtonCheck();

        checkTapMod.setChecked(optionGame.getBoolean(TAP_MOD, true));
        checkVoiceMod.setChecked(optionGame.getBoolean(VOICE_MOD, false));
        checkShakeMod.setChecked(optionGame.getBoolean(SHAKE_MOD, false));


    }


    public void validInformation(View view) {
        SharedPreferences.Editor editor = optionGame.edit();
        editor.putBoolean(TAP_MOD, checkTapMod.isChecked());
        editor.putBoolean(VOICE_MOD, checkVoiceMod.isChecked());
        editor.putBoolean(SHAKE_MOD, checkShakeMod.isChecked());

        defineLvlDifficult();
        editor.putInt(LVL_DIFFICULT, lvlDifficult);
        editor.commit();
        finish();
    }

    private void defineLvlDifficult(){
        switch (selectDifficult.getCheckedRadioButtonId()){
            case R.id.buttonEasy :
                lvlDifficult = 1;
                break;
            case R.id.buttonNormal :
                lvlDifficult = 2;
                break;
            case R.id.buttonDifficile :
                lvlDifficult = 3;
                break;
            case R.id.buttonImpossible:
                lvlDifficult = 4;
        }
    }

    private void whatButtonCheck(){
        switch (optionGame.getInt(LVL_DIFFICULT,0)){
            case 1:
                modeFacile.setChecked(true);
                break;
            case 2 :
                modeNormal.setChecked(true);
                break;
            case 3:
                modeDifficile.setChecked(true);
                break;
            case 4:
                modeImpossible.setChecked(true);
        }
    }
}
