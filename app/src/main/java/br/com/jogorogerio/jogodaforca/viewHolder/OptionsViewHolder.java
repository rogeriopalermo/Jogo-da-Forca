package br.com.jogorogerio.jogodaforca.viewHolder;

import android.view.View;
import android.widget.TextView;

import br.com.jogorogerio.jogodaforca.R;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class OptionsViewHolder {

    public final TextView fieldName;
    public final TextView fieldSize;

    public OptionsViewHolder(View mainView) {
        fieldName = (TextView) mainView.findViewById(R.id.tvNome);
        fieldSize = (TextView) mainView.findViewById(R.id.tvTamanho);
    }
}
