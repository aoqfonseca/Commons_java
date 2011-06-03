package br.com.neoris.util.excel;


public class Celula {

	private PosicaoCelula pos;

	private Object valor;

	protected EstiloDeCelula estilo = new EstiloDeCelula();

	private String formula;

	protected Celula(PosicaoCelula pos) {
		this.pos = pos;
	}

	public Integer getColuna() {
		return pos.getColuna();
	}

	public Integer getLinha() {
		return pos.getLinha();
	}

	public Cor getCorDaFonte() {
		return estilo.corDaFonte;
	}

	public Celula setCorDaFonte(Cor corDaFonte) {
		estilo.corDaFonte = corDaFonte;
		return this;
	}

	public Cor getCorDeFundo() {
		return estilo.corDeFundo;
	}

	public Celula setCorDeFundo(Cor corDeFundo) {
		estilo.corDeFundo = corDeFundo;
		return this;
	}

	public Object getValor() {
		return valor;
	}

	public Celula setValor(Object valor) {
		this.valor = valor;
		return this;
	}

	public boolean getWarptext() {
		return estilo.warptext;
	}

	public Celula setWarptext(boolean warptext) {
		estilo.warptext = warptext;
		return this;
	}

	public String getFormula() {
		return formula;
	}

	public Celula setFormula(String formula) {
		this.formula = formula;
		return this;
	}

	public String getMascara() {
		return estilo.mascara;
	}

	public Celula setMascara(String mascara) {
		estilo.mascara = mascara;
		return this;
	}

	public String getFonte() {
		return estilo.fonte;
	}

	public Celula setFonte(String fonte) {
		estilo.fonte = fonte;
		return this;
	}

	public int getTamanhoDaFonte() {
		return estilo.tamanhoDaFonte;
	}

	public Celula setTamanhoDaFonte(int tamanhoFonte) {
		estilo.tamanhoDaFonte = tamanhoFonte;
		return this;
	}

	public boolean equals(Object o) {
		if ((o == null) || !(o instanceof Celula)) return false;

		Celula other = (Celula) o;

		return pos.equals(other.pos);
	}

	public int hashCode() {
		return pos.hashCode();
	}
}
