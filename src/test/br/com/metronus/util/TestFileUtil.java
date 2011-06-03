package br.com.neoris.util;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

public class TestFileUtil extends TestCase {

	/*
	 * Test method for 'br.com.neoris.util.FileUtil.getFileName(String)'
	 */
	public void testGetFileName() {
		 
		assertNull(FileUtil.getFileName(null));
		assertEquals("", FileUtil.getFileName(""));
		assertEquals("teste", FileUtil.getFileName("teste.txt"));
		assertEquals("arquivo para rename", FileUtil.getFileName("arquivo para rename"));
		assertEquals("outro_teste.comPonto", FileUtil.getFileName("outro_teste.comPonto.xls"));
		assertEquals("outro_teste", FileUtil.getFileName("outro_teste.comPonto"));

	}

	/*
	 * Test method for 'br.com.neoris.util.FileUtil.getFileExtension(String)'
	 */
	public void testGetFileExtension() {
		
		assertNull(FileUtil.getFileExtension(null));
		assertEquals("", FileUtil.getFileExtension(""));
		assertEquals("", FileUtil.getFileExtension("teste"));
		assertEquals("", FileUtil.getFileExtension("arquivo para rename"));
		assertEquals("xls", FileUtil.getFileExtension("outro_teste.comPonto.xls"));
		assertEquals("properties", FileUtil.getFileExtension("outro_teste.comPonto.properties"));
	}
	
	public void testeGetFileByExtension(){
	    Collection arquivos = FileUtil.getFilesByExtension("D:\\Dados\\temp\\test1",new String []{"txt"},true);	    
	    Iterator iterator = arquivos.iterator();
	    while(iterator.hasNext()){
	        assertEquals("andre.txt", (String)iterator.next());
	    }
	    if(arquivos == null ||arquivos.isEmpty()){
	        fail();   
	    }
	    assertEquals(2,arquivos.size());
	    try{
	        FileUtil.getFilesByExtension("",new String []{"txt"},true);
	        FileUtil.getFilesByExtension("D:\\Dados\\temp\\test1\\test2",null,true);
	        fail();
	    }catch(IllegalArgumentException e){   }
	}

}
