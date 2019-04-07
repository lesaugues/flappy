package iut.flappy.metier;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DetectShake implements SensorEventListener {

    private static final int DETECT_SHAKE_VALUE = 4;
    private float z = 0;
    private boolean shake = false;

    public DetectShake (Context context){
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        shake = Math.abs((z - event.values[2])) > DETECT_SHAKE_VALUE;
        z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isShake(){
        return shake;
    }

}
