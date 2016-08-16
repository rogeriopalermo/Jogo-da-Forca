package br.com.jogorogerio.jogodaforca;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.jogorogerio.jogodaforca.DAO.JogoDaVelhaDAO;
import br.com.jogorogerio.jogodaforca.adapter.ListaAdapter;
import br.com.jogorogerio.jogodaforca.model.OptionName;

public class ListaNomesActivity extends AppCompatActivity {

    private ListView namesList;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_nomes);

        namesList = (ListView) findViewById(R.id.lvListaAlunos);

        populateList();

        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OptionName option = (OptionName) namesList.getItemAtPosition(i);
                Intent result = new Intent();
                result.putExtra("name", option.getNome());
                setResult(Activity.RESULT_OK, result);
                finish();
             }
        });

        registerForContextMenu(namesList);
    }

    private void populateList() {
        JogoDaVelhaDAO dao = new JogoDaVelhaDAO(this);
        List<OptionName> list = dao.retrieveWords();
        dao.close();
        ListaAdapter adapter = new ListaAdapter(this, list);

        namesList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        JogoDaVelhaDAO dao = new JogoDaVelhaDAO(this);
        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final OptionName option = (OptionName) namesList.getItemAtPosition(adapterMenuInfo.position);
        menu.setHeaderTitle(option.getNome());

        MenuItem itemDeletar = menu.add("Deletar");
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                JogoDaVelhaDAO dao = new JogoDaVelhaDAO(ListaNomesActivity.this);
                dao.deleteOption(option);
                dao.close();
                Toast.makeText(ListaNomesActivity.this, option.getNome() + " deleted.", Toast.LENGTH_SHORT).show();
                populateList();
                return false;
            }
        });

    }
}
