package com.android.provaandroid2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.provaandroid2b.R;
import com.android.provaandroid2b.model.Ordenha;

import java.util.List;

public class OrdenhaAdapter extends BaseAdapter {
    LayoutInflater myInflater;
    List<Ordenha> ordenhaList;

    public OrdenhaAdapter(Context context, List<Ordenha> ordenhaList) {
        this.ordenhaList = ordenhaList;
        myInflater = LayoutInflater.from(context); //Responsavel por inflar o layout
    }

    @Override
    public int getCount() {
        return ordenhaList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordenhaList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //Popula a view passada com os parâmetros do Objeto
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Ordenha ordenha = ordenhaList.get(position); //Recupera o animal da posição passada
        view = myInflater.inflate(R.layout.item_ordenha, null); //Seto o Layout escolhido na view
        ((TextView) view.findViewById(R.id.etIdentificador)).setText(String.valueOf(ordenha.getIdentificador()));
        ((TextView) view.findViewById(R.id.etQtdLitros)).setText(String.valueOf(ordenha.getQtdLitros()));
        ((TextView) view.findViewById(R.id.etDt)).setText(String.valueOf(ordenha.getData()));
        ((TextView) view.findViewById(R.id.etHr)).setText(String.valueOf(ordenha.getHora()));



        return view;
    }

}
