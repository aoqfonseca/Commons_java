package br.com.neoris.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import br.com.neoris.util.exception.TamanhoDoArquivoMaiorQueSuportadoException;


public class FileUtil {

	
    /**
	 * Recebe o nome do arquivo e retorna seu conteudo, removendo a extensao:
	 * teste.txt -> teste
	 * arquivo para rename -> arquivo para rename
	 * outro_teste.comPonto.xls -> outro_teste.comPonto
	 * @param fileName nome do arquivo
	 * @return nome sem a extensao, caso ela exista. Se receber null, retorna null.
	 */
	public static String getFileName(String fileName) {
		if(fileName == null) {
			return null;
		}
		if(fileName.equals("")) {
			return "";
		}
		String[] valores = fileName.split("\\.");

		if(valores.length == 1) {
			return fileName;
		}
		StringBuffer sbResult = new StringBuffer();
		for(int i=0;i<valores.length -1;i++){
		    if(i!=0)
		        sbResult.append(".");
		    sbResult.append(valores[i]);
		}		
		return sbResult.toString();
	}
	
	/**
	 * Recebe o nome do arquivo e retorna sua extensao. 
	 * @param fileName nome do arquivo
	 * @return Extensao do arquivo, sem o ponto. Caso nao tenha extensao, retorna String vazio. Se receber null, retorna null.
	 */
	public static String getFileExtension(String fileName) {
		if(fileName == null) {
			return null;
		}
		if(fileName.equals("")) {
			return "";
		}
		
		String[] valores = fileName.split("\\.");		
		if(valores.length == 1) {
			return "";
		}				
		return valores[valores.length-1];
	}
	/**
	 * Metodo para retornar todas os arquivos de um diretório com uma determinada extensão
	 * @param dirName String com o caminho do diretório a ser buscado
	 * @param extension Array de string com as extensões
	 * @param recursive indica se a busca deve ser feita nos subdiretorios(true) ou não (false)
	 * @return Collection de files com as extensões ou null caso não encontre nada
	 */
	public static Collection getFilesByExtension(String dirName,String [] extension,boolean recursive){
	    File dir = new File(dirName);
	    return FileUtil.getFilesByExtension(dir,extension,recursive);
	}
	/**
	 * Metodo para retornar todas os arquivos de um diretório com uma determinada extensão
	 * @param dirName String com o caminho do diretório a ser buscado
	 * @param extension Array de string com as extensões
	 * @param recursive indica se a busca deve ser feita nos subdiretorios(true) ou não (false)
	 * @return Collection de files com as extensões ou null caso não encontre nada
	 */
	public static Collection getFilesByExtension(File dir,String [] extension,boolean recursive){	    	    
	    Collection resposta = new ArrayList();
	    
	    if(!dir.isDirectory()){
	        throw new IllegalArgumentException("caminho de diretorio passado, não é válido");
	    }else if(extension == null || extension.length == 0){
	        throw new IllegalArgumentException("Array de extensão não é valido");
	    }
	    if(recursive){
	        File subFolder [] = FileUtil.getSubFolder(dir);	        
	        if(subFolder.length > 0){
	            for(int i=0;i<subFolder.length;i++){	                
	                resposta.addAll(FileUtil.getFilesByExtension(subFolder[i],extension,true));	                
	            }	            
	        }
	    }
	    Utils.addToCollection(resposta,
	                          dir.list(new ExtensionFileNameFilterImpl(extension)));	    
	    return resposta;
	}
	/**
	 * Método para retornar os subdiretorios de um diretorio
	 * @param dir File que representa o diretorio
	 * @return Arrays de Files
	 */
	public static File[] getSubFolder(File dir){
	    if(!dir.isDirectory()){
	        throw new IllegalArgumentException("A referencia File passada não representa um diretorio");
	    }
	    return dir.listFiles(new FileFilter(){
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
	    });
	}
	
	/**
	 * Retorna a representacao de um java.io.File em um byte[]
	 * @param file Arquivo a ser convertido
	 * @return byte[] representando o arquivo
	 * @throws IOException caso haja algum problema de leitura
	 * @throws TamanhoDoArquivoMaiorQueSuportadoException 
	 */
    public static byte[] getBytesFromFile(File file) throws IOException, TamanhoDoArquivoMaiorQueSuportadoException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new TamanhoDoArquivoMaiorQueSuportadoException("O arquivo é maior do que um byte[] pode suportar");
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("O arquivo '"+file.getName() + "' não pode ser lido por completo");
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

	
}
