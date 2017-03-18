package br.com.regilan.gasosa;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RodizioPneusActivity extends AppCompatActivity {

    EditText txtKmTrocaPneus;
    Button btRegistrar;
    TextView tvUltimoRodizio,tvProximoRodizio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rodizio_pneus);

        txtKmTrocaPneus = (EditText)findViewById(R.id.txtKmTrocaPneus);
        btRegistrar = (Button)findViewById(R.id.btRegistrar);
        tvUltimoRodizio = (TextView)findViewById(R.id.tvUltimoRodizio);
        tvProximoRodizio = (TextView)findViewById(R.id.tvProximoRodizio);

        btRegistrar.setOnClickListener(rodizioPneus);

        lerTrocaPneus();
    }

    View.OnClickListener rodizioPneus = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gravarTrocaPneus();
            lerTrocaPneus();
        }
    };
    public void gravarTrocaPneus()
    {


        try {

           int proximaTroca;

            proximaTroca = Integer.parseInt(txtKmTrocaPneus.getText().toString());
            proximaTroca += 10000;

            //Gravar no SharedPreferencer - Chave/Valor
            SharedPreferences settings = getSharedPreferences("TrocaPneus", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("kmUltimaTroca", txtKmTrocaPneus.getText().toString());
            editor.putString("kmProximaTroca", String.valueOf(proximaTroca));

            //Confirma a gravação dos dados
            editor.commit();

            Toast.makeText(getBaseContext(), "Registro gravado", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getBaseContext(), "Erro na gravação", Toast.LENGTH_LONG).show();
        }


    }

    public void lerTrocaPneus()
    {

        try
        {
            //Restaura as preferencias gravadas
            SharedPreferences settings = getSharedPreferences("TrocaPneus", 0);
            tvUltimoRodizio.setText(settings.getString("kmUltimaTroca", "0"));
            tvProximoRodizio.setText(settings.getString("kmProximaTroca", "0"));
        }
        catch (Exception ex)
        {

            tvUltimoRodizio.setText("0");
            tvProximoRodizio.setText("0");
        }



    }
}
