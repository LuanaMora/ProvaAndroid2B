package com.android.provaandroid2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.provaandroid2b.R;
import com.android.provaandroid2b.model.MatrizLeiteira;

import java.util.List;

public class MatrizAdapter extends BaseAdapter {
    LayoutInflater myInflater;
    List<MatrizLeiteira> matrizList;


    public MatrizAdapter(Context context, List<MatrizLeiteira> matrizList) {
        this.matrizList = matrizList;
        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        MatrizLeiteira matrizLeiteira = matrizList.get(position);
        view = myInflater.inflate(R.layout.item_matriz, null);
        ((TextView) view.findViewById(R.id.etIdentificador)).setText(String.valueOf(matrizLeiteira.getIdentificador()));
        return view;
    }
}
