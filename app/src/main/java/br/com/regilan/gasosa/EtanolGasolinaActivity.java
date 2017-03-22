package br.com.regilan.gasosa;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class EtanolGasolinaActivity extends AppCompatActivity {
Button btCalcular;
    TextView tvResultado,tvTaxa;
    EditText txtGasolina,txtEtanol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etanol_gasolina);

        tvResultado = (TextView)findViewById(R.id.tvResultado);
        tvTaxa = (TextView)findViewById(R.id.tvTaxa);

        txtGasolina = (EditText)findViewById(R.id.txtGasolina);
        txtEtanol = (EditText)findViewById(R.id.txtEtanol);

        btCalcular = (Button)findViewById(R.id.btCalcular);
        btCalcular.setOnClickListener(calcular);

    }

    View.OnClickListener calcular = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            double etanol,gasolina,taxa;
            String opcao;


            try
            {
                if(txtEtanol.getText().toString().equals("") || txtGasolina.getText().toString().equals(""))
                {

                    Toast.makeText(getBaseContext(),R.string.msg_preencha_campos,Toast.LENGTH_LONG).show();


                }
                 else
                {
                    etanol = Double.parseDouble(txtEtanol.getText().toString());
                    gasolina = Double.parseDouble(txtGasolina.getText().toString());

                    Abastecimento abastecimento = new Abastecimento();
                    abastecimento.setEtanol(etanol);
                    abastecimento.setGasolina(gasolina);

                    opcao = abastecimento.CompararEtanolGasolina();


                    DecimalFormat formatacao = new DecimalFormat("#0.00");


                    tvResultado.setText(opcao);


                    tvTaxa.setText("Taxa de " + formatacao.format(abastecimento.getTaxa_etanol_gasolina()) + " % ");
                }

            }
            catch (Exception ex)
            {
                Toast.makeText(getBaseContext(),R.string.msg_sem_resultado,Toast.LENGTH_LONG).show();
            }



        }
    };
}
