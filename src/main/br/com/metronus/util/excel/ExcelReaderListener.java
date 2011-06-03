package br.com.metronus.util.excel;

public interface ExcelReaderListener {

	public void read(int row, int col, String value);

	public void read(int row, int col, double value);

	public void read(int row, int col, boolean value);

}
