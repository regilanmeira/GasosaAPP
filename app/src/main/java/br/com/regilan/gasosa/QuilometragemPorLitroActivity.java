package br.com.regilan.gasosa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class QuilometragemPorLitroActivity extends AppCompatActivity {
    EditText txtKmRodados, txtLitrosAbastecidos;
    TextView tvResultado;
    Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quilometragem_por_litro);

        txtKmRodados = (EditText) findViewById(R.id.txtKmRodados);
        txtLitrosAbastecidos = (EditText) findViewById(R.id.txtLitrosAbastecidos);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        btCalcular = (Button) findViewById(R.id.btCalcular);

        btCalcular.setOnClickListener(calcular);

    }

    View.OnClickListener calcular = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                double kmrodados, litros, kmporlitro;

                if (txtKmRodados.getText().toString().equals("") || txtLitrosAbastecidos.getText().toString().equals("")) {


                    Toast.makeText(getBaseContext(), R.string.msg_preencha_campos, Toast.LENGTH_LONG).show();

                } else {
                    kmrodados = Double.parseDouble(txtKmRodados.getText().toString());
                    litros = Double.parseDouble(txtLitrosAbastecidos.getText().toString());

                    Abastecimento abastecimento = new Abastecimento();
                    abastecimento.setKm_rodados(kmrodados);
                    abastecimento.setLitros_abastecidos(litros);


                    kmporlitro = abastecimento.CalcularQuilometragemPorLitro();

                    DecimalFormat formatacao = new DecimalFormat("#0.00");

                    tvResultado.setText(formatacao.format(kmporlitro) + " Km/l");
                }

            } catch (Exception ex) {
                Toast.makeText(getBaseContext(), R.string.msg_sem_resultado, Toast.LENGTH_LONG).show();
            }


        }
    };
}
