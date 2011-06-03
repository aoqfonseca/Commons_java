package br.com.metronus.util.integration;

import javax.transaction.Transaction;

/**
 * @author Andre Fonseca
 *
 * interface que determinar o comportamento de um objeto de acesso a filas
 * 
 */
public interface MQAccess extends Transaction{
    public void connect();

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
    public void send(String message, String queueName, int options);

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
    public void send(String message, String queueName);

    /**
     * Metodo para realizar o commit na transações do queueManager
     */
    public void commit();

    /**
     * Metodo para efetuar o rollback nas transações
     */
    public void rollback();

    /**
     * Metodo para obter uma mensagem de uma fila
     * 
     * @param queueName
     *            String com o nome da fila
     * @return String com a mensagem obtida da fila ou null caso a fila esteja
     *         fazia
     */
    public String getMessage(String queueName);

    /**
     * Metodo para registrar um listener para uma fila
     * 
     * @param queueName
     *            String com o nome da fila
     * @param obj
     *            Listener que quando tiver uma mensagem na fila será chamado
     */
    public void registerQueueListener(String queueName, MQListener obj);
    /**
     * Metodo para iniciar uma thread de monitoramento de uma fila
     * @param queueName
     */
    public void startQueueMonitoring(String queueName);

    /**
     * Método para parar um monitoramente de uma fila
     * @param queueName Nome da fila que está sendo monitorada
     */
    public void stopQueueMonitoring(String queueName);
}
