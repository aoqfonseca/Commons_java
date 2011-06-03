package br.com.neoris.util;

import java.util.Collection;

/**
 * @author Andre Fonseca
 *
 * Classe com métodos uteis genéricos.
 */
public class Utils {
    
    /**
     * Método para adicionar um array de objetos a uma collection
     * @param collection Collection que será adicionada
     * @param obj Array de objetos a ser adicionado
     */
    public static void addToCollection(Collection collection, Object obj[]){
        for(int i=0;i<obj.length;i++){
            collection.add(obj[i]);
        }
    }

}
