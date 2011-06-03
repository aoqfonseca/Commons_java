package br.com.neoris.util.excel;

import java.util.HashMap;
import java.util.Map;

public class Folha {

	private String nome;

	protected Map linhas = new HashMap();

	protected Map larguraDasColunas = new HashMap();

	protected Folha(String nome) {
		this.nome = nome;
	}

	public Celula getCelula(int linha, int coluna) {
		if ((linha < 1) && (coluna < 1)) {
			return getCelula(1, 1);
		} else if (linha < 1) {
			return getCelula(1, coluna);
		} else if (coluna < 1) {
			return getCelula(linha, 1);
		}
		
		if (coluna > 255) {
			throw new RuntimeException("Você nâo pode criar uma coluna além da posição 255.");
		}

		PosicaoCelula pos = new PosicaoCelula(linha, coluna);

		Map celulas = (Map) linhas.get(pos.getLinha());
		if (celulas == null) {
			celulas = new HashMap();
			linhas.put(pos.getLinha(), celulas);
		}

		Celula celula = (Celula) celulas.get(pos.getColuna());

		if (celula == null) {
			celula = new Celula(pos);
			celulas.put(pos.getColuna(), celula);
		}

		return celula;
	}

	public Folha setLarguraColuna(int coluna, int largura) {
		if (coluna > -1) {
			larguraDasColunas.put(new Integer(coluna), new Integer(largura));
		}

		return this;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
