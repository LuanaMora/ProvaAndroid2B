package com.android.provaandroid2b;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.provaandroid2b.adapter.OrdenhaAdapter;
import com.android.provaandroid2b.model.Ordenha;

import java.util.List;

public class RelatorioActivity extends AppCompatActivity {
    private ListView lvRelatorio;

    private ArrayAdapter<Ordenha> adapterOrdenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvRelatorio = findViewById(R.id.lvRelatorio);
        loadList();

    }

    public void loadList() {
        List<Ordenha> ordenhaList = Ordenha.listAll(Ordenha.class);
        adapterOrdenha = new ArrayAdapter<>(RelatorioActivity.this, R.layout.support_simple_spinner_dropdown_item, ordenhaList);
        lvRelatorio.setAdapter(adapterOrdenha);

    }


}
