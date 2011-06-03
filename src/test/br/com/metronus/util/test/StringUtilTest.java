/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.test;
import java.sql.Timestamp;
import java.text.ParseException;

import br.com.metronus.util.StringUtil;

import junit.framework.TestCase;

/**
 * Title StringUtilTest.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class StringUtilTest extends TestCase {

	/**
	 * Constructor for StringUtilTest.
	 * @param arg0
	 */
	public StringUtilTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(StringUtilTest.class);
	}

	public void testParserInt() {
		assertEquals(1234, StringUtil.parserInt("1234"));
		try {
			StringUtil.parserInt("andre");
			fail();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void testParserLong() {
		assertEquals(125, StringUtil.parserLong("125"));
		try {
			StringUtil.parserLong("andre");
			fail();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void testParserDouble() {
		assertEquals(12.34, StringUtil.parserDouble("12.34"), 0.0001);
		try {
			StringUtil.parserDouble("andre");
			fail();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void testParserFloat() {
		assertEquals(12.34, StringUtil.parserFloat("12.34"), 0.0001);
		try {
			StringUtil.parserFloat("andre");
			fail();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void testParserDateUtil() {
		try {
			assertEquals(
				new java.util.Date(105, 1, 4),
				StringUtil.parserDateUtil("dd/MM/yyyy", "04/02/2005"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			StringUtil.parserDateUtil("dd/MM/yyyy", "andre");
			fail("Deveria dar erro");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}

	public void testParserDateSql() {
		try {
			assertEquals(
				new java.sql.Date(105, 1, 4),
				StringUtil.parserDateSql("dd/MM/yyyy", "04/02/2005"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			StringUtil.parserDateSql("dd/MM/yyyy", "andre");
			fail("Deveria dar erro");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	public void testParserTimeStamp() {
		try {
			assertEquals(
				new Timestamp(105, 1, 4, 0, 0, 0, 0),
				StringUtil.parserTimeStamp("dd/MM/yyyy", "04/02/2005"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			StringUtil.parserTimeStamp("dd/MM/yyyy", "andre");
			fail("Deveria dar erro");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	public void testValueOfDateUtil() {
		assertEquals(
			"04/02/2005",
			StringUtil.valueOfDateUtil(
				new java.util.Date(System.currentTimeMillis()),
				"dd/MM/yyyy"));
	}

	public void testValueOfDateSQl() {
		assertEquals(
			"04/02/2005",
			StringUtil.valueOfDateSQl(
				new java.sql.Date(System.currentTimeMillis()),
				"dd/MM/yyyy"));
	}

	public void testValueOfTimeStamp() {
		assertEquals(
			"04/02/2005",
			StringUtil.valueOfTimeStamp(
				new Timestamp(System.currentTimeMillis()),
				"dd/MM/yyyy"));
	}

	public void testReplace() {
		assertEquals("teste2222",StringUtil.replace("teste1111",'1','2'));
		assertEquals("teste3333",StringUtil.replace("teste3333",'1','2'));
	}

	public void testRemoveChar() {
		assertEquals("teste",StringUtil.removeChar("teste1111",'1'));
		assertEquals("teste3333",StringUtil.removeChar("teste3333",'1'));
	}

	public void testAddOneSpaceRight() {
		assertEquals(" teste1111",StringUtil.addOneSpaceRight("teste1111"));			
	}

	public void testAddOneSpaceLeft() {
		assertEquals("teste1111 ",StringUtil.addOneSpaceLeft("teste1111"));
	}

	public void testAddSpaceRight() {
		assertEquals("   teste1111",StringUtil.addSpaceRight("teste1111",3));
	}

	public void testAddSpaceLeft() {
		assertEquals("teste1111   ",StringUtil.addSpaceLeft("teste1111",3));
	}

	public void testAddCharRight() {
		assertEquals("1teste",StringUtil.addCharRight("teste",'1',1));
	}

	public void testAddCharLeft() {
		assertEquals("teste1",StringUtil.addCharLeft("teste",'1',1));
	}

	public void testRetiraLetras() {
		assertEquals("1234",StringUtil.retiraLetras("teste1234"));
	}

	public void testRemoveString() {
		assertEquals("1234",StringUtil.removeString("teste1234","teste"));
	}

	public void testToMoneyBR() {
		assertEquals("R$ 1.000,00",StringUtil.toMoneyBR(1000));
	}

	public void testToMoneyBr() {
		assertEquals("R$ 1.000,00",StringUtil.toMoneyBR(1000));
	}

	/*
	 * Test for String percentual(double)
	 */
	public void testPercentualdouble() {
		assertEquals("100.00%",StringUtil.percentual(1));
	}

	/*
	 * Test for String percentual(long)
	 */
	public void testPercentuallong() {
		assertEquals("100.00%",StringUtil.percentual(1));
	}

	/*
	 * Test for String formatNumber(double, String)
	 */
	public void testFormatNumberdoubleString() {
		assertEquals("$1",StringUtil.formatNumber(1,"$#"));
	}

	/*
	 * Test for String formatNumber(long, String)
	 */
	public void testFormatNumberlongString() {
	}

}
