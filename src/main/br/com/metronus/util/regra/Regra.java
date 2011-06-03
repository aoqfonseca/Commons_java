package br.com.neoris.util.regra;

import java.util.LinkedList;

import br.com.neoris.util.rules.Constraint;

/**
 * @author Andre Fonseca Classe que representa uma regra de negocio
 * 
 */
public abstract class Regra {
	
	private Contexto ctx = new Contexto();
	private LinkedList groupAnd;
	private LinkedList groupOr = new LinkedList();
	private boolean flag = true;

	public void registraObjetoNoContexto(String chave, Object obj) {
		ctx.addAtribute(chave, obj);
	}

	public void removeObjetoDoContexto(String chave) {
		ctx.remove(chave);
	}

	public Regra and(Constraint item) {
		if (groupAnd == null || groupAnd.isEmpty()) {
			groupAnd = new LinkedList();
		}
		groupAnd.add(item);
		return this;
	}

	public Regra or(Constraint item) {
		if (groupAnd != null && !groupAnd.isEmpty()) {
			groupOr.add(groupAnd);
			groupAnd = null;
		}
		and(item);
		return this;
	}

	/**
	 * M�todo que dispara a verifica��o da regra
	 */
	public boolean executar() {
		// Verifiacando se foi passado algum item de regra
		if (flag) {
			// Inicializando os objetos para uso na verifica��o da regra
			groupOr.add(groupAnd);
			flag = false;
		}
		if (groupOr == null) {
			throw new NullPointerException("System not found rules");
		}
		boolean orStatus = false;
		boolean andStatus;		
		Restricao item = null;
		// Iniciando a valida��o das regras
		for (int i = 0; i < groupOr.size(); i++) {
			groupAnd = (LinkedList) groupOr.get(i);
			andStatus = true;
			for (int j = 0; j < groupAnd.size(); j++) {
				item = (Restricao) groupAnd.get(j);				
				andStatus = andStatus && item.check();
				if((!andStatus) &&(item instanceof RestricaoSevera)) {
					fail();
				}									
			}
			orStatus = orStatus || andStatus;
		}
		// Chamando os metodos para tratar a consequencia
		if (orStatus) {
			success();
		} else {
			fail();
		}
		// Retornando o status da valida��o
		return orStatus;
	}

	/**
	 * Metodo chamado caso a regra n�o seja atendida
	 */
	public abstract void fail();

	/**
	 * Metodo chamado caso a regra seja atendida
	 * 
	 */
	public abstract void success();
}
