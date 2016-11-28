package ergo.leticia.ergomobile.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ergo.leticia.ergomobile.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {
    // Tempo de exibicao do splash screen
    private static int SPLASH_TIME_OUT = 3000;
    @ViewById(R.id.progressBarSplash)
    ProgressBar progressBar;
    @ViewById(R.id.txvSplashLogo)
    TextView txvSplashLogo;

    @Override
    protected void onStart() {
        super.onStart();
        processAfterViewLoad();
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(107,35,142), PorterDuff.Mode.MULTIPLY);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/atomic_clock.ttf");
        txvSplashLogo.setTypeface(tf);
    }

    public void processAfterViewLoad() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity_.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
