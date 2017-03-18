package br.com.regilan.gasosa;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AlertDialog alerta;
    ListView lvHistoricoAbastecimento;
    TextView tvAutonomia;
    Cursor cursorListView;
    int _idAbastecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent janela = new Intent(MainActivity.this, RegistrarAbastecimentoActivity.class);
                startActivity(janela);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        lvHistoricoAbastecimento = (ListView) findViewById(R.id.lvHistoricoAbastecimento);
        lvHistoricoAbastecimento.setOnItemClickListener(excluirAbastecimento);
        tvAutonomia = (TextView) findViewById(R.id.tvAutonomia);
        carregarListaAbastecimentos();

        calcularEstimativaAbastecimento();

    }

    public void calcularEstimativaAbastecimento() {
        double estimativaConsumo;
        Abastecimento abastecimento = new Abastecimento();

        try {
            estimativaConsumo = abastecimento.CalcularEstimativaConsumo(getBaseContext());
            Log.d("estimativa consumo", String.valueOf(estimativaConsumo));

        } catch (Exception ex) {
            estimativaConsumo = 0;
        }

        DecimalFormat formatacao = new DecimalFormat("#0.00");

        tvAutonomia.setText(formatacao.format(estimativaConsumo) + " km/l");
    }

    @Override
    protected void onStart() {
        super.onStart();

        carregarListaAbastecimentos();
        calcularEstimativaAbastecimento();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
           Intent janela = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(janela);






            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_registrar_abastecimento) {
            Intent janela = new Intent(MainActivity.this, RegistrarAbastecimentoActivity.class);
            startActivity(janela);
        } else if (id == R.id.nav_troca_oleo) {
            Intent janela = new Intent(MainActivity.this, TrocaOleoActivity.class);
            startActivity(janela);

        } else if (id == R.id.nav_rodizio_pneu)
        {
            Intent janela = new Intent(MainActivity.this,RodizioPneusActivity.class);
            startActivity(janela);
        }

        else if (id == R.id.nav_km_por_litro) {
            Intent janela = new Intent(MainActivity.this, QuilometragemPorLitroActivity.class);
            startActivity(janela);
        } else if (id == R.id.nav_custo_trajeto) {
            Intent janela = new Intent(MainActivity.this, CustoTrajetoActivity.class);
            startActivity(janela);

        } else if (id == R.id.nav_custo_abastecimento) {
            Intent janela = new Intent(MainActivity.this, CustoAbastecimentoActivity.class);
            startActivity(janela);

        }
        else if (id == R.id.nav_estimativa_autonomia) {
            Intent janela = new Intent(MainActivity.this, EstimativaAutonomiaActivity.class);
            startActivity(janela);

        }

        else if (id == R.id.nav_etanol_gasolina) {
            Intent janela = new Intent(MainActivity.this, EtanolGasolinaActivity.class);
            startActivity(janela);

        } else if (id == R.id.nav_sobre_app) {
            Intent janela = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(janela);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void carregarListaAbastecimentos() {
        String[] nomeCampos = new String[]{"_id", "km_abastecimento", "litros_abastecidos", "data_abastecimento"};
        int[] idViews = new int[]{R.id.tvIdAbastecimento, R.id.tvKmAbastecimento, R.id.tvLitrosAbastecimento, R.id.tvDataAbastecimento};


        Abastecimento abastecimento = new Abastecimento();
        cursorListView = abastecimento.listarAbastecimentoGeral(getBaseContext());

        try {
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                    R.layout.list_view_abastecimento, cursorListView, nomeCampos, idViews, 0);

            lvHistoricoAbastecimento.setAdapter(adaptador);
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }


    AdapterView.OnItemClickListener excluirAbastecimento = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            cursorListView.moveToPosition(position);
            _idAbastecimento = cursorListView.getInt(0);

            exibirCaixaDialogo();

        }
    };

    private void exibirCaixaDialogo() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Gasosa");
        //define a mensagem
        builder.setMessage("Confirma exclusão do abastecimento?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Abastecimento abastecimento = new Abastecimento(_idAbastecimento);
               if (abastecimento.removerAbastecimento(getBaseContext()) == true)
                {
                    Toast.makeText(MainActivity.this, "Abastecimento removido.", Toast.LENGTH_SHORT).show();
                    calcularEstimativaAbastecimento();
                    carregarListaAbastecimentos();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Erro ao remover o abastecimento.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}




