package br.com.neoris.util.test.exemplo;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import br.com.neoris.util.exception.DbConnectionException;
import br.com.neoris.util.exception.UndefiniedConnectionTypeException;
import br.com.neoris.util.sql.Session;
import br.com.neoris.util.sql.SessionFactory;

/**
 * @author Andre Fonseca
 * 
 * Classe exemplo de uso da api neoris-sql.jar
 */
public class ExecutaQueryExemplo {

	public static void main(String[] args) {
		new ExecutaQueryExemplo();
	}

	public ExecutaQueryExemplo() {
		super();
		try {
			init();
		} catch (UndefiniedConnectionTypeException e) {
			e.printStackTrace();
		} catch (DbConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws DbConnectionException
	 * @throws UndefiniedConnectionTypeException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * 
	 */
	private void init() throws UndefiniedConnectionTypeException,
			DbConnectionException, SQLException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		/*
		 * Abrindo um conexão com o banco de dados. Aqui o sistema irá pegar a
		 * conexão cujo o nome seja default
		 */
		Session sessao = SessionFactory.openSession();

		/*
		 * Uma vez que a sessão esteja criada e aberta vamos criar o
		 * prepareStatament. A sessão pode criar através de uma string ou um sql
		 * mapeado num arquivo xml Aqui vamos criar por uma string comum
		 */
		PreparedStatement sqlStatement = sessao
				.createQuery("select count(*) from pbadc.mq_ev_int");

		ResultSet resultSet = sqlStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getInt(1));
		}

		// Agora vamos encerrar os objetos de conexão
		resultSet.close();

		Collection list = sessao
				.executeQuery("select object_key as id, object_name as name from pbadc.mq_archive where object_name ='EAI_0105' and event_comment = 'SQL_AOQF_REPROC_20060404'",EventoTesteVO.class);		

		// Ao fechar a sessão vc automaticamente encerra os preparedStatement
		// associados a ela .Também
		// fechamos a conexão
		sessao.close();
	}

}

