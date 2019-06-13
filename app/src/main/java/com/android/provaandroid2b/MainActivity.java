package com.android.provaandroid2b;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.provaandroid2b.model.MatrizLeiteira;
import com.android.provaandroid2b.model.Ordenha;
import com.android.provaandroid2b.util.Mensagem;
import com.android.provaandroid2b.util.TipoMensagem;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ArrayList<MatrizLeiteira> vacaList = new ArrayList<MatrizLeiteira>();


    private EditText etIdentificador, etQtLitros, etDt, etHr;
    private Spinner spMatrizLeiteira;
    private Button btSalvar, btCancelar;
    private int day, month, year;
    private int hour, minute;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ListView lvOrdenha;
    private Ordenha ordenha = null;
    private ArrayAdapter<Ordenha> adapterOrdenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SugarContext.init(this);
        iniciaComponentes();
        populaMatrizLeiteira();
        limpaCampos();
        loadList();


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        datePickerDialog = new DatePickerDialog(MainActivity.this, this, year, month, day);
        timePickerDialog = new TimePickerDialog(MainActivity.this, this, hour, minute, true);

        eventosBotao();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    public void iniciaComponentes() {
        etIdentificador = findViewById(R.id.etIdentificador);
        etQtLitros = findViewById(R.id.etQtdLitros);
        spMatrizLeiteira = findViewById(R.id.spMatrizLeiteira);
        btSalvar = findViewById(R.id.btSalvar);
        btCancelar = findViewById(R.id.btCancelar);
        etDt = findViewById(R.id.etDt);
        etHr = findViewById(R.id.etHr);
        lvOrdenha = findViewById(R.id.lvOrdenha);


        etDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        etHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

    }

    public void eventosBotao() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        lvOrdenha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ordenha = (Ordenha) adapterOrdenha.getItem(position);
                btSalvar.setText(R.string.lbAtualizar);
                edit(position);
                carregarCampos();
            }
        });

        lvOrdenha.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position);
                return true;
            }
        });
    }

    private void carregarCampos() {
        etIdentificador.setText(String.valueOf(ordenha.getIdentificador()));
        etDt.setText(ordenha.getData());
        etQtLitros.setText(String.valueOf(ordenha.getQtdLitros()));
        etHr.setText(ordenha.getHora());
    }

    private int delete(final int position) {
        AlertDialog.Builder alertConfirmacao = new AlertDialog.Builder(MainActivity.this);
        alertConfirmacao.setTitle("Confirmação Exclusão");
        alertConfirmacao.setMessage("Deseja Realmente excluir o registro?");
        alertConfirmacao.setNeutralButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ordenha ordenha = (Ordenha) lvOrdenha.getItemAtPosition(position);
                ordenha.delete();
                loadList();
            }
        });
        alertConfirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                loadList();

            }
        });
        alertConfirmacao.show();
        return 0;
    }


    private void edit(int position){
        carregarCampos();
        Ordenha ordenaUpdate = (Ordenha) lvOrdenha.getItemAtPosition(position);
        ordenaUpdate.update();
        loadList();
    }


    public void populaMatrizLeiteira() {
        MatrizLeiteira mLeiteira = new MatrizLeiteira();
        mLeiteira.setIdentificador(1);
        mLeiteira.setDescricao("Mimosa");
        mLeiteira.setIdade(3);
        mLeiteira.setDtUltimoParto(new Date());
        vacaList.add(mLeiteira);

        mLeiteira = new MatrizLeiteira();
        mLeiteira.setIdentificador(2);
        mLeiteira.setDescricao("Carinhosa");
        mLeiteira.setIdade(5);
        mLeiteira.setDtUltimoParto(new Date());
        vacaList.add(mLeiteira);

        System.out.println("TSTE" + new Date());
        System.out.println(vacaList.toString());

        loadSpinnerMatrizLeiteira();

    }

    public void save() {
        Ordenha ordenha = new Ordenha();

        ordenha.setIdentificador(Integer.parseInt(etIdentificador.getText().toString()));
        ordenha.setMatrizLeiteira((MatrizLeiteira) spMatrizLeiteira.getSelectedItem());
        ordenha.setQtdLitros(Double.parseDouble(etQtLitros.getText().toString()));
        ordenha.setData(etDt.getText().toString());
        ordenha.setHora(etHr.getText().toString());
        ordenha.save();
        Mensagem.ExibirMensagem(MainActivity.this, "Ordenha salva com Sucesso!", TipoMensagem.SUCESSO);
        limpaCampos();
        loadList();
    }

    public void loadSpinnerMatrizLeiteira() {
        List<MatrizLeiteira> matrizList = MatrizLeiteira.listAll(MatrizLeiteira.class);
        ArrayAdapter matrizAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,
                vacaList);
        spMatrizLeiteira.setAdapter(matrizAdapter);

    }

    public void limpaCampos() {
        Ordenha last = Ordenha.last(Ordenha.class);

        if (last == null) {
            etIdentificador.setText("1");
        } else {
            etIdentificador.setText(String.valueOf(last.getIdentificador() + 1));
        }
        etQtLitros.setText("");
        etDt.setText("");
        etHr.setText("");
    }

    public void loadList() {
        List<Ordenha> ordenhaList = Ordenha.listAll(Ordenha.class);
        adapterOrdenha = new ArrayAdapter<Ordenha>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,
                ordenhaList);
        lvOrdenha.setAdapter(adapterOrdenha);
        limpaCampos();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_relatorio) {
            Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Mensagem.ExibirMensagem(MainActivity.this, "Data Selecionada (" + day + "/ " + month + "/ " + year + ")", TipoMensagem.ALERTA);
        etDt.setText(day + "/ " + (month + 1) + "/ " + year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etHr.setText(hourOfDay + ":" + minute);
    }
}
