package br.com.regilan.gasosa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TrocaOleoActivity extends AppCompatActivity {
    EditText txtKmTrocaOleo,txtKmProximaTroca;
    DatePicker txtDataTrocaOleo,txtDataProximaTrocaOleo;
    Button btRegistrar;
    ListView lvTrocaOleo;
    TextView tvDataProximaTroca,tvKmProximaTroca,tvDataUltimaTroca,tvKmUltimaTroca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_oleo);

        txtKmTrocaOleo = (EditText)findViewById(R.id.txtKmTrocaOleo);
        txtDataTrocaOleo = (DatePicker) findViewById(R.id.txtDataTrocaOleo);
        txtKmProximaTroca = (EditText)findViewById(R.id.txtKmProximaTroca);
        txtDataProximaTrocaOleo = (DatePicker)findViewById(R.id.txtDataProximaTrocaOleo);

        lvTrocaOleo = (ListView)findViewById(R.id.lvTrocaOleo);
        btRegistrar = (Button) findViewById(R.id.btRegistrar);

        tvDataProximaTroca = (TextView)findViewById(R.id.tvDataProximaTroca);
        tvKmProximaTroca = (TextView)findViewById(R.id.tvKmProximaTroca);
        tvDataUltimaTroca = (TextView)findViewById(R.id.tvDataUltimaTroca);
        tvKmUltimaTroca = (TextView)findViewById(R.id.tvKmUltimaTroca);

        btRegistrar.setOnClickListener(registrar);

        lerTrocaOleo();

    }

    View.OnClickListener registrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            gravarTrocaOleo();
            lerTrocaOleo();
            gerarAlarmeTrocaOleo();

        }
    };


    public void gravarTrocaOleo()
    {


        try {

            StringBuilder dataUltimaTroca = new StringBuilder();
            dataUltimaTroca.append(String.valueOf(txtDataTrocaOleo.getDayOfMonth()));
            dataUltimaTroca.append(" de ");


            int mes = txtDataTrocaOleo.getMonth() + 1;

            switch (mes) {
                case 1:
                    dataUltimaTroca.append("jan ");
                    break;
                case 2:
                    dataUltimaTroca.append("fev ");
                    break;
                case 3:
                    dataUltimaTroca.append("mar ");
                    break;
                case 4:
                    dataUltimaTroca.append("abr ");
                    break;
                case 5:
                    dataUltimaTroca.append("mai ");
                    break;
                case 6:
                    dataUltimaTroca.append("jun ");
                    break;
                case 7:
                    dataUltimaTroca.append("jul ");
                    break;
                case 8:
                    dataUltimaTroca.append("ago ");
                    break;
                case 9:
                    dataUltimaTroca.append("set ");
                    break;
                case 10:
                    dataUltimaTroca.append("out ");
                    break;
                case 11:
                    dataUltimaTroca.append("nov ");
                    break;
                case 12:
                    dataUltimaTroca.append("dez ");
                    break;
                default:
                    dataUltimaTroca.append("");
                    break;
            }

            dataUltimaTroca.append(String.valueOf(txtDataTrocaOleo.getYear()));

            StringBuilder dataProximaTroca = new StringBuilder();
            dataProximaTroca.append(String.valueOf(txtDataProximaTrocaOleo.getDayOfMonth()));
            dataProximaTroca.append(" de ");


            int dia = txtDataProximaTrocaOleo.getDayOfMonth();
            int mesProximaTroca = txtDataProximaTrocaOleo.getMonth() + 1;
            int ano = txtDataProximaTrocaOleo.getYear();

            switch (mesProximaTroca) {
                case 1:
                    dataProximaTroca.append("jan ");
                    break;
                case 2:
                    dataProximaTroca.append("fev ");
                    break;
                case 3:
                    dataProximaTroca.append("mar ");
                    break;
                case 4:
                    dataProximaTroca.append("abr ");
                    break;
                case 5:
                    dataProximaTroca.append("mai ");
                    break;
                case 6:
                    dataProximaTroca.append("jun ");
                    break;
                case 7:
                    dataProximaTroca.append("jul ");
                    break;
                case 8:
                    dataProximaTroca.append("ago ");
                    break;
                case 9:
                    dataProximaTroca.append("set ");
                    break;
                case 10:
                    dataProximaTroca.append("out ");
                    break;
                case 11:
                    dataProximaTroca.append("nov ");
                    break;
                case 12:
                    dataProximaTroca.append("dez ");
                    break;
                default:
                    dataProximaTroca.append("");
                    break;
            }

            dataProximaTroca.append(String.valueOf(txtDataProximaTrocaOleo.getYear()));






            //Gravar no SharedPreferencer - Chave/Valor
            SharedPreferences settings = getSharedPreferences("TrocaOleo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("dataUltimaTroca", dataUltimaTroca.toString());
            editor.putString("kmUltimaTroca", txtKmTrocaOleo.getText().toString());
            editor.putString("dataProximaTroca", dataProximaTroca.toString());
            editor.putString("kmProximaTroca", txtKmProximaTroca.getText().toString());

            if (dia < 10)
            {

                editor.putString("dia","0" + String.valueOf(dia));
            }
            else
            {

                editor.putString("dia",String.valueOf(dia));
            }

            if (mesProximaTroca < 10)
            {
                editor.putString("mes","0" + String.valueOf(mesProximaTroca));
            }
            else
            {
                editor.putString("mes",String.valueOf(mesProximaTroca));
            }


            editor.putString("ano",String.valueOf(ano));






            //Confirma a gravação dos dados
            editor.commit();

            Toast.makeText(getBaseContext(), "Registro gravado", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getBaseContext(), "Erro na gravação", Toast.LENGTH_LONG).show();
        }


    }


    public void gerarAlarmeTrocaOleo()
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND,30);

        long tempo= c.getTimeInMillis();

        Intent receptor = new Intent(VerificarDataTrocaOleoReceiver.NOME_ACAO);

        Alarme.agendarComRepeticao(getBaseContext(),receptor,tempo,3600 * 1000);
    }
    public void lerTrocaOleo()
    {
        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences("TrocaOleo", 0);
        tvKmProximaTroca.setText(settings.getString("kmProximaTroca", ""));
        tvDataProximaTroca.setText(settings.getString("dataProximaTroca", ""));

        tvKmUltimaTroca.setText(settings.getString("kmUltimaTroca", ""));
        tvDataUltimaTroca.setText(settings.getString("dataUltimaTroca", ""));
    }
}
