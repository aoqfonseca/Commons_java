/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import br.com.metronus.util.DateUtil;

/**
 * Title DateUtilTest.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class DateUtilTest extends TestCase {

	/**
	 * Constructor for DateUtilTest.
	 * @param arg0
	 */
	public DateUtilTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(DateUtilTest.class);
	}

	public void testGetIntervalo() {
		assertEquals(
			(24 * 3600 * 1000),
			DateUtil.getIntervalo(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 5)));
		try {
			DateUtil.getIntervalo(
				new java.util.Date(105, 1, 5),
				new java.util.Date(105, 1, 4));
			fail();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public void testGetIntervaloDia() {
		assertEquals(
			1,
			DateUtil.getIntervaloDia(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 5)));
	}

	public void testGetIntervaloSegundo() {
		assertEquals(
			24 * 3600,
			DateUtil.getIntervaloSegundo(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 5)));
	}

	public void testGetIntervaloMinuto() {
		assertEquals(
			24 * 60,
			DateUtil.getIntervaloMinuto(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 5)));
	}

	public void testGetIntervaloHoras() {
		assertEquals(
			24,
			DateUtil.getIntervaloHoras(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 5)));
	}

	/*
	 * Test for int getIntervaloDiaUteis(java.util.Date, java.util.Date)
	 */
	public void testGetIntervaloDiaUteisDateDate() {
		assertEquals(
			4,
			DateUtil.getIntervaloDiaUteis(
				new java.util.Date(105, 1, 4),
				new java.util.Date(105, 1, 9)));
	}

	public void testGetIntervaloDiaUteisDateDateFeriado() {
		Collection feriados = new ArrayList();		
		feriados.add(new GregorianCalendar(2005,1,7));		
		feriados.add(new GregorianCalendar(2005,1,8));		
		GregorianCalendar calendar = new GregorianCalendar(2005, 1, 4);
		GregorianCalendar calendar2 = new GregorianCalendar(2005,1,9);		
		assertEquals(2,DateUtil.getIntervaloDiaUteis(calendar, calendar2, feriados));

	}
	public void testGetCurrentDay(){
	    int day  = DateUtil.getCurrentDay();
	    assertEquals(new java.util.Date().getDate(),day);
	}
	public void testGetCurrentMonth(){
	    int month  = DateUtil.getCurrentMonth();
	    assertEquals(new java.util.Date().getMonth(),month);
	}
	public void testGetCurrentYear(){
	    int year = DateUtil.getCurrentYear();
	    assertEquals(new java.util.Date().getYear(),(year-1900));
	}
	public void testGetDay(){
	    int day = DateUtil.getDay(System.currentTimeMillis());
	    assertEquals(new java.util.Date().getDate(),day);
	}
	public void testGetMonth(){
	    int month = DateUtil.getMonth(System.currentTimeMillis());
	    assertEquals(new java.util.Date().getMonth(),month);
	}
	public void testGetYear(){
	    int year = DateUtil.getYear(System.currentTimeMillis());
	    assertEquals(new java.util.Date().getYear(),(year-1900));
	}

}
