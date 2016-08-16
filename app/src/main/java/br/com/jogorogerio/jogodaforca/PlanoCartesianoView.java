package br.com.jogorogerio.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

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

    public PlanoCartesianoView(Context context) {
        super(context);
    }

    public PlanoCartesianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void drawPlanoCartesiano(Canvas canvas) {
        Path path = new Path();

        int max = toPixel(10);

        for(int n = 0; n <= 10; n++) {
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

        setUnidade(menorLadoDisplay/10);
        //drawPlanoCartesiano(canvas);
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
