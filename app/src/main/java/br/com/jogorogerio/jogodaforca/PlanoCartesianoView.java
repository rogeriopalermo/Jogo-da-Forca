package br.com.jogorogerio.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import br.com.jogorogerio.jogodaforca.graphic.Screen;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class PlanoCartesianoView extends View {


    //armazena o menor lado do nosso display
    //caso o display seja maior na vertical
    //a variavel guardara o valor da horizontal
    //e vice e versa
    private int menorLadoDisplay;
    private int unidade;
    private Context context; //in case of Toast

    public PlanoCartesianoView(Context context) {
        super(context);
        this.context = context;
    }

    public PlanoCartesianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void drawPlanoCartesiano(Canvas canvas) {
        Path path = new Path();

        int max = toPixel(15);
        for(int n = 0; n <= 15; n++) {
            //desenhando as linhas verticais
            path.moveTo(toPixel(n), 1);
            path.lineTo(toPixel(n), max);
            //desenhando as linhas horizontais
            path.moveTo(1, toPixel(n));
            path.lineTo(max, toPixel(n));
        }

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        canvas.drawPath(path, paint);
    }

    public void drawExample(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight()-15);
        path.lineTo(getWidth(), getHeight()- 15);

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        canvas.drawPath(path, paint);
    }

    public int toPixel(int vezes) {

        return vezes * getUnidade();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(getHeight() > getWidth())
            setMenorLadoDisplay(getWidth());
        else
            setMenorLadoDisplay(getHeight());

        setUnidade(menorLadoDisplay/15);
      //  drawPlanoCartesiano(canvas);
      //  drawExample(canvas);
    }

    public int getMenorLadoDisplay() {
        return menorLadoDisplay;
    }

    public void setMenorLadoDisplay(int menorLadoDisplay) {
        this.menorLadoDisplay = menorLadoDisplay;
    }

    public int getUnidade() {
        return unidade;
    }

    public void setUnidade(int unidade) {
        this.unidade = unidade;
    }
}
