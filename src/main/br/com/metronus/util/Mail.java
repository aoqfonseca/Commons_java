package br.com.metronus.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Title Mail.java
 * <b> NEORIS - Brasil </b>
 * @author Andre Fonseca
 * Classe que representa a entidade e-mail para os sistemas metronus
 * Ela só manda email simples, sem anexos e no formato texto, para esta versão. Em versões futuras<bR>
 * pretende-se acertar isso
 * @version 0.8
 * 
 */
public class Mail {

	private String host;
	private String from;
	private String [] recipient;
	private String subject;
	private String mensagem;

	/**
	 * Cosntrutor 
	 * @param host host (servidor para envio) . Lembrando que usa-se um smtp
	 * @param from from 
	 * @param dest array de string com os destinatários
	 * @param assunto assunto da mensagem
	 * @param mensagem mensagem
	 */
	public Mail(String host, String from, String [] dest, String assunto, String mensagem){
		this.host = host;
		this.from = from;
		this.recipient = dest;
		this.subject =assunto;
		this.mensagem = mensagem;
	}
	
	/**
	 * Metodo para enviar um email com os parametros setados previamente
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send() throws AddressException, MessagingException {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		//Setando os parametros da mensagem
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		//Criando os endereços de envio
		InternetAddress[] address = new InternetAddress[recipient.length];
		for(int i=0;i<recipient.length;i++){
			 address[i] = new InternetAddress(recipient[i]); 
		}
		msg.setRecipients(Message.RecipientType.TO,address);
		msg.setSentDate(new Date());
		msg.setSubject(subject);
		msg.setText(mensagem);
		
		//Enviando a mensagem
		Transport.send(msg);
	}
	/**
	 * @return
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @return
	 */
	public String [] getRecipient() {
		return recipient;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param string
	 */
	public void setFrom(String string) {
		from = string;
	}

	/**
	 * @param string
	 */
	public void setHost(String string) {
		host = string;
	}

	/**
	 * @param string
	 */
	public void setMensagem(String string) {
		mensagem = string;
	}

	/**
	 * @param string
	 */
	public void setRecipients(String [] string) {
		recipient = string;
	}
	
	/**
	 * Metodo para setar um destinatario. Ele coloca este na ultima posição
	 * @param string
	 */
	public void setRecipient(String string){
		if(recipient == null || recipient.length < 1){
			recipient = new String[1];
		}
		recipient[(recipient.length -1)] = string;
	}

	/**
	 * @param string
	 */
	public void setSubject(String string) {
		subject = string;
	}

}
