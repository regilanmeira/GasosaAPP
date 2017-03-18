/* REFERÊNCIA
http://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815 */

/* INCLUIR O NOME DO PACOTE DA APLICAÇÃO - EXEMPLO
 * package com.example.regilan.acessodados; */
package br.com.regilan.gasosa;

// IMPORTAÇÃO DAS BIBLIOTECAS
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Regilan on 20/06/2016.
 */
public class SQLiteConexao extends SQLiteOpenHelper {
    //TROCAR PELO NOME DO BANCO DE DADOS DA APLICAÇÃO
    private static String nomeDB = "abastecimentos.db";
    //NÚMERO DA VERSÃO, CASO SEJA ALTERADO O SCRIPT, DEVE-SE ALTERAR O NÚMERO DA VERSÃO
    private static int versao = 1;

    //CONSTRUTOR DA CLASSE QUE HERDA O CONSTRUTOR DA CLASSE SQLiteOpenHelper PARA CRIAR O BANCO DE DADOS
    public SQLiteConexao(Context context){
        super(context, nomeDB,null,versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Método onCreate(): é chamado quando a aplicação cria o banco de dados pela primeira vez.
        Nesse método devem ter todas as diretrizes de criação e população inicial do banco.*/

        try {

            //O objeto sql do tipo StringBuilder será usado para constuir a string com o comando SQL
            StringBuilder sql = new StringBuilder();

        /* Aqui criamos o código que será responsável por definir o esquema do banco de dados
        ou seja, criamos as tabelas que compõe o BD - CREATE TABLE ...
		NO ANDROID DEVEMOS TER UMA COLUNA _id EM CADA TABELA
         */
            sql.append("CREATE TABLE IF NOT EXISTS troca_oleo( " );
            sql.append("_id integer primary key autoincrement, ");
            sql.append("km_troca float, ");
            sql.append("data_troca varchar(20), ");
            sql.append("km_proxima_troca float, ");
            sql.append("data_proxima_troca varchar(20)) ");



            // Executa um comando SQL armazenado no objeto SQL
            db.execSQL(sql.toString());



            sql = new StringBuilder();

            sql.append("CREATE TABLE IF NOT EXISTS abastecimento ( " );
            sql.append("_id integer primary key autoincrement, ");
            sql.append("km_abastecimento dobule, ");
            sql.append("litros_abastecidos double, ");
            sql.append("data_abastecimento varchar(20) )");

            // Executa um comando SQL armazenado no objeto SQL
            db.execSQL(sql.toString());





        }
        catch (Exception ex)
        {

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*Método onUpgrade(): é o método responsável por atualizar o banco de dados com alguma informação estrutural que tenha sido alterada.
 Ele sempre é chamado quando uma atualização é necessária,
 para não ter nenhum tipo de inconsistência de dados entre o banco existente no aparelho e o novo que a aplicação irá utilizar.*/
        db.execSQL("DROP TABLE IF EXISTS abastecimento");
        onCreate(db);

    }
}
