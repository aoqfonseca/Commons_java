package br.com.neoris.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Andre Fonseca
 * 
 * Classe que implementa o filenameFilter, filtrando para extensões
 */
public class ExtensionFileNameFilterImpl implements FilenameFilter {

    private String[] extension;    

    public ExtensionFileNameFilterImpl(String extension[]) {
        this.extension = extension;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File dir, String name) {
        boolean ret = false;                
        for (int i = 0; i < extension.length; i++) {            
            ret = ret || FileUtil.getFileExtension(name).trim().equalsIgnoreCase(extension[i].trim());
        }
        return ret;
    }

}
