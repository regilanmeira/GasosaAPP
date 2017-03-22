package br.com.regilan.gasosa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CustoAbastecimentoActivity extends AppCompatActivity {
    EditText txtValorAbastecer, txtPrecoCombustivel;
    Button btCalcular;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custo_abastecimento);

        txtPrecoCombustivel = (EditText)findViewById(R.id.txtPrecoCombustivel);
        txtValorAbastecer = (EditText)findViewById(R.id.txtValorAbastecer);
        btCalcular = (Button)findViewById(R.id.btCalcular);
        tvResultado = (TextView)findViewById(R.id.tvResultado);

        btCalcular.setOnClickListener(calcular);

    }

    View.OnClickListener calcular = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            double valor,preco,quantidade;




            try
            {

                if (txtPrecoCombustivel.getText().toString().equals("") || txtValorAbastecer.getText().toString().equals(""))
                {
                    Toast.makeText(getBaseContext(),R.string.msg_preencha_campos,Toast.LENGTH_LONG).show();

                }
                else
                {
                    valor = Double.parseDouble(txtValorAbastecer.getText().toString());
                    preco = Double.parseDouble(txtPrecoCombustivel.getText().toString());

                    Abastecimento abastecimento = new Abastecimento();
                    abastecimento.setValor_abastecer(valor);
                    abastecimento.setPreco_combustivel(preco);

                    quantidade = abastecimento.CalcularQuantidadeCustoAbastecimento();

                    DecimalFormat formatacao = new DecimalFormat("0.##");

                    tvResultado.setText(formatacao.format(quantidade) + " litros");
                }

            }
            catch (Exception ex)
            {
                Toast.makeText(getBaseContext(),R.string.msg_sem_resultado,Toast.LENGTH_LONG).show();
            }

        }
    };
}
