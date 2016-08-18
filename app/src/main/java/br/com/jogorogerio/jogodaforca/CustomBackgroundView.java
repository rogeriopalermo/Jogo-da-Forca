package br.com.jogorogerio.jogodaforca;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.jogorogerio.jogodaforca.graphic.Screen;

/**
 * Created by Rogerio on 18/08/2016.
 */
public class CustomBackgroundView extends View {

    private Bitmap back;
    private Screen screen ;

    public CustomBackgroundView(Context context) {
        super(context);
        screen = new Screen(context);
        Bitmap background  = BitmapFactory.decodeResource(getResources(), R.drawable.hangmanbackground2);
        back = Bitmap.createScaledBitmap(background, screen.getWidth(), screen.getHeight(), false);

    }

    public CustomBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        screen = new Screen(context);
        Bitmap background  = BitmapFactory.decodeResource(getResources(), R.drawable.hangmanbackground2);
        back = Bitmap.createScaledBitmap(background, screen.getWidth(), screen.getHeight(), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       canvas.drawBitmap(back, 0, 0, null);
    }
}
