package iut.flappy.metier;


import android.media.MediaRecorder;

import java.io.IOException;

public class DetectNoise {

    private MediaRecorder mediaRecorder = null;

    public void start() {

        if (mediaRecorder == null) {

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null");

            try {
                mediaRecorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
        }
    }

    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public double getAmplitude() {
        if (mediaRecorder != null){
            //return mediaRecorder.getMaxAmplitude();
            return 20 * Math.log(mediaRecorder.getMaxAmplitude() / 2700.0);
        }
        else{
            return 0;
        }


    }
}
