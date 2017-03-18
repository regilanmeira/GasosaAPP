package br.com.regilan.gasosa;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Regilan on 13/03/2017.
 */

public class Abastecimento {

    //ATRIBUTOS PRIVADOS
    private double valor_abastecer, preco_combustivel, autonomia, distancia, etanol, gasolina, km_rodados, litros_abastecidos;
    private double km_atual, quantidade_combustivel;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private int _id;
    private double taxa_etanol_gasolina;
    String data_abastecimento;

    /* *********************************** INÍCIO GETS E SETS ***********************************************************/
    public double getTaxa_etanol_gasolina() {
        return taxa_etanol_gasolina;
    }
    public double getValor_abastecer() {
        return valor_abastecer;
    }

    public void setValor_abastecer(double valor_abastecer) {
        this.valor_abastecer = valor_abastecer;
    }

    public double getPreco_combustivel() {
        return preco_combustivel;
    }

    public void setPreco_combustivel(double preco_combustivel) {
        this.preco_combustivel = preco_combustivel;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getEtanol() {
        return etanol;
    }

    public void setEtanol(double etanol) {
        this.etanol = etanol;
    }

    public double getGasolina() {
        return gasolina;
    }

    public void setGasolina(double gasolina) {
        this.gasolina = gasolina;
    }

    public double getKm_rodados() {
        return km_rodados;
    }

    public void setKm_rodados(double km_rodados) {
        this.km_rodados = km_rodados;
    }

    public double getLitros_abastecidos() {
        return litros_abastecidos;
    }

    public void setLitros_abastecidos(double litros_abastecidos) {
        this.litros_abastecidos = litros_abastecidos;
    }

    public double getKm_atual() {
        return km_atual;
    }

    public void setKm_atual(double km_atual) {
        this.km_atual = km_atual;
    }

    public double getQuantidade_combustivel() {
        return quantidade_combustivel;
    }

    public void setQuantidade_combustivel(double quantidade_combustivel) {
        this.quantidade_combustivel = quantidade_combustivel;
    }

    public String getData_abastecimento() {
        return data_abastecimento;
    }

    public void setData_abastecimento(String data_abastecimento) {
        this.data_abastecimento = data_abastecimento;
    }


    /* *********************************** FIM GETS E SETS ***********************************************************/

    /* *********************************** INICIO CONSTRUTORES ***********************************************************/
    public Abastecimento(double valor_abastecer, double preco_combustivel) {
        this.valor_abastecer = valor_abastecer;
        this.preco_combustivel = preco_combustivel;
    }

    public Abastecimento(double preco_combustivel, double autonomia, double distancia) {
        this.preco_combustivel = preco_combustivel;
        this.autonomia = autonomia;
        this.distancia = distancia;
    }

    public Abastecimento() {

    }

    public Abastecimento(int id)
    {
        this._id = id;
    }

    public Abastecimento(double valor_abastecer, double preco_combustivel, double autonomia, double distancia, double etanol, double gasolina, double km_rodados, double litros_abastecidos, double km_atual, double quantidade_combustivel, String data_abastecimento) {
        this.valor_abastecer = valor_abastecer;
        this.preco_combustivel = preco_combustivel;
        this.autonomia = autonomia;
        this.distancia = distancia;
        this.etanol = etanol;
        this.gasolina = gasolina;
        this.km_rodados = km_rodados;
        this.litros_abastecidos = litros_abastecidos;
        this.km_atual = km_atual;
        this.quantidade_combustivel = quantidade_combustivel;
        this.data_abastecimento = data_abastecimento;
    }

    public Abastecimento(double km_rodados, double litros_abastecidos, String data_abastecimento) {
        this.km_rodados = km_rodados;
        this.litros_abastecidos = litros_abastecidos;
        this.data_abastecimento = data_abastecimento;
    }

    /* *********************************** FIM CONSTRUTORES ***********************************************************/



    /* *********************************** INICIO MÉTODOS ***********************************************************/

    public double calcularEstimativaConsumo(double capacidadeTanque,double percentualTanque,double autonomiaVeiculo)
    {

        double totalTanqueCombustivel = 0;
        double kmEstimadoAutonomia;

        try
        {
            totalTanqueCombustivel = capacidadeTanque * percentualTanque;


            kmEstimadoAutonomia = totalTanqueCombustivel * autonomiaVeiculo;


        }
        catch (Exception ex)
        {
            kmEstimadoAutonomia = 0;
        }

        return kmEstimadoAutonomia;

    }

    public double CalcularQuantidadeCustoAbastecimento() {

        try {
            double quantidadeLitros;

            quantidadeLitros = valor_abastecer / preco_combustivel;

            return quantidadeLitros;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double CalcularCustoTrajeto() {
        double custo;
        try {
            custo = distancia / autonomia * preco_combustivel;
            return custo;
        } catch (Exception ex) {
            return 0;
        }

    }

    public String CompararEtanolGasolina() {
        double taxa;

        try {
            taxa_etanol_gasolina = 100 * etanol / gasolina;
            DecimalFormat formatacao = new DecimalFormat("#0.00");

            if (taxa_etanol_gasolina <= 70) {
                return "ETANOL";
            } else {
                return "GASOLINA";
            }
        } catch (Exception ex) {
            return "";
        }

    }

    public double CalcularQuilometragemPorLitro()
    {

      double kmporlitro;
        try {

            kmporlitro = km_rodados / litros_abastecidos;
            return kmporlitro;
        }
        catch (Exception ex) {
            return 0;

        }
    }

    public boolean registrarAbastecimento(Context c) {

        DecimalFormat formatacao = new DecimalFormat("0.00");


        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO abastecimento(km_abastecimento,litros_abastecidos,data_abastecimento) ");
        sql.append("VALUES ( ");
        sql.append("'" + km_rodados + "',");
        sql.append("'" + formatacao.format(litros_abastecidos) + "',");
        sql.append("'" + data_abastecimento + "')");

        SQLiteComando comando = new SQLiteComando(c);

        return comando.executar(sql.toString());
    }

    public boolean removerAbastecimento(Context c)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM abastecimento WHERE ");
        sql.append("_id = '" + _id + "'");


        SQLiteComando comando = new SQLiteComando(c);

        return comando.executar(sql.toString());
    }

    public Cursor listarAbastecimentoGeral(Context c)
    {
        SQLiteComando comandos = new SQLiteComando(c);


        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM abastecimento ");
        sql.append("ORDER BY _id DESC");

        Cursor cursor = comandos.retornar(sql.toString());

        return  cursor;
    }

    public double CalcularEstimativaConsumo(Context c)
    {
        double estimativa_consumo, kminicial, kmfinal, litros_abastecidos;
        SQLiteComando comando = new SQLiteComando(c);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM abastecimento ORDER BY _id DESC LIMIT 2");


        try
        {
            Cursor cursor = comando.retornar(sql.toString());

            cursor.moveToFirst();
            kmfinal = cursor.getDouble(1);
            litros_abastecidos = cursor.getDouble(2);

            cursor.moveToNext();
            kminicial = cursor.getDouble(1);


            estimativa_consumo = (kmfinal - kminicial) / litros_abastecidos;

            return  estimativa_consumo;
        }
        catch (Exception ex)
        {
            return 0;
        }

    }


}
