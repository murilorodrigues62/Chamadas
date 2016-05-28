package br.edu.ifspsaocarlos.sdm.chamadas;


import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lstView);
        listAdapter = new ArrayAdapter<String>(this, R.layout.simple_row);
        String[] dadosLista = new String[]{};

        Uri ligacoes = Calls.CONTENT_URI;
        //Uri contatos = ContactsContract.PhoneLookup.CONTENT_FILTER_URI;

        String[] projecao = new String[]{Calls.NUMBER, Calls.DURATION, Calls.TYPE, Calls.DATE};

        Cursor lig = getContentResolver().query(ligacoes, projecao, null, null, null);
        if (lig != null) {
            lig.moveToFirst();

            while (!lig.isAfterLast()) {

                String num = lig.getString(0);
                String duracao = lig.getString(1);
                String tipo = null;
                int typocall = lig.getInt(2);

                switch (typocall) {
                    case 1:
                        tipo = "Recebida";
                        break;
                    case 2:
                        tipo = "Efetuada";
                        break;
                    case 3:
                        tipo = "Perdida";
                        break;
                }
                Long data = lig.getLong(3);
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy HH:mm");
                String dataligacao = formato.format(new Date(data));

                String dados = "Numero: " + num + "\nDuracao (seg): " + duracao + "\nTipo: " + tipo + "\nData: " + dataligacao;

                listAdapter.add(dados);

                lig.moveToNext();
            }

            listView.setAdapter(listAdapter);
        }
        lig.close();


    }

}
