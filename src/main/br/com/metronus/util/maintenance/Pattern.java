package br.com.metronus.util.maintenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class Pattern {

	public Collection fixes = null;

	public Object pattern;

	/**
	 * construtor da classe pattern
	 * 
	 * @param pattern
	 */
	public Pattern(Object pattern) {
		this.pattern = pattern;
		this.fixes = new ArrayList();
	}

	/**
	 * Metodo para realizar a busca do pattern dentro do object passado
	 * 
	 * @param valor
	 */
	public abstract void find(Object valor);

	/**
	 * Metodo para adicionar um Fix para ser executado quando o pattern for
	 * encontrado no objeto
	 * 
	 * @param fix
	 *            Classe que implementa a interface Fix
	 */
	public void addFix(Fix fix) {
		fixes.add(fix);
	}

	/**
	 * Metodo para disparar todos fixes cadastrados
	 * 
	 */
	public void fireFixes() {
		Iterator iterator = fixes.iterator();
		Fix fix = null;
		while (iterator.hasNext()) {
			fix = (Fix) iterator.next();
			new FixThread(fix).start();
		}
	}

	class FixThread extends Thread {
		Fix fix;

		public FixThread(Fix fix) {
			this.fix = fix;
		}

		public void run() {
			fix.fixing();
		}
	}
}
