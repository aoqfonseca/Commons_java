/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import br.com.neoris.util.exception.PropertyNotFoundException;

/**
 * Title LDAPAcess.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class LDAPAcess {
	
	private Hashtable env ;
	private String securityAuthentication ;
	private String providerUrl; 
	
	public LDAPAcess(){
	    super();
	}
    /**
     * @param securityAuthentication
     * @param providerUrl
     */
    public LDAPAcess(String securityAuthentication, String providerUrl) {
        super();
        this.securityAuthentication = securityAuthentication;
        this.providerUrl = providerUrl;
    }
	/**
	 * Metodo para conectar no LDAP definido no construtor ou no arquivo de propriedades
	 *
	 */
	public void connect() throws NamingException, PropertyNotFoundException{	   
		env = new Hashtable();
		env.put(Context.SECURITY_AUTHENTICATION, securityAuthentication);
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, providerUrl);
	}
	/**
	 * Metodo para verificar a existencia de um usuario (e sua senha) no LDAP
	 * @param usuario username 
	 * @param senha password cadastrado no ldap
	 * @throws NamingException Caso não encontre o usuario ou aconteça algum outro erro de acesso ao LDAP
	 */
	public void verifyUser(String usuario, String senha) throws NamingException {
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, senha);
		DirContext ctx = new InitialDirContext(env);
		ctx.close();
	}

}
