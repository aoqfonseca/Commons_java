package br.com.neoris.util.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class Planilha {

	public final static Cor AZUL = new Cor(HSSFColor.BLUE.index, HSSFColor.BLUE.index2);

	public final static Cor VERMELHO = new Cor(HSSFColor.RED.index);

	public final static Cor BRANCO = new Cor(HSSFColor.WHITE.index);

	public final static Cor PRETO = new Cor(HSSFColor.BLACK.index);

	public final static Cor AMARELO = new Cor(HSSFColor.YELLOW.index, HSSFColor.YELLOW.index2);

	private final Map cellStyles = new HashMap();

	private List folhas = new ArrayList();

	private HSSFWorkbook workbook = new HSSFWorkbook();

	private short colorSeed = HSSFColor.RED.index;

	// atributos

	private Cor corDaFonteDefault;

	private Cor corDeFundoDefault;

	private String fonteDefault;

	private int tamanhoDaFonteDefault = -1;
	
	public Folha criarFolha(String nome) {
		Folha ret = new Folha(nome);
		folhas.add(ret);
		return ret;
	}

	public Cor criarCor(int r, int g, int b) {
		workbook.getCustomPalette().setColorAtIndex(colorSeed, (byte) r, (byte) g, (byte) b);
		return new Cor(colorSeed++);
	}


	public InputStream salvar() {

		for (Iterator i = folhas.iterator(); i.hasNext();) {
			Folha folha = (Folha) i.next();
			HSSFSheet sheet = workbook.createSheet(folha.getNome());

			for (Iterator j = folha.linhas.keySet().iterator(); j.hasNext();) {
				Integer linha = (Integer) j.next();
				Iterator colunas = ((Map) folha.linhas.get(linha)).values().iterator();
				HSSFRow row = sheet.createRow(linha.shortValue() - 1);

				for (Iterator h = folha.larguraDasColunas.keySet().iterator(); h.hasNext();) {
					Integer coluna = (Integer) h.next();
					Integer largura = (Integer) folha.larguraDasColunas.get(coluna);
					sheet.setColumnWidth((short) (coluna.shortValue() - 1), largura.shortValue());
				}

				while (colunas.hasNext()) {
					Celula celula = (Celula) colunas.next();
					HSSFCell cell = row.createCell((short) (celula.getColuna().shortValue() - 1));

					// valor
					if (celula.getValor() instanceof Number) {
						cell.setCellValue(((Number) celula.getValor()).doubleValue());
					} else if (celula.getValor() instanceof Date) {
						cell.setCellValue((Date) celula.getValor());
					} else if (celula.getValor() instanceof Boolean) {
						cell.setCellValue(((Boolean) celula.getValor()).booleanValue());
					} else if (celula.getValor() != null) {
						cell.setCellValue(celula.getValor().toString());
					}

					if (celula.getFormula() != null) {
						cell.setCellFormula(celula.getFormula());
					}

					HSSFCellStyle cellStyle = getCellStyle(celula);
					cell.setCellStyle(cellStyle);
				}
			}
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			workbook.write(output);
		} catch (IOException e) {
			throw new NestableRuntimeException(e);
		}

		return new ByteArrayInputStream(output.toByteArray());
	}

	private HSSFCellStyle getCellStyle(Celula celula) {
		HSSFCellStyle cellStyle = (HSSFCellStyle) cellStyles.get(celula.estilo);

		if (cellStyle == null) {
			cellStyle = workbook.createCellStyle();

			HSSFFont font = workbook.createFont();
			if (celula.getFonte() != null) {
				font.setFontName(celula.getFonte());
			} else if (getFonteDefault() != null) {
				font.setFontName(getFonteDefault());
			}

			if (celula.getTamanhoDaFonte() >= 0) {
				font.setFontHeightInPoints((short) celula.getTamanhoDaFonte());
			} else if (getTamanhoDaFonteDefault() >= 0) {
				font.setFontHeightInPoints((short) getTamanhoDaFonteDefault());
			}

			if (celula.getCorDaFonte() != null) {
				font.setColor(celula.getCorDaFonte().cor1);
			} else if (getCorDaFonteDefault() != null) {
				font.setColor(getCorDaFonteDefault().cor1);
			}
			cellStyle.setFont(font);

			if (celula.getMascara() != null) {
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(celula.getMascara()));
			}

			if (celula.getCorDeFundo() != null) {
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(celula.getCorDeFundo().cor2);
			} else if (getCorDeFundoDefault() != null) {
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(getCorDeFundoDefault().cor2);
			}

			cellStyle.setWrapText(celula.getWarptext());

			cellStyles.put(celula.estilo, cellStyle);
		}

		return cellStyle;
	}

	public String getFonteDefault() {
		return fonteDefault;
	}

	public void setFonteDefault(String fonteDefault) {
		this.fonteDefault = fonteDefault;
	}

	public Cor getCorDaFonteDefault() {
		return corDaFonteDefault;
	}

	public Planilha setCorDaFonteDefault(Cor corDaFonteDefault) {
		this.corDaFonteDefault = corDaFonteDefault;
		return this;
	}

	public Cor getCorDeFundoDefault() {
		return corDeFundoDefault;
	}

	public Planilha setCorDeFundoDefault(Cor corDeFundoDefault) {
		this.corDeFundoDefault = corDeFundoDefault;
		return this;
	}

	public int getTamanhoDaFonteDefault() {
		return tamanhoDaFonteDefault;
	}

	public Planilha setTamanhoDaFonteDefault(int tamanhoDaFonteDefault) {
		this.tamanhoDaFonteDefault = tamanhoDaFonteDefault;
		return this;
	}
}
