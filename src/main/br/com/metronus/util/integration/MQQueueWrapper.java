package br.com.neoris.util.integration;

public class MQQueueWrapper {
	
	private String nome;
	private String queueManager;
	private int tamanhoAtual;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQueueManager() {
		return queueManager;
	}
	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}
	public int getTamanhoAtual() {
		return tamanhoAtual;
	}
	public void setTamanhoAtual(int tamanhoAtual) {
		this.tamanhoAtual = tamanhoAtual;
	}
	
	

}
