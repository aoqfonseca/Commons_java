package br.com.metronus.util.excel;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EstiloDeCelula {

	public Cor corDaFonte;

	public Cor corDeFundo;

	public boolean warptext = true;

	public String mascara;

	public String fonte;

	public int tamanhoDaFonte = -1;

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
