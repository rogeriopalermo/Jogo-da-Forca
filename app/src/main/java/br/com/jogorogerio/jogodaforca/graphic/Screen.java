package br.com.jogorogerio.jogodaforca.graphic;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Rogerio on 17/08/2016.
 */
public class Screen {
    private final DisplayMetrics metrics;

    public Screen(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        metrics = new DisplayMetrics();

        display.getMetrics(metrics);
    }

    public int getHeight() {return metrics.heightPixels;}
    public int getWidth() {return metrics.widthPixels;}
}
