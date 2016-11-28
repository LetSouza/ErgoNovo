package ergo.leticia.ergomobile.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import ergo.leticia.ergomobile.service.AudioService;
import ergo.leticia.ergomobile.service.BackgroundAudioService;
import ergo.leticia.ergomobile.R;

public class TesteAudioActivity extends Activity {

    private MediaRecorder myAudioRecorder = new MediaRecorder();
    public static Button Play, Stop, Record;
    private String outputFile = null;
    public static Boolean testeUsabilidade = false;

    // variaveis para tirar print da tela
    private static final String TAG_recorder = TesteUsabilidadeActivity.class.getName();
    private static final int REQUEST_CODE = 100;
    private static String STORE_DIRECTORY;
    private static int IMAGES_PRODUCED;
    private static final String SCREENCAP_NAME = "screencap";
    private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
    private static MediaProjection sMediaProjection;

    private MediaProjectionManager mProjectionManager;
    private ImageReader mImageReader;
    private Handler mHandler;
    private Display mDisplay;
    private VirtualDisplay mVirtualDisplay;
    private int mDensity;
    private int mWidth;
    private int mHeight;
    private int mRotation;
    private OrientationChangeCallback mOrientationChangeCallback;

    private File mediaStorageDir = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_audio);
        try{
             testeUsabilidade = getIntent().getExtras().getBoolean("testeUsabilidade");
        }catch(Exception e){

        }

        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        Play = (Button) findViewById(R.id.button);
        Stop = (Button) findViewById(R.id.button2);
        Record = (Button) findViewById(R.id.button3);


//        Stop.setEnabled(false);
//        Play.setEnabled(false);
//
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory("ErgoMobile"), "Audio Teste");
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        mediaStorageDir = new File(mediaStorageDir.getPath() + File.separator + "AUD_" + timeStamp + ".3gp");
//
//        myAudioRecorder = new MediaRecorder();
//        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//        myAudioRecorder.setOutputFile(mediaStorageDir.getPath());
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Record.setEnabled(false);
//                Stop.setEnabled(true);
                Intent intent = new Intent(TesteAudioActivity.this, AudioService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);
                if (testeUsabilidade) {
                    Intent intent1 = new Intent(TesteAudioActivity.this, BackgroundAudioService.class);
                    intent1.putExtra("testeUsabilidade", false); //se false aviso a backgronud pra retornar pra activity de teste de audio
                    startService(intent1);
                    startProjection();
                    //finish();

                }
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Stop.setEnabled(false);
//                Play.setEnabled(true);
                stopService(new Intent(TesteAudioActivity.this, AudioService.class));
                stopService(new Intent(TesteAudioActivity.this, BackgroundAudioService.class));
                if(testeUsabilidade){
                    startActivity(new Intent(TesteAudioActivity.this, FinalActivity.class));
                    stopProjection();
                }
                testeUsabilidade = false;
            }
        });

        if (testeUsabilidade) {
            Play.setVisibility(View.INVISIBLE);
        }

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(AudioService.getOutputFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();


               /* Play.setEnabled(false);
                Record.setEnabled(true);*/

                Toast.makeText(getApplicationContext(), "Reprodução do áudio", Toast.LENGTH_LONG).show();

            }


        });

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

    private class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = null;
            FileOutputStream fos = null;
            Bitmap bitmap = null;

            try {
                image = mImageReader.acquireLatestImage();
                if (image != null) {
                    Image.Plane[] planes = image.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    int rowStride = planes[0].getRowStride();
                    int rowPadding = rowStride - pixelStride * mWidth;

                    // create bitmap
                    bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);

                    // write bitmap to a file
                    fos = new FileOutputStream(getOutputMediaFile());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    IMAGES_PRODUCED++;
                    Log.e(TAG_recorder, "captured image: " + IMAGES_PRODUCED);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos!=null) {
                    try {
                        fos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                if (bitmap!=null) {
                    bitmap.recycle();
                }

                if (image!=null) {
                    image.close();
                }
            }
        }

        private  File getOutputMediaFile() {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            if(mediaStorageDir == null){
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory("ErgoMobile"), "ErgoMobile-Imagens_" +
                        timeStamp);
            }

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("Vídeos", "failed to create directory");
                    return null;
                }
            }


            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");


            return mediaFile;
        }

    }

    // classe desnecessaria
    private class OrientationChangeCallback extends OrientationEventListener {
        public OrientationChangeCallback(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            synchronized (this) {
                final int rotation = mDisplay.getRotation();
                if (rotation != mRotation) {
                    mRotation = rotation;
                    try {
                        // clean up
                        if(mVirtualDisplay != null) mVirtualDisplay.release();
                        if(mImageReader != null) mImageReader.setOnImageAvailableListener(null, null);

                        // re-create virtual display depending on device width / height
                        createVirtualDisplay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class MediaProjectionStopCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            Log.e("ScreenCapture", "stopping projection.");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mVirtualDisplay != null) mVirtualDisplay.release();
                    if(mImageReader != null) mImageReader.setOnImageAvailableListener(null, null);
                    if(mOrientationChangeCallback != null) mOrientationChangeCallback.disable();
                    sMediaProjection.unregisterCallback(MediaProjectionStopCallback.this);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            sMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);

            if (sMediaProjection != null) {
                File externalFilesDir = getExternalFilesDir(null);
                if (externalFilesDir != null) {
                    STORE_DIRECTORY = externalFilesDir.getAbsolutePath() + "/screenshots/";
                    File storeDirectory = new File(STORE_DIRECTORY);
                    if (!storeDirectory.exists()) {
                        boolean success = storeDirectory.mkdirs();
                        if (!success) {
                            Log.e(TAG_recorder, "failed to create file storage directory.");
                            return;
                        }
                    }
                } else {
                    Log.e(TAG_recorder, "failed to create file storage directory, getExternalFilesDir is null.");
                    return;
                }

                // display metrics
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                mDensity = metrics.densityDpi;
                mDisplay = getWindowManager().getDefaultDisplay();

                // create virtual display depending on device width / height
                createVirtualDisplay();

                // register orientation change callback
                mOrientationChangeCallback = new OrientationChangeCallback(this);
                if (mOrientationChangeCallback.canDetectOrientation()) {
                    mOrientationChangeCallback.enable();
                }

                // register media projection stop callback
                sMediaProjection.registerCallback(new MediaProjectionStopCallback(), mHandler);
            }
        }
    }

    /****************************************** UI Widget Callbacks *******************************/
    private void startProjection() {
        startActivityForResult(mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
    }

    private void stopProjection() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (sMediaProjection != null) {
                    sMediaProjection.stop();
                }
            }
        });
    }

    /****************************************** Factoring Virtual Display creation ****************/
    private void createVirtualDisplay() {
        // get width and height
        Point size = new Point();
        mDisplay.getSize(size);
        mWidth = size.x;
        mHeight = size.y;

        // start capture reader
        mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2);
        mVirtualDisplay = sMediaProjection.createVirtualDisplay(SCREENCAP_NAME, mWidth, mHeight, mDensity, VIRTUAL_DISPLAY_FLAGS, mImageReader.getSurface(), null, mHandler);
        mImageReader.setOnImageAvailableListener(new ImageAvailableListener(), mHandler);
    }
}


