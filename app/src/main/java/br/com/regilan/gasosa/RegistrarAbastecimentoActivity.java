package br.com.regilan.gasosa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarAbastecimentoActivity extends AppCompatActivity {
    EditText txtQuilometragemAtual, txtQuantidadeCombustivel;
    DatePicker txtDataAbastecimento;
    Button btRegistrar;
    double quantidade, quilometragem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_abastecimento);

        txtDataAbastecimento = (DatePicker) findViewById(R.id.txtDataAbastecimento);
        txtQuantidadeCombustivel = (EditText) findViewById(R.id.txtQuantidadeCombustivel);
        txtQuilometragemAtual = (EditText) findViewById(R.id.txtQuilometragemAtual);

        btRegistrar = (Button) findViewById(R.id.btRegistrar);
        btRegistrar.setOnClickListener(registrar);
    }

    View.OnClickListener registrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            try {
                quantidade = Double.parseDouble(txtQuantidadeCombustivel.getText().toString());
                quilometragem = Double.parseDouble(txtQuilometragemAtual.getText().toString());

                StringBuilder dataabastecimento = new StringBuilder();
                dataabastecimento.append(String.valueOf(txtDataAbastecimento.getDayOfMonth()));
                dataabastecimento.append(" de ");


                int mes = txtDataAbastecimento.getMonth() + 1;

                switch (mes) {
                    case 1:
                        dataabastecimento.append("jan");
                        break;
                    case 2:
                        dataabastecimento.append("fev");
                        break;
                    case 3:
                        dataabastecimento.append("mar");
                        break;
                    case 4:
                        dataabastecimento.append("abr");
                        break;
                    case 5:
                        dataabastecimento.append("mai");
                        break;
                    case 6:
                        dataabastecimento.append("jun");
                        break;
                    case 7:
                        dataabastecimento.append("jul");
                        break;
                    case 8:
                        dataabastecimento.append("ago");
                        break;
                    case 9:
                        dataabastecimento.append("set");
                        break;
                    case 10:
                        dataabastecimento.append("out");
                        break;
                    case 11:
                        dataabastecimento.append("nov");
                        break;
                    case 12:
                        dataabastecimento.append("dez");
                        break;
                    default:
                        dataabastecimento.append("");
                        break;
                }


                Abastecimento abastecimento = new Abastecimento(quilometragem, quantidade, dataabastecimento.toString());

                if (abastecimento.registrarAbastecimento(getBaseContext()) == true) {
                    Toast.makeText(getBaseContext(), "Abastecimento registrado!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Erro no registro do abastecimento!", Toast.LENGTH_LONG).show();
                }


                try
                {
                    verificarRodizioPneus(quilometragem);
                    verificarTrocaOleo(quilometragem);
                }
                catch (Exception ex)
                {

                }

            } catch (Exception ex) {
                Toast.makeText(getBaseContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }


        }
    };


    public void verificarRodizioPneus(double km)
    {
        SharedPreferences settings = getSharedPreferences("TrocaPneus", 0);

        String kmRodizioShared;
        double kmRodizio;


        kmRodizioShared = settings.getString("kmProximaTroca","0");

        kmRodizio = Double.parseDouble(kmRodizioShared);

        if (km >= kmRodizio)
        {
            Intent janela = new Intent(RegistrarAbastecimentoActivity.this,RodizioPneusActivity.class);

            MensagemNotificacao m = new MensagemNotificacao(getBaseContext(),janela);

            m.exibirMensagem("Gasosa Informa", "Você tem um rodízio de pneus programado para a quilometragem atual");

        }

    }

    public void verificarTrocaOleo(double km)
    {
        SharedPreferences settings = getSharedPreferences("TrocaOleo", 0);

        String kmTrocaOleooShared;
        double kmTrocaOleoo;


        kmTrocaOleooShared = settings.getString("kmProximaTroca","0");

        kmTrocaOleoo = Double.parseDouble(kmTrocaOleooShared);

        if (km >= kmTrocaOleoo)
        {
            Intent janela = new Intent(RegistrarAbastecimentoActivity.this,RodizioPneusActivity.class);

            MensagemNotificacao m = new MensagemNotificacao(getBaseContext(),janela);

            m.exibirMensagem("Gasosa Informa", "Você tem uma troca de óleo programado para a quilometragem atual.");

        }
    }
}
