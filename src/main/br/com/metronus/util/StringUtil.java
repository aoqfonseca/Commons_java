package br.com.metronus.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Classe que encapsula todos os metodos util referente a manipulação de String
 * <b> NEORIS - Brasil </b>
 * 
 * @author Andre Fonseca
 * @version 1.0
 * 
 */
public final class StringUtil {

	/**
	 * Construtor da classe
	 */
	private StringUtil() {
	}
	/**
	 * Metodo para buscar uma expressão regular dentro de uma string
	 * @param valor String  na qual será procurado o pattern
	 * @param pattern String com a expressão regular a ser usada na procura
	 * @return
	 */
	public static boolean find(String valor,String pattern){
		return Pattern.compile(pattern).matcher(valor).find();
	}
	
	public static String concatStrings(Collection strings) {
		Iterator iterator = strings.iterator();
		StringBuffer buffer = new StringBuffer();
		while (iterator.hasNext()) {
			buffer.append((String) iterator.next());
		}
		return buffer.toString();
	}

	/**
	 * Metodo para retirar todos os caracteres de nova linha ,tab, etc
	 * 
	 * @param alterado
	 *            String a ser alterada
	 * @return String sem os caracteres especiais acima citados
	 */
	public static String retiraCaracteresEspeciais(String alterado) {
		return alterado.replaceAll("\t", "").replaceAll("\n", "");
	}

	/**
	 * Metodo para converter um String para inteiro
	 * 
	 * @param valor
	 *            String com o valor a ser convertido
	 * @return
	 * @throws NumberFormatException
	 */
	public static int parserInt(String valor) throws NumberFormatException {
		return Integer.parseInt(valor);
	}

	/**
	 * Metodo para converter um string para long
	 * 
	 * @param valor
	 *            string a ser convertida
	 * @return
	 * @throws NumberFormatException
	 */
	public static long parserLong(String valor) throws NumberFormatException {
		return Long.parseLong(valor);
	}

	/**
	 * Metodo para converter um string em um double
	 * 
	 * @param valor
	 *            String a ser convertido
	 * @return
	 * @throws NumberFormatException
	 */
	public static double parserDouble(String valor)
			throws NumberFormatException {
		return Double.parseDouble(valor);
	}

	/**
	 * Metodo para converter string e um float
	 * 
	 * @param valor
	 *            string com o valor a ser convertido
	 * @return
	 * @throws NumberFormatException
	 */
	public static float parserFloat(String valor) throws NumberFormatException {
		return Float.parseFloat(valor);
	}

	/**
	 * Metodo para converter um string numa java.util.Date
	 * 
	 * @param patern
	 *            formato de conversão
	 * @param valor
	 *            string a ser convertida
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date parserDateUtil(String patern, String valor)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return format.parse(valor);
	}

	/**
	 * Metodo para converter um string para um java.sql.Date
	 * 
	 * @param patern
	 *            formato de conversão
	 * @param valor
	 *            string a ser convertida
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date parserDateSql(String patern, String valor)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return new java.sql.Date(format.parse(valor).getTime());
	}

	/**
	 * Metodo para converter um string num timestamp. Primeiro ele converte para
	 * um java.util.Date e depois faz <br>
	 * um cache para timeStamp. Pode haver perda de informação
	 * 
	 * @param patern
	 *            formato de conversão
	 * @param valor
	 *            string a ser convertida
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp parserTimeStamp(String patern, String valor)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return new Timestamp(format.parse(valor).getTime());
	}

	/**
	 * Metodo para converte um java.util.Date para um string
	 * 
	 * @param date
	 *            java.util date a ser convertido
	 * @param patern
	 *            Formato padrão da conversão
	 * @return
	 */
	public static String valueOfDateUtil(java.util.Date date, String patern) {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return format.format(date);
	}

	/**
	 * Metodo para converter um java.sql.Date para String
	 * 
	 * @param date
	 *            java.sql.Date a ser convetido
	 * @param patern
	 *            formato padrão usado na conversão
	 * @return
	 */
	public static String valueOfDateSQl(java.sql.Date date, String patern) {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return format.format(date);
	}

	/**
	 * Metodo para converter um java.sql.Timestamp para string
	 * 
	 * @param time
	 *            Timestamp para ser convertido
	 * @param patern
	 *            formato padrão usado na conversão
	 * @return
	 */
	public static String valueOfTimeStamp(Timestamp time, String patern) {
		SimpleDateFormat format = new SimpleDateFormat(patern);
		return format.format(time);
	}

	/**
	 * Metodo para trocar um caracter de um string por outro caracter
	 * 
	 * @param valor
	 *            String a ser alterada
	 * @param caracterOld
	 *            caracter a ser mudado
	 * @param caracterNew
	 *            caracter que será colocado no lugar
	 * @return
	 */
	public static String replace(String valor, char caracterOld,
			char caracterNew) {
		return valor.replace(caracterOld, caracterNew);
	}

	/**
	 * Metodo para retirar um determinado caracter de um string. Cuidado com os
	 * caracteres usados nesta string <br>
	 * pois pode haver perda de informação (não usar ?)
	 * 
	 * @param valor
	 *            string a ser alterada
	 * @param c
	 * @return
	 */
	public static String removeChar(String valor, char c) {
		return valor.replace(' ', '?').replace(c, ' ').trim().replace('?', ' ');
	}

	/**
	 * Adicionar um espaoço branco a direita da string
	 * 
	 * @param valor
	 *            String a ser alterada
	 * @return
	 */
	public static String addOneSpaceRight(String valor) {
		return new String(" ").concat(valor);
	}

	/**
	 * Adicionar um espaço branco a esquerda da string
	 * 
	 * @param valor
	 *            String a ser alterada
	 * @return
	 */
	public static String addOneSpaceLeft(String valor) {
		return valor.concat(" ");
	}

	/**
	 * Adicionar um espaoço branco a direita da string
	 * 
	 * @param valor
	 *            String a ser alterada
	 * @param quantidade
	 *            numero de espaços a ser acrescentado
	 * @return
	 */
	public static String addSpaceRight(String valor, int quantidade) {
		return addCharRight(valor, ' ', quantidade);
	}

	/**
	 * Adicionar um espaço branco a esquerda da string
	 * 
	 * @param valor
	 *            String a ser alterada
	 * @param quantidade
	 *            numero de espaços a ser acrescentado
	 * @return
	 */
	public static String addSpaceLeft(String valor, int quantidade) {
		return addCharLeft(valor, ' ', quantidade);
	}

	/**
	 * Metodo para concatenar um caracter n vezes a direita de uma string
	 * 
	 * @param valor
	 *            string a ser alterada
	 * @param c
	 *            char a ser acrescentado
	 * @param quantidade
	 *            numero de vezes que o char será acrescentado
	 * @return
	 */
	public static String addCharRight(String valor, char c, int quantidade) {
		char[] caracter = new char[quantidade];
		for (int i = 0; i < quantidade; i++) {
			caracter[i] = c;
		}
		return valor.concat(new String(caracter));
	}

	/**
	 * Metodo para concatenar um caracter n vezes a esquerda de uma string
	 * 
	 * @param valor
	 *            string a ser alterada
	 * @param c
	 *            char a ser acrescentado
	 * @param quantidade
	 *            numero de vezes que o char será acrescentado
	 * @return
	 */
	public static String addCharLeft(String valor, char c, int quantidade) {
		char[] caracter = new char[quantidade];
		for (int i = 0; i < quantidade; i++) {
			caracter[i] = c;
		}
		return new String(caracter).concat(valor);
	}

	/**
	 * Metodo para retirar as letras de uma string
	 * 
	 * @param str
	 *            string a ser alterada
	 * @return String
	 */
	public static String retiraLetras(String str) {
		String letras = "[ABCDEFGHIJKLMNOPWQRSTUVXYZabcdefghijklmnopwqrstuvxyz]";
		StringBuffer saida = new StringBuffer();
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		char[] aux = str.toCharArray();
		for (int i = 0; i < aux.length; i++) {
			if (!new String(aux[i] + "").matches(letras))
				saida.append(aux[i]);
		}
		return saida.toString();
	}

	/**
	 * Remove uma string de outra string. Se não tiver a string retorna a string
	 * original.
	 * 
	 * @return string modificada
	 * @param str
	 *            string original
	 * @param strRemoved
	 *            string a ser removida
	 */
	public static String removeString(String str, String strRemoved) {
		if (str != null) {
			int pos = str.indexOf(strRemoved);
			if (pos != -1) {
				return str.substring(0, pos)
						+ str
								.substring(pos + strRemoved.length(), str
										.length());
			} else {
				return str;
			}
		} else {
			return null;
		}
	}

	/**
	 * Metodo para converter um double para um string no formato de dinheiro do
	 * brasil
	 * 
	 * @param valor
	 *            Double com o valor
	 * @return String
	 */
	public static String toMoneyBR(double valor) {
		DecimalFormat format = new DecimalFormat("R$ #,###.00");
		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setGroupingSeparator('.');
		sym.setDecimalSeparator(',');
		format.setDecimalFormatSymbols(sym);
		return format.format(valor);
	}

	/**
	 * Metodo para converter um long para um string no formato de dinheiro do
	 * brasil
	 * 
	 * @param valor
	 *            Double com o valor
	 * @return String
	 */
	public static String toMoneyBr(long valor) {
		DecimalFormat format = new DecimalFormat("R$ #,###.00");
		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setGroupingSeparator('.');
		sym.setDecimalSeparator(',');
		format.setDecimalFormatSymbols(sym);
		return format.format(valor);
	}

	/**
	 * Metodo para converter um double para um string no formato percentual.Ele
	 * antes vai multiplicar por 100
	 * 
	 * @param valor
	 * @return
	 */
	public static String percentual(double valor) {
		DecimalFormat format = new DecimalFormat("##.00%");
		return format.format(valor);
	}

	/**
	 * Metodo para converter um long para um string no formato percentual.Ele
	 * antes vai multiplicar por 100.<br>
	 * O patern utilizado é ##,##%(ex:100,00%)
	 * 
	 * @param valor
	 * @return
	 */
	public static String percentual(long valor) {
		DecimalFormat format = new DecimalFormat("#.00%");
		return format.format(valor);
	}

	/**
	 * Metodo para converter um double para string através patern passado
	 * 
	 * @param valor
	 *            double a ser convertido
	 * @param patern
	 *            string com o patern
	 * @see DecimalFormat
	 * @return String
	 */
	public static String formatNumber(double valor, String patern) {
		DecimalFormat format = new DecimalFormat(patern);
		return format.format(valor);
	}

	/**
	 * Metodo para converter um long para string através patern passado
	 * 
	 * @param valor
	 * @param patern
	 * @return
	 * @see DecimalFormat
	 */
	public static String formatNumber(long valor, String patern) {
		DecimalFormat format = new DecimalFormat(patern);
		return format.format(valor);
	}

	/**
	 * Metodo para substituir um string por outro dentro uma string
	 * 
	 * @param source
	 *            String que contem a parte a ser substituida
	 * @param old
	 *            String a ser substituida
	 * @param replace
	 *            String que irá substituir
	 * @return
	 */
	public static String replaceString(String source, String old, String replace) {
		return source.replaceFirst(old, replace);
	}
}
