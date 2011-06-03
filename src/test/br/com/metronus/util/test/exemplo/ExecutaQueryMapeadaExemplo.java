package br.com.neoris.util.test.exemplo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.neoris.util.exception.DbConnectionException;
import br.com.neoris.util.exception.UndefiniedConnectionTypeException;
import br.com.neoris.util.sql.Session;
import br.com.neoris.util.sql.SessionFactory;

/** 
 * @author Andre Fonseca
 * 
 * Classe exemplo para o uso da api de acesso a banco, usando uma query mapeada
 */
public class ExecutaQueryMapeadaExemplo {
    public static void main(String[] args) {

    }

    public ExecutaQueryMapeadaExemplo() {
        super();
        try {
            init();
        } catch (UndefiniedConnectionTypeException e) {
            e.printStackTrace();
        } catch (DbConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws DbConnectionException
     * @throws UndefiniedConnectionTypeException
     * @throws SQLException
     *  
     */
    private void init() throws UndefiniedConnectionTypeException,
            DbConnectionException, SQLException {
        /*
         * Abrindo um conex�o com o banco de dados. Aqui o sistema ir� pegar a
         * conex�o cujo o nome seja default
         */
        Session sessao = SessionFactory.openSession();

        /*
         * Uma vez que a sess�o esteja criada e aberta vamos criar o
         * prepareStatament. A sess�o pode criar atrav�s de uma string ou um sql
         * mapeado num arquivo xml.
         * Aqui vamos criar um query a partir de um sql mapeado pelo xml. Para tanto vc tem dois metodos:
         * uma que vc passa apenas a chave do sql e outro que alem da chave vc passa um prefixo que indica o apelido
         * do arquivo xml onde est� a query mapeada.
         * Se vc n�o passa nada, ele procura no arquivo cujo o apelido seja default.
         */
        PreparedStatement sqlStatement = sessao.createMappingQuery("query_exemplo");

        ResultSet resultSet = sqlStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }

        //Agora vamos encerrar os objetos de conex�o
        resultSet.close();

        //Ao fechar a sess�o vc automaticamente encerra os preparedStatement
        // associados a ela .Tamb�m
        // fechamos a conex�o
        sessao.close();
    }
}
