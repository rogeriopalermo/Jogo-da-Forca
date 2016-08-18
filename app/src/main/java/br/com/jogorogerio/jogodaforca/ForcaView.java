package br.com.jogorogerio.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import br.com.jogorogerio.jogodaforca.graphic.Screen;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class ForcaView extends PlanoCartesianoView {

    private enum Membro {braco, perna}
    private enum Lado {direito, esquerdo}
    public ForcaView(Context context) {
        super(context);

    }

    public ForcaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPathForca(new Path());
    }

    //responsavel por armazenar todas as figuras geometricas a serem desenhadas
    private Path pathForca;
    private Paint paintForca;
    private ForcaController forcaController;

    private void plotaCabeca() {
        getPathForca().addCircle(toPixel(5), toPixel(3), toPixel(1), Path.Direction.CW);
    }

    private void plotaForca() {

        getPathForca().moveTo(toPixel(0), toPixel(10));
        getPathForca().lineTo(toPixel(1), toPixel(10));

        getPathForca().moveTo(toPixel(1), toPixel(10));
        getPathForca().lineTo(toPixel(1), toPixel(1));

        getPathForca().rLineTo(toPixel(4), 0);

        getPathForca().rLineTo(0, toPixel(1));
    }

    private void plotaCorpo() {
        getPathForca().moveTo(toPixel(5), toPixel(4));
        getPathForca().lineTo(toPixel(5), toPixel(7));
    }

    private void plotaMembro(Membro membro, Lado lado) {
        final int posicaoDoCorpo = 5; //posicao do corpo no eixo x
        final int alturaDoBraco = 5; //define o y onde sera desenhado o braço
        final int alturaDaPerna = 7; // define o y onde sera desenhado a perna

        int alturaFinal;

        if(membro==Membro.braco) {
            getPathForca().moveTo(toPixel(posicaoDoCorpo), toPixel(alturaDoBraco));
            alturaFinal = alturaDoBraco +1;
        } else {
            getPathForca().moveTo(toPixel(posicaoDoCorpo), toPixel(alturaDaPerna));
            alturaFinal = alturaDaPerna + 1;
        }

        if(lado==Lado.direito) {
            getPathForca().lineTo(toPixel(posicaoDoCorpo +1), toPixel(alturaFinal));
        } else {
            getPathForca().lineTo(toPixel(posicaoDoCorpo-1), toPixel(alturaFinal));
        }
    }

    private void plotaTracosLetras(Path path) {
        int eixoX = 1; //used to position in which line to draw in the X axis
        int eixoY = 10; //used to position in which line to draw in the Y axis
        path.moveTo(eixoX + 5, toPixel(eixoY));

        if(getForcaController()==null) {
            return;
        }
        int limitCounter = 0;
        String word = getForcaController().getPalavraAteAgora();

        for(int i = 0; i< word.length();i++) {
            if(limitCounter == 12) {
                eixoY++;
                eixoX = 1;
                limitCounter=0;
                path.moveTo(toPixel(eixoX) + 5, toPixel(eixoY));
            }
            path.moveTo(toPixel(eixoX) + 5, toPixel(eixoY));
            eixoX++;
            String comparison = String.valueOf(word.charAt(i));
            if(comparison.equals("_"))
                path.lineTo(toPixel(eixoX), toPixel(eixoY));

            limitCounter++;
        }
    }
    private void plotaTracosEspacos(Path path2) {
        int eixoX = 1;
        int eixoY = 10;
        path2.moveTo(eixoX + 5, toPixel(eixoY));

        if(getForcaController()==null) {
            return;
        }
        int limitCounter = 0;
        String word = getForcaController().getPalavraParaAdivinhar();

        for(int i = 0; i< word.length();i++) {
            if(limitCounter == 12) {
                eixoY++;
                eixoX = 1;
                limitCounter=0;
                path2.moveTo(toPixel(eixoX) +5, toPixel(eixoY));
            }
            path2.moveTo(toPixel(eixoX) + 5, toPixel(eixoY));
            eixoX++;
            if(Character.isWhitespace(word.charAt(i))) {
                path2.lineTo(toPixel(eixoX), toPixel(eixoY));
            }
            limitCounter++;
        }
    }
    private void drawLetrasCorretas(Canvas canvas) {
        int eixoX = toPixel(1);
        int eixoY = 10;
        int limitCounter = 0;
        int spaceCounter = -1;
        getPathForca().moveTo(eixoX , toPixel(eixoY));
        eixoX += 35;

        if(getForcaController() == null) {
            return;

        }

        for(int i = 0; i < getForcaController().getPalavraAteAgora().length(); i++) {
            spaceCounter++;
            if(limitCounter == 12) {
                limitCounter = 0;
                spaceCounter = 0;
                eixoY++;
            }
            char c = getForcaController().getPalavraAteAgora().charAt(i);
            canvas.drawText(c+"",
                    eixoX +
                            ((toPixel(1) ) * spaceCounter),
                    toPixel(eixoY),
                    getPaintTraco());
            limitCounter++;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        plotaForca();

        if(getForcaController() != null) {
            switch (getForcaController().getQuantidadeDeErros()) {
                case 0:
                    plotaCabeca();
                    break;
                case 1:
                    plotaCorpo();
                    break;
                case 2:
                    plotaMembro(Membro.braco, Lado.direito);
                    break;
                case 3:
                    plotaMembro(Membro.braco, Lado.esquerdo);
                    break;
                case 4:
                    plotaMembro(Membro.perna, Lado.direito);
                    break;
                case 5:
                    plotaMembro(Membro.perna, Lado.esquerdo);
                    break;
            }
        }

        drawLetrasCorretas(canvas);

        canvas.drawPath(getPathForca(), getPaintForca());

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
       // plotaTracosLetras(path);

        Path path2 = new Path();
        Paint paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(8);
     //   plotaTracosEspacos(path2);

     //   canvas.drawPath(path, paint);
     //  canvas.drawPath(path2,paint2);
    }


    public Path getPathForca() {
        return pathForca;
    }

    public void setPathForca(Path pathForca) {
        this.pathForca = pathForca;
    }

    public Paint getPaintForca() {
        paintForca = new Paint();
        paintForca.setColor(Color.BLACK);
        paintForca.setStyle(Paint.Style.STROKE);
        paintForca.setStrokeWidth(8);
        return paintForca;
    }

    private Paint getPaintTraco() {
        Paint painttraco = new Paint();
        painttraco.setColor(Color.BLACK);
        painttraco.setStyle(Paint.Style.FILL);
        painttraco.setStrokeWidth(3);
        painttraco.setTextSize(35);
        return painttraco;
    }



    public ForcaController getForcaController() {
        return forcaController;
    }

    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
}
