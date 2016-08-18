package br.com.jogorogerio.jogodaforca;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jogorogerio.jogodaforca.DAO.JogoDaVelhaDAO;

public class InserirPalavrasActivity extends AppCompatActivity {

    public static final String PALAVRA = "palavra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_palavras);

        final EditText etNewWord = (EditText) findViewById(R.id.etNewWord);

        Button btnInsertAndPlay = (Button) findViewById(R.id.btnInsertAndPlay);
        btnInsertAndPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWordTask(etNewWord);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(PALAVRA, etNewWord.getText().toString().toLowerCase());
                setResult(Activity.RESULT_OK,  resultIntent);
                finish();
            }
        });

        Button btnInsertAndRandomize = (Button) findViewById(R.id.btnInsertAndRandomize);
        btnInsertAndRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWordTask(etNewWord);
            }
        });
    }

    private void insertWordTask(EditText etNewWord) {
        JogoDaVelhaDAO dao = new JogoDaVelhaDAO(InserirPalavrasActivity.this);
        String word = etNewWord.getText().toString();
        dao.insertWord(word.toLowerCase());
        Toast.makeText(InserirPalavrasActivity.this, "Inserted the word: " + word, Toast.LENGTH_SHORT).show();
        dao.close();
    }
}
