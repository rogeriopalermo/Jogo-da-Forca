package br.com.jogorogerio.jogodaforca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.jogorogerio.jogodaforca.R;
import br.com.jogorogerio.jogodaforca.model.OptionName;
import br.com.jogorogerio.jogodaforca.viewHolder.OptionsViewHolder;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class ListaAdapter extends BaseAdapter{

    private final Context context;
    private final List<OptionName> listaNomes;

    public ListaAdapter(Context context, List<OptionName> alunos) {
        this.context = context;
        this.listaNomes = alunos;
    }

    @Override
    public int getCount() {
        return listaNomes.size();
    }

    @Override
    public Object getItem(int i) {
        return listaNomes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaNomes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OptionName option = listaNomes.get(i);

        LayoutInflater inflater = LayoutInflater.from(context);

        OptionsViewHolder viewHolder;
        View mainView;
        if(view == null) {
            mainView = inflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new OptionsViewHolder(mainView);
            mainView.setTag(viewHolder);
        } else {
            mainView = view;
            viewHolder = (OptionsViewHolder) mainView.getTag();
        }

        viewHolder.fieldName.setText(option.getNome());
        String tamanho;
        if (option.getNome().length() > 1) {
            tamanho = option.getNome().length() + " letras";
        } else {
            tamanho = option.getNome().length() + " letra";
        }
        viewHolder.fieldSize.setText(tamanho);

        return mainView;
    }
}
