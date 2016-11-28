package ergo.leticia.ergomobile.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import ergo.leticia.ergomobile.R;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.IniciarBtn)
    Button mIniciar;
    @ViewById(R.id.textViewAvaliador)
    EditText textViewAvaliador;
    @ViewById(R.id.textViewSite)
    EditText textViewSite;
    @ViewById(R.id.textViewFuncionalidade)
    EditText textViewFuncionalidade;
    @StringRes(R.string.btn_ok)
    String okButton;
    @StringRes(R.string.validation_data_msg_main)
    String validationDataMsgMain;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_primeira_tela, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.IniciarBtn)
    public void onClick(View view) {

        String v1 = textViewAvaliador.getText().toString();
        String v2 = textViewSite.getText().toString();
        String v3 = textViewFuncionalidade.getText().toString();

        if (v1.trim().isEmpty() || v2.trim().isEmpty() || v3.trim().isEmpty()) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage(validationDataMsgMain);
            dlg.setNeutralButton(okButton, null);
            dlg.show();
        } else {
            Intent it = new Intent(this, MainTela2.class);
            startActivity(it);
        }
    }

}




