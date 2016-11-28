package ergo.leticia.ergomobile.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ergo.leticia.ergomobile.activity.TesteUsabilidadeActivity;


public class RecorderService extends Service {
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TCC = "tcc";



    private static final String TAG = "RecorderService";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private static Camera mServiceCamera;
    private static boolean mRecordingStatus = true;
    private MediaRecorder mMediaRecorder;
    private static File file;

    @Override
    public void onCreate() {
        super.onCreate();
        mRecordingStatus = false;
        Log.i("TCC", "voce nao deveria estar aqui2");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        stopRecording();
        Log.i("TCC", "voce nao deveria estar aqui");
        mRecordingStatus = false;

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceCamera = TesteUsabilidadeActivity.mCamera;
        mServiceCamera = getInstanceCamera();
        Log.i("TCC", "entrei no oncreat");
        if (mServiceCamera != null) {

            mSurfaceView = TesteUsabilidadeActivity.mSurfaceView;
            mSurfaceHolder = TesteUsabilidadeActivity.mSurfaceHolder;

            if (mRecordingStatus == false)
                startRecording();
            Log.i("TCC", "entrei no oncreat 2");
        }
        return -1;
    }

    

    public boolean startRecording() {
        Log.i(TCC, "startRecord");
        try {
            Toast.makeText(getBaseContext(), "Registro iniciado", Toast.LENGTH_SHORT).show();

            //mServiceCamera = Camera.open();
            /*Camera.Parameters params = mServiceCamera.getParameters();
            mServiceCamera.setParameters(params);
            Camera.Parameters p = mServiceCamera.getParameters();

            final List<Size> listSize = p.getSupportedPreviewSizes();
            Size mPreviewSize = listSize.get(2);
            Log.v(TAG, "use: width = " + mPreviewSize.width
                    + " height = " + mPreviewSize.height);
            p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
            mServiceCamera.setParameters(p);*/

            try {
                mServiceCamera.setPreviewDisplay(mSurfaceHolder);
                mServiceCamera.startPreview();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

           mServiceCamera.setDisplayOrientation(90);
           /* Camera.Parameters par = mServiceCamera.getParameters();
            par.setRotation(180);
            par.setPreviewFrameRate(20);
            mServiceCamera.setParameters(par);*/
            //mServiceCamera.setDisplayOrientation(90);
            mServiceCamera.getParameters().setRotation(90);
            mServiceCamera.unlock();

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mServiceCamera);
            Log.i(TCC, "teste");
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);

            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));
            mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
            //mMediaRecorder.setMaxDuration(20000);
            Log.i(TCC, "teste3");


            mRecordingStatus = true;

            mMediaRecorder.prepare();
            mMediaRecorder.start();



            return true;
        } catch (IllegalStateException e) {
            //Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
        // return false;
    }

    public void stopRecording() {
        Toast.makeText(getBaseContext(), "Teste Finalizado", Toast.LENGTH_SHORT).show();
        try {
            mServiceCamera.reconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Log.i(TCC, "stoP");
        mMediaRecorder.stop();
        mMediaRecorder.reset();

        mServiceCamera.stopPreview();
        mMediaRecorder.release();

        mServiceCamera.release();
        mServiceCamera = null;
    }

    private Camera getInstanceCamera() {
        try {
            Log.i(TCC, Camera.getNumberOfCameras() + " cam");
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            if (Camera.getNumberOfCameras() > 1) {
                return Camera.open(1);
            }
        } catch (Exception e) {

        }
        return Camera.open();
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory("ErgoMobile"), "ErgoMobile-Vídeos");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Vídeos", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return (file = mediaFile);
    }


}