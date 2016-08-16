package br.com.jogorogerio.jogodaforca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.jogorogerio.jogodaforca.DAO.JogoDaVelhaDAO;
import br.com.jogorogerio.jogodaforca.model.OptionName;

public class Main extends AppCompatActivity {
    public static final int REQUEST_CODE = 123;
    public static final int LISTANOMESACTIVITY_CODE = 124;
    private Button btJogar;
    private Button btPlay;
    private EditText etLetra;
    private ForcaView forcaView;
    private ForcaController forcaController;
    private List<OptionName> wordsList;
    private String wordRegisteredForGame = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btJogar = (Button)findViewById(R.id.btJogar);
        btPlay = (Button)findViewById(R.id.btPlay);
        etLetra = (EditText) findViewById(R.id.etLetra);

        forcaView = (ForcaView) findViewById(R.id.fvJogo);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_adicionarPalavra) {
            Intent intent = new Intent(this, InserirPalavrasActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (item.getItemId() == R.id.menu_listNames) {
            Intent intent = new Intent(this, ListaNomesActivity.class);
            startActivityForResult(intent, LISTANOMESACTIVITY_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE: {
                if(resultCode == Activity.RESULT_OK) {
                    String palavra = data.getStringExtra(InserirPalavrasActivity.PALAVRA);
                    wordRegisteredForGame = palavra;
                    setForcaController(new ForcaController(wordRegisteredForGame));
                    startGame();
                }
                break;
            }
            case LISTANOMESACTIVITY_CODE: {
                if(resultCode == Activity.RESULT_OK) {
                    String palavra = data.getStringExtra("name");
                    wordRegisteredForGame = palavra;
                    setForcaController(new ForcaController(wordRegisteredForGame));
                    startGame();
                }
                break;
            }
        }
    }

    private void init() {

        btJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLetra.getText().toString().trim().length() == 0) {
                    Toast.makeText(Main.this, "Select a letter!", Toast.LENGTH_LONG).show();
                    return;
                }
                getForcaController().joga(etLetra.getText().toString().toLowerCase().trim().charAt(0));
                forcaView.invalidate();
                etLetra.getText().clear();
                if(getForcaController().isTerminou()) {
                    btJogar.setEnabled(false);
                    btPlay.setEnabled(true);
                    etLetra.setEnabled(false);
                    if (getForcaController().isMorreu()) {
                        Toast.makeText(Main.this, "You lose!", Toast.LENGTH_LONG).show();
                    }
                    else if (getForcaController().isGanhou()) {
                        Toast.makeText(Main.this, "You win!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JogoDaVelhaDAO dao = new JogoDaVelhaDAO(Main.this);
                wordsList = dao.retrieveWords();
                dao.close();
                setForcaController(new ForcaController(wordsList.get(new Random().nextInt(wordsList.size())).getNome()));
                startGame();
            }
        });
    }

    private void startGame() {
        forcaView.setForcaController(getForcaController());
        forcaView.invalidate();
        forcaView.setPathForca(new Path()); // restarting the path the drawing of the hangman AND the letters' gaps will be reseted.
        etLetra.getText().clear();
        etLetra.setEnabled(true);
        btJogar.setEnabled(true);
        btPlay.setEnabled(false);
    }

    public ForcaController getForcaController() {
        return forcaController;
    }

    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
}
