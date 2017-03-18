/* REFERÊNCIA
http://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815 */

/* INCLUIR O NOME DO PACOTE DA APLICAÇÃO - EXEMPLO
 * package com.example.regilan.acessodados; */
package br.com.regilan.gasosa;

// IMPORTAÇÃO DAS BIBLIOTECAS
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Regilan on 20/06/2016.
 */
public class SQLiteComando {
    //OBJETOS NECESSÁRIOS PARA CRIAR A BASE DE DADOS
    private SQLiteDatabase db;
    private SQLiteConexao banco;

    //Construtor da classe que cria ou abre um banco de dados
    public SQLiteComando(Context context)
    {
        banco = new SQLiteConexao(context);
    }

    //Método para executar um comando INSERT, DELETE, UPDATE e retorna true ou false
    public boolean executar(String sql)
    {
        try {

            //Abre o banco e modo escrita de dados
            db = banco.getWritableDatabase();
            //Executa um comando SQL recebido por parâmetro
            db.execSQL(sql);
            //Fecha o banco de dados
            db.close();

            //Retorna true quando o comando SQL(INSERT, DELETE e UPDATE) foi executado corretamente no banco de dados
            return true;
        }
        catch (Exception ex) {
            //Retorna false quando ocorreu algum erro no comando TRY
            return false;
        }
    }

    //Método executa um comando do tipo SELECT e retorna um  CURSOR com os dados da consulta
    public Cursor retornar(String sql)
    {
        //O objeto Cursor guarda os dados de uma consulta SQL no banco de dados
        Cursor dados = null;

        try
        {
            //Abre o banco em modo leitura
            db = banco.getReadableDatabase();

            //Executa um comando SELCECT na base dados e guarda os valores em dados
            dados = db.rawQuery(sql, null);

            //Verifica se o curso está vazio, ou seja se o SELECT retornou dados
            if(dados!=null){
                dados.moveToFirst();
            }

            //Fecha a conexão de dados
            db.close();

            //Retorna o cursor com os dados
            return dados;

        }
        catch (Exception ex)
        {
            //Em caso de erro na execução de qualquer comando do TRY, o método retorna um cursor vazio(sem dados)
            return  dados;
        }

    }
}
