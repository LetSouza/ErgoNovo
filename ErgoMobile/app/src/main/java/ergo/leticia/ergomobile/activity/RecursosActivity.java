package ergo.leticia.ergomobile.activity;

/*
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import ergo.leticia.ergomobile.R;

@EActivity(R.layout.activtiy_recursos)
public class RecursosActivity extends Activity {

    @ViewById(R.id.TestarCameraButton)
    Button mTestarCamera;

    @ViewById(R.id.TestarAudioButton)
    Button mTestarAudio;
    @ViewById(R.id.IniciarButtons)
    Button mIniciarTeste;
    @StringRes(R.string.status_teste_usabilidade)
    String statusTesteUsabilidade;

    @Click(R.id.TestarCameraButton)
    public void onClickTestarCamera(View view) {
        Intent it = new Intent(this, TesteVideoActivity.class);
        startActivity(it);
    }

    @Click(R.id.TestarAudioButton)
    public void onClickTestarAudio(View view) {
        Intent in = new Intent(this, TesteAudioActivity.class);
        in.putExtra(statusTesteUsabilidade, false);
        startActivity(in);
    }

    @Click(R.id.IniciarButtons)
    public void onClickIniciarTest(View view) {
        if(Camera.getNumberOfCameras() > 1) {
            Intent intent = new Intent(this, TesteUsabilidadeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, TesteAudioActivity.class);
            intent.putExtra(statusTesteUsabilidade, true);
            startActivity(intent);
        }
       finish();
    }
}*/
