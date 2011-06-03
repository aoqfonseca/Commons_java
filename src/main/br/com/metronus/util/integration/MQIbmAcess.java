package br.com.metronus.util.integration;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

/**
 * @author Andre Fonseca
 * 
 * Classe que encapsula a entidade de um fila MQ
 */
public class MQIbmAcess implements MQAccess {

    private MQQueueManager manager;
    private HashMap filas;
    private HashMap listener;
    private HashMap threads;
    private String queueManagerName;
    private Log log = LogFactory.getLog(MQIbmAcess.class);

    /**
     * Construtor do objeto
     * 
     * @param host
     *            String com o host ou ip do queue manager
     * @param channel
     *            String com o canal
     * @param port
     *            int com o numero da porta onde o queuemanager está escutando
     * @param queueManager
     *            String com o nome do queuemanager
     */
    public MQIbmAcess(String host, String channel, int port, String queueManager) {
        //Definindo as propriedades do
        MQEnvironment.hostname = host;
        MQEnvironment.channel = channel;
        MQEnvironment.port = port;
        this.queueManagerName = queueManager;

        MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,
                MQC.TRANSPORT_MQSERIES);
        filas = new HashMap();
        listener = new HashMap();
    }

    /**
     * construtor para realizar acesso local da máquina onde está o qm
     * 
     * @param queueManager
     *            String com o nome do queue manager
     */
    public MQIbmAcess(String queueManager) {
        this.queueManagerName = queueManager;
        filas = new HashMap();
        listener = new HashMap();
    }

    public void connect() {
        try {
            this.manager = new MQQueueManager(queueManagerName);
        } catch (MQException e) {
            log.error("Erro na tentativa de conexão com o queuemanager :CompletionCode :"
                      + e.completionCode + ", Reason Code"
                      + e.reasonCode, e);
        }
    }

    /**
     * Método para enviar uma mensagem para uma fila
     * 
     * @param message
     *            String com o conteúdo da mensagem
     * @param queueName
     *            String com o nome da fila
     * @param options
     *            int com as opções da fila
     */
    public void send(String message, String queueName, int options) {
        MQQueue queue = (MQQueue) filas.get(queueName);
        if (queue == null) {
            try {
                queue = manager.accessQueue(queueName, options);
                filas.put(queueName, queue);
            } catch (MQException e) {
                log.error(
                                "Erro durante a tentativa de acesso a fila. CompletionCode:"
                                        + e.completionCode + ", Reason:"
                                        + e.reasonCode, e);
            }
        } else {
            queue.openOptions = options;
        }
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        pmo.options = pmo.options + MQC.MQPMO_NEW_MSG_ID;
        pmo.options = pmo.options + MQC.MQPMO_SYNCPOINT;

        //Criando o objeto message
        MQMessage mqMessage = new MQMessage();

        try {
            mqMessage.writeString(message);

            //Definindo o formato da mensagem
            mqMessage.format = MQC.MQFMT_STRING;

            //colocando a mensagem na fila
            queue.put(mqMessage, pmo);

        } catch (IOException e) {
            log.error("Erro durante a tentativa de escrita da mensagem", e);
        } catch (MQException e) {
            log.error("Erro durante a tentativa de colocar uma mensagem na fila. CompletionCode:"
                      + e.completionCode + ", Reason:" + e.reasonCode, e);
        }
    }

    /**
     * Método para enviar uma mensagem para uma fila as opções default()
     * 
     * @param message
     *            String com o conteúdo da mensagem
     * @param queueName
     *            String com o nome da fila
     * @param options
     *            int com as opções da fila
     * @throws MQException
     *             caso não consiga acessar a fila
     */
    public void send(String message, String queueName) {
        int options = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
        send(message, queueName, options);
    }

    /**
     * Metodo para realizar o commit na transações do queueManager
     */
    public void commit() {
        try {
            manager.commit();
        } catch (MQException e) {
            log.error("Erro durante a tentativa de commitar as alteraçoes."
                    + "CompletionCode: " + e.completionCode + ", Reason: "
                    + e.reasonCode, e);
        }
    }

    /**
     * Metodo para efetuar o rollback nas transações
     */
    public void rollback() {
        try {
            manager.backout();
        } catch (MQException e) {
            log.error("Erro durante a tentativa de realizar o rollback as alteraçoes."
                      + "CompletionCode: " + e.completionCode
                      + ", Reason: " + e.reasonCode, e);
        }
    }

    /**
     * Metodo para obter uma mensagem de uma fila
     * 
     * @param queueName
     *            String com o nome da fila
     * @return String com a mensagem obtida da fila ou null caso a fila esteja
     *         fazia
     */
    public String getMessage(String queueName) {
        MQQueue queue = (MQQueue) filas.get(queueName);
        int options = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING
                | MQC.MQOO_INQUIRE;
        if (queue == null) {
            try {
                queue = manager.accessQueue(queueName, options);
                filas.put(queueName, queue);
            } catch (MQException e) {
                log.error("Erro durante a tentativa de acesso a fila. CompletionCode:"
                          + e.completionCode + ", Reason:"
                          + e.reasonCode, e);
            }
        } else {
            queue.openOptions = options;
        }
        //Cria um objeto encapsulador dos dados da mensagem
        MQMessage mqMesg = new MQMessage();

        // Aceita as opcoes definidas
        MQGetMessageOptions mqGmo = new MQGetMessageOptions();        
        mqGmo.options = mqGmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
        try {
            // Captura a saida da fila
            queue.get(mqMesg, mqGmo);
            return mqMesg.readString(mqMesg.getMessageLength());
        } catch (MQException e) {
            log.error("Não foi possivel acessar a fila para obter a mensagem."
                    + " CompletionCode " + e.completionCode + " Reason:"
                    + e.reasonCode, e);
        } catch (EOFException e) {
            log.error("Fila está vazia", e);
        } catch (IOException e) {
            log.error("Não foi possivel ler a mensagem da fila", e);
        }
        return null;
    }

    /**
     * Metodo para registrar um listener para uma fila
     * 
     * @param queueName
     *            String com o nome da fila
     * @param obj
     *            Listener que quando tiver uma mensagem na fila será chamado
     */
    public void registerQueueListener(String queueName, MQListener obj) {
        Collection objetos = (Collection) listener.get(queueName);
        if (objetos == null || objetos.isEmpty()) {
            objetos = new ArrayList();
        }
        objetos.add(obj);
        listener.put(queueName, objetos);
    }

    public void startQueueMonitoring(String queueName) {
        MQQueue queue = (MQQueue) filas.get(queueName);
        int options = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INQUIRE;
        if (queue == null) {
            try {
                queue = manager.accessQueue(queueName, options);
                filas.put(queueName, queue);
            } catch (MQException e) {
                log.error("Erro durante a tentativa de acesso a fila. CompletionCode:"
                          + e.completionCode + ", Reason:"
                          + e.reasonCode, e);
            }
        } else {
            queue.openOptions = options;
        }

        // Aceita as opcoes definidas
        MQGetMessageOptions mqGmo = new MQGetMessageOptions();        
        mqGmo.options = mqGmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
        mqGmo.options = mqGmo.options + MQC.MQGMO_WAIT;
        mqGmo.waitInterval = 80000;

        QueueThread thread = new QueueThread(queue, mqGmo,(Collection) listener.get(queueName));
        threads.put(queueName, thread);

        //Disparando a thread
        thread.start();
    }
    
    /**
     * Método para parar um monitoramente de uma fila
     * @param queueName Nome da fila que está sendo monitorada
     */
    public void stopQueueMonitoring(String queueName){
        QueueThread thread = (QueueThread)threads.get(queueName);
        if(thread != null){
            thread.interrupt();
        }
    }
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#delistResource(javax.transaction.xa.XAResource, int)
     */
    public boolean delistResource(XAResource arg0, int arg1) throws IllegalStateException, SystemException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#enlistResource(javax.transaction.xa.XAResource)
     */
    public boolean enlistResource(XAResource arg0) throws RollbackException, IllegalStateException, SystemException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#getStatus()
     */
    public int getStatus() throws SystemException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#registerSynchronization(javax.transaction.Synchronization)
     */
    public void registerSynchronization(Synchronization arg0) throws RollbackException, IllegalStateException, SystemException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.transaction.Transaction#setRollbackOnly()
     */
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        // TODO Auto-generated method stub
        
    }
    /**
     * 
     * @author Andre Fonseca
     *
     * Classe que encapsula a inteligencia da gerencia da thread de monitaração da fila
     */
    class QueueThread extends Thread {
        private MQQueue queue;

        private MQGetMessageOptions op;

        private Collection listener;

        private boolean flag = true;

        /**
         * @param queue
         * @param op
         */
        public QueueThread(MQQueue queue, MQGetMessageOptions op,
                Collection listener) {
            super();
            this.queue = queue;
            this.op = op;
            this.listener = listener;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Thread#run()
         */
        public void run() {
            while (flag) {
                try {
                    //Cria um objeto encapsulador dos dados da mensagem
                    MQMessage mqMesg = new MQMessage();
                    queue.get(mqMesg, op);
                    //Disparando os listeners
                    Iterator iterator = listener.iterator();
                    while (iterator.hasNext()) {
                        MQListener listener = (MQListener)iterator.next();
                        listener.onMessage(mqMesg.readString(mqMesg.getMessageLength()));
                    }
                } catch (MQException e) {
                    log.error("Ocorreu um erro na tentativa de acesso a fila e obtenção da mensagem."
                              + " CompletationCode" + e.completionCode
                              + ", Reason:" + e.reasonCode, e);
                } catch (EOFException e) {
                    log.error("Não existem mensagens na fila.", e);
                } catch (IOException e) {
                    log.error("Erro na tentativa de converter a mensagem da fila em string", e);
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Thread#interrupt()
         */
        public void interrupt() {
            flag = false;           
        }
    }
    
}
