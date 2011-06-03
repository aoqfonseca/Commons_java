package br.com.neoris.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

public class Conecta {
	
	public List test(String login){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();			
		}
		try {
			List modulos = new ArrayList();
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@cgrubd01.cgr.michelin.com:1521:PROD03", "bmmerctc", "mercmichelin");
			PreparedStatement st = conn.prepareStatement("select CD_MODULO_UMD from VSA_USUARIO_MODULO where upper(CD_USUARIO_UMD)='"+login.toUpperCase().trim()+"' and CD_SISTEMA_UMD='NM' ");			
			ResultSet result = st.executeQuery();
			while(result.next()){
				modulos.add(result.getString(1).trim().toUpperCase());
			}
			DbUtils.closeQuietly(result);
			DbUtils.closeQuietly(st);			
			return modulos;
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		return null;
	}

}
