package br.com.regilan.gasosa;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CustoTrajetoActivity extends AppCompatActivity {
EditText txtAutonomia,txtDistanciaTrajeto,txtPrecoCombustivel;
    TextView tvResultado;
    Button btCalcular;
    CheckBox ckUsarHistoricoConsumo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custo_trajeto);

        txtAutonomia = (EditText)findViewById(R.id.txtAutonomia);
        txtDistanciaTrajeto = (EditText)findViewById(R.id.txtDistanciaTrajeto);
        txtPrecoCombustivel = (EditText)findViewById(R.id.txtPrecoCombustivel);

        tvResultado = (TextView)findViewById(R.id.tvResultado);

        ckUsarHistoricoConsumo = (CheckBox)findViewById(R.id.ckUsarHistoricoConsumo);
        ckUsarHistoricoConsumo.setOnCheckedChangeListener(usar_autonomia);

        btCalcular = (Button)findViewById(R.id.btCalcular);
        btCalcular.setOnClickListener(calcular);
    }

    View.OnClickListener calcular = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try
            {
                double autonomia,distancia,preco,custo;

                if (txtAutonomia.getText().toString().equals("") || txtAutonomia.getText().toString().equals("") || txtAutonomia.getText().toString().equals("")) {

                    Toast.makeText(getBaseContext(),R.string.msg_preencha_campos,Toast.LENGTH_LONG).show();

                }
                else
                {
                    autonomia = Double.parseDouble(txtAutonomia.getText().toString());
                    distancia = Double.parseDouble(txtDistanciaTrajeto.getText().toString());
                    preco = Double.parseDouble(txtPrecoCombustivel.getText().toString());

                    Abastecimento abastecimento = new Abastecimento(preco, autonomia, distancia);

                    custo = abastecimento.CalcularCustoTrajeto();

                    DecimalFormat formatacao = new DecimalFormat("#0.00");

                    tvResultado.setText("R$ " + formatacao.format(custo));
                }

            }
            catch (Exception ex)
            {
                Toast.makeText(getBaseContext(),R.string.msg_sem_resultado,Toast.LENGTH_LONG).show();
            }

        }
    };

    CompoundButton.OnCheckedChangeListener usar_autonomia = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Abastecimento abastecimento = new Abastecimento();
            double estimativaConsumo;

            try
            {
                estimativaConsumo = abastecimento.CalcularEstimativaConsumo(getBaseContext());

                if (isChecked)
                {
                    DecimalFormat formatacao = new DecimalFormat("#0.00");

                    String valor = formatacao.format(estimativaConsumo);
                    valor = valor.replace(",",".");

                    txtAutonomia.setText(valor);
                }
                else
                {
                    txtAutonomia.setText("");
                }
            }
            catch (Exception ex)
            {
                txtAutonomia.setText("0");
            }

        }
    };

}
