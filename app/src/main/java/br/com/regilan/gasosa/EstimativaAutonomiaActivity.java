package br.com.regilan.gasosa;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EstimativaAutonomiaActivity extends AppCompatActivity {
    Spinner spPosicaoTanque;
    TextView tvResultado;
    CheckBox ckUsarEstimativaConsumo,ckGravarCapacidadeTanque;
    EditText txtCapacidadeTanque,txtEstimativaConsumo;
    Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimativa_autonomia);

        spPosicaoTanque = (Spinner)findViewById(R.id.spPosicaoTanque);
        tvResultado = (TextView)findViewById(R.id.tvResultado);

        ckUsarEstimativaConsumo = (CheckBox)findViewById(R.id.ckUsarEstimativaConsumo);
        ckGravarCapacidadeTanque = (CheckBox)findViewById(R.id.ckGravarCapacidadeTanque);
        txtCapacidadeTanque = (EditText)findViewById(R.id.txtCapacidadeTanque);
        txtEstimativaConsumo = (EditText)findViewById(R.id.txtEstimativaConsumo);

        btCalcular = (Button)findViewById(R.id.btCalcular);

        btCalcular.setOnClickListener(calcularAutonomia);
        ckGravarCapacidadeTanque.setOnCheckedChangeListener(gravarCapacidadeTanque);
        ckUsarEstimativaConsumo.setOnCheckedChangeListener(usarEstimativaConsumo);


        //preencher capacidade do tanque
        try
        {
            SharedPreferences settings = getSharedPreferences("Tanque",0);
            String capacidadeTanque = settings.getString("capacidade","");
            txtCapacidadeTanque.setText(capacidadeTanque);
        }
        catch (Exception ex)
        {
            txtCapacidadeTanque.setText("");
        }


        preencherSpinner();
    }


    View.OnClickListener calcularAutonomia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try
            {
                double estimativaKm;

                double capacidadeTanque = 0, tanqueAtual = 0, estimativaConsumo = 0;

                capacidadeTanque = Double.parseDouble(txtCapacidadeTanque.getText().toString());
                estimativaConsumo = Double.parseDouble(txtEstimativaConsumo.getText().toString());

                switch (spPosicaoTanque.getSelectedItem().toString())
                {
                    case "1/8":
                        tanqueAtual = 1.0 / 8.0;
                        break;
                    case "2/8":
                        tanqueAtual = 2.0 / 8.0;
                        break;
                    case "3/8":
                        tanqueAtual = 3.0 / 8.0;
                        break;
                    case "4/8":
                        tanqueAtual = 4.0 / 8.0;
                        break;
                    case "5/8":
                        tanqueAtual = 5.0 / 8.0;
                        break;
                    case "6/8":
                        tanqueAtual = 6.0 / 8.0;
                        break;
                    case "7/8":
                        tanqueAtual = 7.0 / 8.0;
                        break;
                    case "8/8":
                        tanqueAtual = 1;
                        break;
                    default:
                        tanqueAtual = 0;
                        break;

                }



                Abastecimento abastecimento = new Abastecimento();
                estimativaKm = abastecimento.calcularEstimativaConsumo(capacidadeTanque,tanqueAtual,estimativaConsumo);

                DecimalFormat formatacao = new DecimalFormat("#0.00");

                tvResultado.setText(formatacao.format(estimativaKm) + " km");


            }
            catch (Exception ex)
            {
                tvResultado.setText("0.00 km");
            }

        }
    };

    CompoundButton.OnCheckedChangeListener usarEstimativaConsumo = new CompoundButton.OnCheckedChangeListener() {
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

                    txtEstimativaConsumo.setText(valor);
                }
                else
                {
                    txtEstimativaConsumo.setText("");
                }
            }
            catch (Exception ex)
            {
                txtEstimativaConsumo.setText("0");
            }

        }
    };

    CompoundButton.OnCheckedChangeListener gravarCapacidadeTanque = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            SharedPreferences settings = getSharedPreferences("Tanque",0);
            String capacidadeTanque = settings.getString("capacidade","");


            if (isChecked == true)
            {

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("capacidade",txtCapacidadeTanque.getText().toString());
                editor.commit();

            }
            else
            {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("capacidade","");
                editor.commit();

            }


        }
    };

    public void preencherSpinner()
    {
        ArrayList<String> posicaoTanque = new ArrayList<>();

        posicaoTanque.add("8/8");
        posicaoTanque.add("7/8");
        posicaoTanque.add("6/8");
        posicaoTanque.add("5/8");
        posicaoTanque.add("4/8");
        posicaoTanque.add("3/8");
        posicaoTanque.add("2/8");
        posicaoTanque.add("1/8");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,posicaoTanque);

        spPosicaoTanque.setAdapter(adapter);

    }

}
