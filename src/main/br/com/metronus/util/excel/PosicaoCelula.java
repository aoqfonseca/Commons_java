package br.com.metronus.util.excel;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

class PosicaoCelula {

	private final Integer coluna;

	private final Integer linha;

	private final int hashCode;

	protected PosicaoCelula(final int linha, final int coluna) {
		this.linha = new Integer(linha);
		this.coluna = new Integer(coluna);

		this.hashCode = new HashCodeBuilder().append(linha).append(coluna).toHashCode();
	}

	public Integer getColuna() {
		return coluna;
	}

	public Integer getLinha() {
		return linha;
	}

	public boolean equals(Object o) {
		if ((o == null) || !(o instanceof PosicaoCelula)) return false;

		PosicaoCelula other = (PosicaoCelula) o;

		return (getLinha().equals(other.getLinha())) && (getColuna().equals(other.getColuna()));
	}

	public int hashCode() {
		return hashCode;
	}

	public String toString() {
		return new ToStringBuilder(this).append("linha", getLinha()).append("coluna", getColuna()).toString();
	}

}
