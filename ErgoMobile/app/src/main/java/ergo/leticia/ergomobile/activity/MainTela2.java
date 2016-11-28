package ergo.leticia.ergomobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

import ergo.leticia.ergomobile.R;

/**
 * Created by Letícia Silva Souza on 25/11/2016.
 */

public class MainTela2 extends Activity implements AdapterView.OnItemClickListener {private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;

    String[] mTitulos;
    String[] mDescricao;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //carrega o layout onde contem o ListView
        setContentView(R.layout.activtiy_recursos);

        Resources res = getResources();
        mTitulos = res.getStringArray(R.array.Titulos);
        mDescricao = res.getStringArray(R.array.Descricao);
        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.listView);
        //Define o Listener quando alguem clicar no item.
        createListView();
        listView.setOnItemClickListener(this);


    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();
        ItemListView item1 = new ItemListView(mDescricao[0], mTitulos[0], R.drawable.camera, ItemListView.CAMERA);
        ItemListView item2 = new ItemListView(mDescricao[1], mTitulos[1],R.drawable.audio, ItemListView.AUDIO);
        ItemListView item3 = new ItemListView(mDescricao[2],mTitulos[2], R.drawable.usabilidade, ItemListView.TESTE);

        itens.add(item1);
        itens.add(item2);
        itens.add(item3);

        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter
        listView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //Pega o item que foi selecionado.
        ItemListView item = adapterListView.getItem(arg2);
        //Demostração
        if(item.getId()==ItemListView.CAMERA){
            Intent it = new Intent(this, TesteVideoActivity.class);
            startActivity(it);
        }else if(item.getId()==ItemListView.AUDIO){
            Intent in = new Intent(this, TesteAudioActivity.class);
            in.putExtra("statusTesteUsabilidade", false);
            startActivity(in);
        }else if(item.getId()==ItemListView.TESTE){
            if(Camera.getNumberOfCameras() > 1) {
                Intent intent = new Intent(this, TesteUsabilidadeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, TesteAudioActivity.class);
                intent.putExtra("statusTesteUsabilidade", true);
                startActivity(intent);
            }
            finish();
        }

    }
}
