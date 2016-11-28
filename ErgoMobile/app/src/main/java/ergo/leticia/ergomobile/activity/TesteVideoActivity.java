package ergo.leticia.ergomobile.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ergo.leticia.ergomobile.surfaceview.CameraPreview;
import ergo.leticia.ergomobile.R;

public class TesteVideoActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = "CameraRecorderActivity";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mMediaRecorder;
    private Button mOk;


    /**
     * Called when the activity is first created.
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_video);
        // Cria instancia da camera
        mCamera = getCameraInstance();


        // Cria vista de pre-visualizacao
        mPreview = new CameraPreview(this, mCamera);
        mCamera.setDisplayOrientation(90);


        int i = R.id.camera_preview;
        Object o = this.findViewById(i);
        FrameLayout preview = (FrameLayout) o;
        preview.addView(mPreview);

        mOk = (Button) findViewById(R.id.button4);

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       /* mGravar = (Button) findViewById(R.id.btnVideo);
        mParar = (Button) findViewById(R.id.btnVideo2);




        mGravar.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           prepareVideoRecorder();
                                           mMediaRecorder.start();
                                           Toast.makeText(getApplicationContext(), "A gravação começou", Toast.LENGTH_LONG).show();

                                          *//* if (isRecording) {
                                               // Camera parar a gravacao e lancamento
                                               mMediaRecorder.stop();  // parar a gravacao
                                               releaseMediaRecorder(); // liberar o objeto MediaRecorder
                                               mCamera.lock();         // levar o acesso a camera de volta ao MediaRecorder


                                               setCaptureButtonText("Gravar Vídeo");
                                               isRecording = false;
                                           } else {
                                               // inicializa camera de video
                                               if (prepareVideoRecorder()) {
                                                   // Camera esta disponivel e desbloqueada, MediaRecorder esta preparado
                                                   // pode iniciar gravacao
                                                   mMediaRecorder.start();
                                                   setCaptureButtonText("Finalizar gravação");
                                                   isRecording = true;

                                               } else {
                                                   // prepare nao funcionou, libera a camera
                                                   releaseMediaRecorder();


                                               }
                                           }*//*
                                       }

                                   }

        );

        mParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaRecorder.stop();
                releaseMediaRecorder();
                mCamera.lock();
                finish();
            }
        });*/

/*
        getActionBar().setDisplayHomeAsUpEnabled(true);
*/
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // possui camera
            return true;
        } else {

            return false;
        }
    }


   /* public void setCaptureButtonText(String s) {

        mGravar.setText(s);
    }
*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // A superficie foi criada, agora diga a camera onde tracar a pré-visualizacao
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }


   /* private boolean prepareVideoRecorder() {
        // mCamera = getCameraInstance();
        mMediaRecorder = new MediaRecorder();
        // Desbloquear e definir camera para o MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        // Configurar fontes
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        //mMediaRecorder.setOutputFile("CartaoSD/videocaptureteste.mp4");

        //Definir arquivo de saida
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
        //mMediaRecorder.setOutputFile("/sdcard/XT1025/meuvideo");
        // Definir a saida de preview
        mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());
        //  MediaRecorder preparado e configurado
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {

   /* Log.i("onClick", "BEGIN");
    if(!recording) {
        recording = startRecording();
    } else {
        stopRecording();
        recording = false;
    }
    Log.i("onClick", "END");

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // liberar MediaRecorder
        releaseCamera();              // liberar a camera
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // configuracao do gravador limpa
            mMediaRecorder.release(); // libera obj gravador
            mMediaRecorder = null;
            mCamera.lock();           // camera bloqueio para uso posterior
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // liberar camera p/ outras aplicacoes
            mCamera = null;
        }
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    /**
     * Create a File for saving an image or video
     */
   /* private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ErgoMobile");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ErgoMobile", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }*/

    private Camera getCameraInstance() {
        Camera c = null;

        try {
            c = open(); // obter instancia de camera

        } catch (Exception e) {
            // Camera nao esta disponivel
        }
        return c;
    }

    public Camera open() {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();


        int front = -1;
        int back = -1;
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                front = i;
                return Camera.open(i);
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                back = i;
            }
        }
        if (front == -1) {
            Toast.makeText(getApplicationContext(), "Não possui câmera frontal", Toast.LENGTH_LONG).show();
            return Camera.open(back);
        }
        return Camera.open();
    }

    @Override
    public void onClick(View v) {

    }
}

