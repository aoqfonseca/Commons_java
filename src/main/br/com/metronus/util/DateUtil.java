/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 * Title DateUtil.java
 * <b> NEORIS - Brasil </b>
 * @author Andre Fonseca
 * @version 1.0
 * 
 */
public class DateUtil {

	/**
	 * Metodo para calcular o intervalo em MiliSegundos entre duas datas.A data fim deve ser maior que a data inicio
	 * @param inicio Data inicial
	 * @param fim Data final 
	 * @return numero de miisegundos entre as duas datas
	 */
	public static long getIntervalo(java.util.Date inicio, java.util.Date fim) {
		if (fim.compareTo(inicio) < 0) {
			throw new RuntimeException("Data fim menor que data inicio");
		}
		return (fim.getTime() - inicio.getTime());
	}

	/**
	 * Metodo para calcular o numero de dias entre duas datas. A data fim deve ser maior que a data inicio 
	 * @param inicio data inicial
	 * @param fim data final
	 * @return inteiro de numero de dias entre elas ( sem forem no mesmo dia retorna 0)
	 */
	public static int getIntervaloDia(java.util.Date inicio, java.util.Date fim) {
		long intervalo = getIntervalo(inicio, fim);
		return (int) (intervalo / (24*3600*1000));
	}

	/**
	 * Metodo para calcular o numero de segundos entre duas datas
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static int getIntervaloSegundo(java.util.Date inicio, java.util.Date fim) {
		long intervalo = getIntervalo(inicio, fim);
		return (int) (intervalo / (1000));
	}
	/**
	 * Metodo para calcular o numero de minutos entre duas datas
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static int getIntervaloMinuto(java.util.Date inicio, java.util.Date fim) {
		long intervalo = getIntervalo(inicio, fim);
		return (int) (intervalo / (60 * 1000));
	}
	/**
	 * Metodo para calcular o numero de horas entre duas datas
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static int getIntervaloHoras(java.util.Date inicio, java.util.Date fim) {
			long intervalo = getIntervalo(inicio, fim);
			return (int) (intervalo / (3600 * 1000));
	}

	/**
	 * Metodo para calcular o numero de dias uteis entre duas datas.
	 * @param inicio java.util.Date inicial
	 * @param fim java.util.Date final
	 * @return
	 */
	public static int getIntervaloDiaUteis(
		java.util.Date inicio,
		java.util.Date fim) {
		GregorianCalendar calendarInicial = new GregorianCalendar();
		calendarInicial.setTime(inicio);
		GregorianCalendar calendarFinal = new GregorianCalendar();
		calendarFinal.setTime(fim);
		return getIntervaloDiaUteis(calendarInicial, calendarFinal);
	}

	/**
	 * Metodo para calcular o numero de dias uteis entre duas datas
	 * @param inicio Calendar date
	 * @param fim Calendar date
	 * @return
	 */
	public static int getIntervaloDiaUteis(Calendar initialDate, Calendar finalDate) {
		return getIntervaloDiaUteis(initialDate, finalDate, new ArrayList());
	}

	/**
	 * Metodo para o numero de dias uteis entre duas datas,considerando possiveis feriados
	 * @param initialDate 
	 * @param finalDate
	 * @param feriados Collection de feriados
	 * @return
	 */
	public static int getIntervaloDiaUteis(	Calendar initialDate,Calendar finalDate,Collection feriados) {
		int dayOfWeek, dayOfMonth;
		int result = 0;
		while (initialDate.before(finalDate) || initialDate.equals(finalDate)) {
			dayOfWeek = initialDate.get(Calendar.DAY_OF_WEEK);
			dayOfMonth = initialDate.get(Calendar.DAY_OF_MONTH);			
			switch (dayOfWeek) {
				case 2 : // Segunda
				case 3 : // Terça
				case 4 : // Quarta
				case 5 : // Quinta
				case 6 : // Sexta                     
					if (!feriados.contains(initialDate))
						result++;	
					break;
				case 1 : // Domingo                      
				case 7 : // Sabado					   
					break;
			}
			initialDate.set(Calendar.DAY_OF_MONTH,dayOfMonth+1);					
		}
		return result;
	}
	/**
	 * Método para retornar o dia corrente
	 * @return int com o dia corrente
	 */
	public static int getCurrentDay(){
	    Calendar calendar = new GregorianCalendar();	    
	    return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * Método para retornar o mês corrente
	 * @return int com mes corrente (lembrando que é janeiro é zero)
	 */
	public static int getCurrentMonth(){
	    Calendar calendar = new GregorianCalendar();	    
	    return calendar.get(Calendar.MONTH);
	}
	/**
	 * Metodo para retornar o ano corrente
	 * @return int com o valor do ano corrente
	 */
	public static int getCurrentYear(){
	    Calendar calendar = new GregorianCalendar();	    
	    return calendar.get(Calendar.YEAR);
	}
	/**
	 * metodo para retorna o dia de uma data
	 * @param date long com timeMilis da data
	 * @return int com o valor do dia da data
	 */
	public static int getDay(long date){
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(new java.util.Date(date));
	    return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * metodo para retorna o mes de uma data
	 * @param date long com timeMilis da data
	 * @return int com o valor do mes da data
	 */
	public static int getMonth(long date){
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(new java.util.Date(date));
	    return calendar.get(Calendar.MONTH);
	}
	/**
	 * metodo para retorna o ano de uma data
	 * @param date long com timeMilis da data
	 * @return int com o valor do ano da data
	 */
	public static int getYear(long date){
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(new java.util.Date(date));
	    return calendar.get(Calendar.YEAR);
	}

}
