package br.com.regilan.gasosa;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Regilan on 16/03/2017.
 */

public class VerificarDataTrocaOleoReceiver extends BroadcastReceiver {

    public int dia, mes, ano;
    public static final String NOME_ACAO = "TrocaOleo";

    @Override
    public void onReceive(Context context, Intent intent) {


        try
        {
            Intent janela = new Intent(context, TrocaOleoActivity.class);

            String dia, mes, ano;
            int diaAtual,mesAtual,anoAtual;
            //Restaura as preferencias gravadas
            SharedPreferences settings = context.getSharedPreferences("TrocaOleo", 0);

            dia = settings.getString("dia", "0");
            mes = settings.getString("mes", "0");
            ano = settings.getString("ano", "0");


            Calendar dataAtual = Calendar.getInstance();
            diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);
            mesAtual = dataAtual.get(Calendar.MONTH);
            mesAtual += 1;
            anoAtual = dataAtual.get(Calendar.YEAR);



            int dtAtual = 0,dtTroca=0;
            String dataAtualTexto = "",dataTrocaTexto= "";

            dataTrocaTexto = ano + mes + dia;
            dataAtualTexto = String.valueOf(anoAtual) + String.valueOf(mesAtual) + String.valueOf(diaAtual);


            dtTroca = Integer.parseInt(dataTrocaTexto);
            dtAtual = Integer.parseInt(dataAtualTexto);

            if (dtAtual >= dtTroca)
            {
                MensagemNotificacao m = new MensagemNotificacao(context, janela);

                m.exibirMensagem("Gasosa Informa", "Você tem uma data de troca de óleo programada para este dia");
            }

        }
        catch (Exception ex)
        {

        }


    }


}
