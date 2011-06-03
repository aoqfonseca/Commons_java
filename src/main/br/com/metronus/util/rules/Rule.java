package br.com.metronus.util.rules;

import java.util.LinkedList;

/**
 * @author Andre Fonseca
 *
 * Classe que representa um regra. A sua fun��o � validar um contexto
 * segundo a lista de item de regra.
 * A classe contem dois m�todos para que cadastrar um item de regra: or e and
 * Atrav�s destes dois metodos o desenvolvedor poder� estabelecer a forma de intera��o entre as regras. Seu funcionamento
 * basea-se em agrupamento. Cada and ele junto o item num grupo. Quando chamar um or ele fecha o grupo anterior e abre um novo
 * grupo de itens que se relacionam por and.
 * A ideia � usar sempre o modelo de express�o booleana: soma dos produtos (mapa de karnot) 
 * 
 */
public abstract class Rule {

    private LinkedList groupAnd;
    private LinkedList groupOr = new LinkedList();
    private Context ctx = null;
    private boolean flag = true;

    public Rule and(Constraint item){        
        if(groupAnd == null ||groupAnd.isEmpty()){
            groupAnd = new LinkedList();
        }
        groupAnd.add(item);
        return this;
    }
    
    public Rule or(Constraint item){        
        if(groupAnd != null && !groupAnd.isEmpty()){
            groupOr.add(groupAnd);
            groupAnd = null;    
        }
        and(item);
        return this;
    }
    /**
     * M�todo que dispara a verifica��o da regra
     */
    public boolean fire(Context ctx){
        this.ctx = ctx;
        //Verifiacando se foi passado algum item de regra
        if(flag){
            //Inicializando os objetos para uso na verifica��o da regra            
            groupOr.add(groupAnd);            
            flag= false;
        }
        if(groupOr == null){
            throw new NullPointerException("System not found rules");
        }        
        boolean orStatus = false;
        boolean andStatus ;
        Constraint item = null;
        //Iniciando a valida��o das regras
        for(int i=0;i<groupOr.size();i++){            
            groupAnd = (LinkedList) groupOr.get(i);
            andStatus = true;
            for(int j=0;j<groupAnd.size();j++){
                item = (Constraint) groupAnd.get(j);
                andStatus = andStatus && item.check(ctx);                 
            }            
            orStatus = orStatus || andStatus;
        }
        //Chamando os metodos para tratar a consequencia
        if(orStatus){
            success();            
        }else{
            fail();
        }
        //Retornando o status da valida��o
        return orStatus;
    }
    
    /**
     * Metodo para adicionar um object ao contexto atual da Regra 
     * @param key Objeto que servir� de chave
     * @param value Objeto a ser inserido no contexto ativo
     */
    public Rule assertObject(Object key, Object value){
        if(ctx == null){
            ctx = new Context();
        }
        ctx.addAtribute(key,value);
        return this;
    }
    /**
     * Dispara a verifica��o com o contexto j� existendo ou definido 
     * atrav�s dos m�todos assert do Rule
     * @return
     */
    public boolean fire(){
        if(ctx == null)
            throw new NullPointerException("Context is null or not valid");
        return fire(ctx);
    }
       
    /**
     * Metodo chamado caso a regra n�o seja atendida
     */
    public abstract void fail();
    /**
     * Metodo chamado caso a regra seja atendida
     *
     */
    public abstract void success();
}
