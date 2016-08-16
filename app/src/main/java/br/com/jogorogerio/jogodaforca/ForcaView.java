package br.com.jogorogerio.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

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

    private void plotaTracos() {
        int eixoX = toPixel(1);
        getPathForca().moveTo(eixoX + 5, toPixel(10));

        if(getForcaController()==null) {
            return;
        }

        for(int i = 0; i< getForcaController().getPalavraAteAgora().length();i++) {
            getPathForca().rMoveTo(6, 0);
            getPathForca().rLineTo(toPixel(1), 0);
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

        plotaTracos();

        canvas.drawPath(getPathForca(), getPaintForca());
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
        painttraco.setStrokeWidth(2);
        painttraco.setTextSize(25);
        return painttraco;
    }

    private void drawLetrasCorretas(Canvas canvas) {
        int eixoX = toPixel(1);
        getPathForca().moveTo(eixoX , toPixel(10));
        eixoX += 35;

        if(getForcaController() == null) {
            return;

        }

        for(int i = 0; i < getForcaController().getPalavraAteAgora().length(); i++) {
            char c = getForcaController().getPalavraAteAgora().charAt(i);
            canvas.drawText(c+"",
                                eixoX +
                                        ((toPixel(1) + 6) * i),
                                            toPixel(10)- 15,
                                                getPaintTraco());
        }
    }

    public ForcaController getForcaController() {
        return forcaController;
    }

    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
}
