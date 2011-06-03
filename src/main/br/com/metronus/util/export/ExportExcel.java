package br.com.metronus.util.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Classe que realizar a conversão de um arquivo Excel para um bean
 * @author Andre Fonseca
 */
public class ExportExcel {
	
	private HashMap mapping;
	private Class bean;
	private String path;
	private String methodName;
	private Log log = LogFactory.getLog(ExportExcel.class);
	private boolean pulaPrimeiraLinha = false;
	
	public ExportExcel(String bean, String file, boolean flag ) throws ClassNotFoundException {
		super();
		this.bean = Class.forName(bean);
		this.path = file;
		this.pulaPrimeiraLinha = flag;
		mapping = new HashMap();
		log.debug("Construtor do parser");
	}
	public ExportExcel(Class bean, String file) {
		super();
		this.bean = bean;
		this.path = file;
		mapping = new HashMap();
		log.debug("Construtor do parser");
	}
	/**
	 * Define o metodo a ser chamado caso não exista mapeamento para a coluna
	 * @param metodo String com o nome do metodo
	 */
	public void setGenericMethod(String metodo){
		this.methodName = metodo;
	}
	
	public Class getBean() {
		return bean;
	}

	public void setBean(Class bean) {
		this.bean = bean;
	}

	public String getFile() {
		return path;
	}

	public void setFile(String file) {
		this.path = file;
	}

	public ExportExcel(){		
	}
	
	public void clearMapping(){
        mapping.clear();
    }
    /**
     * Metodo para adicionar o mapeamento de um campo do VO para exportação.
     * <br>
     * Observe que a ordem que inserir as colunas será a ordem que será
     * exportado     * 
     * @param metodo metodo get do campo do VO ( o nome sem o get antes e em letra minuscula) Ex: <br> produto.getPreco(), você irá escrever preco.
     * @param coluna indice da coluna
     * @return String
     */
    public void addMapping(String metodo,int coluna) {
    	log.debug("Setando propriedades no metodo");
        mapping.put(new Integer(coluna),metodo);
    }
    /**
     * Metodo para setar o mapeamento de propriedades do VO a serem exportadas.
     * <br>
     * Observe que os valores devem ser string e conter o nome do metodo sem o
     * get antes e todo em minusculo.
     * @param list Collection de String com os nomes do metodos get a serem chamados para importaçao
     */
    public void setMapping(HashMap list) {
        mapping.putAll(list);
    }
    
    /**
     * Metodo para parsear os dados de um arquivo excel para uma coleção de beans através do mapeamento
     * informado
     *
     */
	public Collection unmarshall (){
		log.debug("Entrando no metodo de passagem do excel para um bean");
		File file   = new File(path);
		Collection resposta = new ArrayList();
		try {
			FileInputStream fis   = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet       = workbook.getSheetAt(0);
			
			String metodo         = null;
			Object obj            = null;
			HSSFRow row           = null;
			Iterator cellIterator = null;
			Object arg            = null; 
			Iterator rowIterator  = sheet.rowIterator();
			
			while(rowIterator.hasNext()){				
				obj = bean.newInstance();				
				row = (HSSFRow)rowIterator.next();
				if(row.getRowNum()== 0 && pulaPrimeiraLinha){
					continue;
				}
				cellIterator = row.cellIterator();			
				while(cellIterator.hasNext()){	                
	                HSSFCell cell = (HSSFCell)cellIterator.next();
	                metodo        = (String)mapping.get(new Integer(cell.getCellNum()));

	                //Verifica o tipo do argumento
	                switch(cell.getCellType()){
	                	case HSSFCell.CELL_TYPE_STRING:
	                		arg = cell.getStringCellValue();
	                		break;
	                	case HSSFCell.CELL_TYPE_NUMERIC:	
	                		arg = new Double(cell.getNumericCellValue());
	                		break;
	                	case HSSFCell.CELL_TYPE_BOOLEAN:
	                		arg = new Boolean(cell.getBooleanCellValue());
	                		break;	                	
	                	default:
	                		arg = cell.getStringCellValue();
	                }
	                
	                //Caso não exista um metodo mapeado ele chama o metodo generico
	                if(metodo == null){	                	
	                	Method method = obj.getClass().getDeclaredMethod(methodName,new Class[]{Object.class});
	                	method.invoke(obj,new Object[]{arg});
	                		                		                
	                }else{
	                	BeanUtils.setProperty(obj, metodo,arg);	                		                		               
	                }					
				}
				
				//Adicionando o objeto a coleção de resposta
				resposta.add(obj);
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {			
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return resposta;
	}

}
