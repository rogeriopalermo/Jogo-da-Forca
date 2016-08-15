package br.com.jogorogerio.jogodaforca;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main extends AppCompatActivity {
    private Button btJogar;
    private Button btPlay;
    private EditText etLetra;
    private ForcaView forcaView;
    private ForcaController forcaController;

    private String[] palavras = {"alura", "caelum"};

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
    private void init() {

        btJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLetra.getText().toString().trim().length() == 0) {
                    Toast.makeText(Main.this, "Faça uma jogada!", Toast.LENGTH_LONG).show();
                    return;
                }
                getForcaController().joga(etLetra.getText().toString().toLowerCase().trim().charAt(0));
                forcaView.invalidate();
                etLetra.getText().clear();
                if(getForcaController().isTerminou()) {
                    btJogar.setEnabled(false);
                    btPlay.setEnabled(true);
                    if (getForcaController().isMorreu()) {
                        Toast.makeText(Main.this, "Ops! Você perdeu!", Toast.LENGTH_LONG).show();
                    }
                    else if (getForcaController().isGanhou()) {
                        Toast.makeText(Main.this, "Você ganhou!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForcaController(new ForcaController(palavras[new Random().nextInt(palavras.length)]));
                forcaView.setForcaController(getForcaController());
                forcaView.invalidate();
                forcaView.setPathForca(new Path()); // restarting the path the drawing of the hangman AND the letters' gaps will be reseted.
                etLetra.getText().clear();
                etLetra.setEnabled(true);
                btJogar.setEnabled(true);
                btPlay.setEnabled(false);
            }
        });
    }

    public ForcaController getForcaController() {
        return forcaController;
    }

    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
}
