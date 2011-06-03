package br.com.metronus.util.excel;

public interface NewRowEventSupport {

	public void newRow(int last, int now);
	public void finished(int last);
	public boolean continua();

}
