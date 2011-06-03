package br.com.metronus.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

import br.com.metronus.util.exception.TransactionException;

/**
 * @author Andre Fonseca
 * Classe que gerencia um transa��o do sistema
 */
public class Transaction implements javax.transaction.Transaction{
    
    public Collection transactionObjects;
    
    /**
     * Construtor da classe. Nele ser� inicializado a transa��o e a collection de objetos transacionais
     */
    public Transaction (){
        transactionObjects  = new ArrayList(); 
    }
    
    /**
     * Metodo para adicionar a transa��o, para ser gerenciado, um objeto de transa��o
     * @param object TransactionObject participante da transa��o
     */
    public Transaction add(javax.transaction.Transaction object){
        transactionObjects.add(object);
        return this;
    }
    
    /**
     * Metodo para commitar as altera��es feitas na transa��o
     */
    public void commit(){
        Iterator iterator = transactionObjects.iterator();
        javax.transaction.Transaction object = null;
        while(iterator.hasNext()){
            object = (javax.transaction.Transaction)iterator.next();
            try {
                object.commit();
            } catch (TransactionException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);
            } catch (SecurityException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);
            } catch (RollbackException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);                
            } catch (HeuristicMixedException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);
            } catch (HeuristicRollbackException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);
            } catch (SystemException e) {
                rollback();
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar commit",e);                
            }
        }        
    }
    
    /**
     * Metodo para realizar rollback em todas as altera��es feitas dentro desta transa��o
     */
    public void rollback() {
        Iterator iterator = transactionObjects.iterator();
        javax.transaction.Transaction object = null;
        while(iterator.hasNext()){
            object = (javax.transaction.Transaction)iterator.next();
            try {
                object.rollback();
            } catch (TransactionException e) {
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar rollback",e);
            } catch (SecurityException e) {

                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar rollback",e);
            } catch (SystemException e) {                
                e.printStackTrace();
                throw new TransactionException("Erro na tentativa de realizar rollback",e);                
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#delistResource(javax.transaction.xa.XAResource, int)
     */
    public boolean delistResource(XAResource arg0, int arg1) throws IllegalStateException, SystemException {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#enlistResource(javax.transaction.xa.XAResource)
     */
    public boolean enlistResource(XAResource arg0) throws RollbackException, IllegalStateException, SystemException {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#getStatus()
     */
    public int getStatus() throws SystemException {
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#registerSynchronization(javax.transaction.Synchronization)
     */
    public void registerSynchronization(Synchronization arg0) throws RollbackException, IllegalStateException, SystemException {
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#setRollbackOnly()
     */
    public void setRollbackOnly() throws IllegalStateException, SystemException {
    }

}
