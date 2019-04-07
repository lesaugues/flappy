package iut.flappy.data;

import android.content.Context;
import android.os.PowerManager;

import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;

public class LockScreen {
    private static PowerManager.WakeLock wakeLock;

    public static void lockScreen(Context context){
        //On bloque le vérouillage de l'écran pour ne pas qu'il ne se vérouille en pleine partie
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        if (pm != null) {
            wakeLock = pm.newWakeLock(SCREEN_DIM_WAKE_LOCK, "NoiseAlert");
        }
        if (!wakeLock.isHeld()) {
            wakeLock.acquire(10*60*1000L /*10 minutes*/);
        }
    }

    public static void unlockScreen(){
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
