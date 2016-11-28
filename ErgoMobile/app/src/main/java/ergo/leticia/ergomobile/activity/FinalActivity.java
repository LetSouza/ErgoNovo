package ergo.leticia.ergomobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ergo.leticia.ergomobile.R;

/**
 * Created by Letícia Silva Souza on 01/09/2016.
 */
public class FinalActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Button novo = (Button) findViewById(R.id.novo_teste);
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinalActivity.this, MainActivity.class));
                finish();
            }
        });

        Button fechar = (Button) findViewById(R.id.fechar_teste);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
    }
}
