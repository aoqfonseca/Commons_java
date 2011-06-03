package br.com.metronus.util;

import java.util.Collection;

/**
 * @author Andre Fonseca
 *
 * Classe com m�todos uteis gen�ricos.
 */
public class Utils {
    
    /**
     * M�todo para adicionar um array de objetos a uma collection
     * @param collection Collection que ser� adicionada
     * @param obj Array de objetos a ser adicionado
     */
    public static void addToCollection(Collection collection, Object obj[]){
        for(int i=0;i<obj.length;i++){
            collection.add(obj[i]);
        }
    }

}
