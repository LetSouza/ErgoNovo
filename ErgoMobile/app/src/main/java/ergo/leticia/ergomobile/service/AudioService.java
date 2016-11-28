package ergo.leticia.ergomobile.service;

import android.app.Service;
import android.content.Intent;

import android.media.MediaRecorder;

import android.os.Environment;
import android.os.IBinder;

import android.util.Log;

import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioService extends Service {

    private static final int MEDIA_TYPE_AUDIO = 1;

    private MediaRecorder myAudioRecorder;
    private static String outputFile = null;
    private boolean mRecordingStatus;


    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mRecordingStatus == false)
            startRecordAudio();

        return 0;
    }

    public void startRecordAudio(){
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_AUDIO).toString());

        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        if (testeUsabilidade) {
//            Intent intent = new Intent(TesteAudioActivity.this, BackgroundService.class);
//            intent.putExtra("testeUsabilidade", false);
//            startService(intent);
//            finish();
//        }

        Toast.makeText(getApplicationContext(), "A gravação começou", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onDestroy() {
        stopRecordAudio();
        mRecordingStatus = false;

        super.onDestroy();
    }
    public void stopRecordAudio(){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        Toast.makeText(getApplication(), "Áudio gravado com sucesso", Toast.LENGTH_LONG).show();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static String getOutputFile(){
        return outputFile;
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory("ErgoMobile"), "ErgoMobile-Audio");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Meus Audios", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_AUDIO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "AUD_TEST_" + timeStamp + ".3gp");
        } else {
            return null;
        }
        outputFile = mediaFile.getPath();
        return mediaFile;
    }

}