package br.com.metronus.util.integration;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.CMQC;
import com.ibm.mq.pcf.CMQCFC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;

/**
 * @author Andre Fonseca
 * Classe que realizar o acesso a um queue manager, permitindo colocar e buscar mensagens em suas filas
 */
public class MessageHandlerMQImpl {

	private String queueManager;

	private MQQueueManager mqQueueManager;

	private final static int LOCAL_QUEUE = 1;

	public MessageHandlerMQImpl(String queueManager) {
		this.queueManager = queueManager;
	}

	/**
	 * Metodo para conectar com o QueueManager, sendo ele local
	 *
	 * @throws MQException
	 */
	public void connect() throws MQException {
		mqQueueManager = new MQQueueManager(queueManager);
	}

	/**
	 * Metodo para conectar num queue manager remoto, ou seja, numa maquina
	 * diferente da aplicação
	 *
	 * @param host
	 *            String com o hostname ou ip da maquina
	 * @param chanel
	 *            canal usado para comunicar com o maquina
	 * @throws MQException
	 */
	public void remoteConnect(String host, String channel, int porta)
			throws MQException {
		// Set up MQSeries environment
		MQEnvironment.hostname = host;
		MQEnvironment.channel = channel;
		MQEnvironment.port = porta;

		// Set TCP/IP or server and Connection
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,
				MQC.TRANSPORT_MQSERIES);
		mqQueueManager = new MQQueueManager(queueManager);
	}

	/**
	 * Método para buscar todas as filas de um queueManager
	 *
	 * @return List com objetos MQQueueWraper com os dados das filas
	 * @throws MQException
	 * @throws IOException
	 */
	public List getAllQueuesName() throws MQException, IOException {
		final PCFMessageAgent pcfAgent = new PCFMessageAgent(mqQueueManager);
		final PCFMessage pcfRequest;
		final PCFMessage pcfResponse[];
		List retorno = new ArrayList();
		pcfRequest = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);
		pcfRequest.addParameter(CMQC.MQCA_Q_NAME, "*");
		pcfRequest.addParameter(CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL);
		pcfResponse = pcfAgent.send(pcfRequest);
		MQQueueWrapper wrapper;
		int qType = 0;
		for (int i = 0; i < pcfResponse.length; i++) {
			wrapper = new MQQueueWrapper();
			// Pegando o tipo da fila
			qType = ((Integer) (pcfResponse[i]
					.getParameterValue(CMQC.MQIA_Q_TYPE))).intValue();
			wrapper.setNome(pcfResponse[i]
					.getStringParameterValue(CMQC.MQCA_Q_NAME));
			wrapper.setQueueManager(queueManager);
			switch (qType) {
			case LOCAL_QUEUE: // local queues
				if (!(wrapper.getNome().substring(0, 6).equals("SYSTEM"))) {
					wrapper.setTamanhoAtual(pcfResponse[i]
							.getIntParameterValue(CMQC.MQIA_CURRENT_Q_DEPTH));
				}
				break;
			default:
				wrapper.setTamanhoAtual(0);
			}
			retorno.add(wrapper);
		}
		pcfAgent.disconnect();
		return retorno;
	}

	public void clearQueue(String queueName) throws MQException, IOException {
		final PCFMessageAgent pcfAgent = new PCFMessageAgent(mqQueueManager);
		final PCFMessage pcfRequest;
		final PCFMessage pcfResponse[];
		pcfRequest = new PCFMessage(CMQCFC.MQCMD_CLEAR_Q);
		pcfRequest.addParameter(CMQC.MQCA_Q_NAME, queueName);
		pcfResponse = pcfAgent.send(pcfRequest);
		for (int i = 0; i < pcfResponse.length; i++) {
			System.out.println(">>>>>>>>" + pcfResponse[i]);
		}
		pcfAgent.disconnect();
	}

	public void getMessagePCF(String queueName) throws MQException, IOException {
		final PCFMessageAgent pcfAgent = new PCFMessageAgent(mqQueueManager);
		final PCFMessage pcfRequest;
		final PCFMessage pcfResponse[];
		pcfRequest = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);
		pcfRequest.addParameter(CMQC.MQCA_Q_NAME, queueName);
		pcfResponse = pcfAgent.send(pcfRequest);
		for (int i = 0; i < pcfResponse.length; i++) {
			System.out.println(">>>>>>>>" + pcfResponse[i].getStringParameterValue(CMQC.MQCA_FIRST));
		}
		pcfAgent.disconnect();
	}

	/**
	 * Metodo para colocar uma mensagem na fila
	 *
	 * @param msg
	 *            String com a mensagem
	 * @param fila
	 *            String com o nome da fila
	 * @param options
	 *            int com as opções da fila (veja a documentação do Mqseries
	 *            para saber quais opções e seus valores)
	 * @throws MQException
	 *             caso ocorra erro na abertura da fila ou colocação da mensagem
	 * @throws IOException
	 *             na conversão da string da mensagem para o objeto mensagem do
	 *             MQ
	 */
	public void putMessage(String msg, String fila, int options)
			throws MQException, IOException {
		MQQueue queue = mqQueueManager.accessQueue(fila, options);

		// Criando o objeto message
		MQMessage mqMessage = new MQMessage();
		mqMessage.writeBytes(msg);

		// Definindo o formato da mensagem
		mqMessage.format = MQC.MQFMT_STRING;
		queue.put(mqMessage);
		// fecha a fila depois de usar
		queue.close();
	}

	public void putMessage(String msg, MQQueue queue) throws IOException,
			MQException {
		// Criando o objeto message
		MQMessage mqMessage = new MQMessage();
		mqMessage.writeBytes(msg);

		// Definindo o formato da mensagem
		mqMessage.format = MQC.MQFMT_STRING;
		queue.put(mqMessage);
		// fecha a fila depois de usar
		queue.close();
	}

	/**
	 * Metodo para colocar uma coleção de mensagens na fila
	 *
	 * @param msgs
	 *            Collection com as String de mensagens
	 * @param fila
	 *            String com o nome da fila
	 * @param options
	 *            int com as opções da fila (veja a documentação do Mqseries
	 *            para saber quais opções e seus valores)
	 * @throws MQException
	 *             caso ocorra erro na abertura da fila ou colocação da mensagem
	 * @throws IOException
	 *             na conversão da string da mensagem para o objeto mensagem do
	 *             MQ
	 */
	public void putMessages(Collection msgs, String fila, int options)
			throws MQException, IOException {
		Iterator iterator = msgs.iterator();
		MQQueue queue = mqQueueManager.accessQueue(fila, options);
		while (iterator.hasNext()) {
			putMessage((String) iterator.next(), queue);
		}
	}

	/**
	 * Metodo para obter todas as mensagens que estão numa fila do queue manager
	 *
	 * @param fila
	 *            String com o nome da fila
	 * @param options
	 *            int com as opções da fila (veja a documentação do Mqseries
	 *            para saber quais opções e seus valores)
	 * @return Collection de string com o conteudo das mensagens
	 * @throws MQException
	 * @throws IOException
	 */
	public Collection getAllMessages(String fila, int options)
			throws MQException, IOException {
		ArrayList msgs = new ArrayList();
		MQQueue queue = mqQueueManager.accessQueue(fila, options);
		try {
			while (true) {
				msgs.add(getMessage(queue));
			}
		} catch (EOFException e) {
			// Encerrando a fila e a fechando
			System.out.println("ocorreu um erro de eofexception. Fila vazia");
		} catch (MQException e) {
			if (e.reasonCode != 2033 && e.completionCode != 2) {
				throw new MQException(e.completionCode, e.reasonCode, e);
			}
		} finally {
			queue.close();
			return msgs;
		}
	}

	/**
	 * Metodo para retorna o conteúdo de uma mensagem numa fila
	 *
	 * @param fila
	 *            String com o nome da fila
	 * @param options
	 *            int com as opçoes da fila
	 * @return String com o conteúdo da mensagem lida da fila
	 * @throws MQException
	 *             Caso ocorra algum erro na conexão com a fila
	 * @throws EOFException
	 *             Casos não tenha nenhuma mensagem na fila
	 * @throws IOException
	 *             Caso o sistema não consiga ler a String do objeto mensagem
	 */
	public String getMessage(String fila, int options) throws MQException,
			EOFException, IOException {
		MQQueue queue = mqQueueManager.accessQueue(fila, options);

		// Cria um objeto encapsulador dos dados da mensagem
		MQMessage mqMesg = new MQMessage();

		// Aceita as opcoes definidas
		MQGetMessageOptions mqGmo = new MQGetMessageOptions();

		// Captura a saida da fila
		queue.get(mqMesg, mqGmo);
		String conteudo = mqMesg.readString(mqMesg.getMessageLength());

		// Encerrando a fila e a fechando
		queue.close();
		return conteudo;
	}

	/**
	 * Metodo para ler uma mensagem de um fila já definida
	 *
	 * @param queue
	 *            MQQueue da fila desejada
	 * @return String com o conteudo da mensagem
	 * @throws MQException
	 *             Caso ocorra algum erro na conexão com a fila
	 * @throws EOFException
	 *             Casos não tenha nenhuma mensagem na fila
	 * @throws IOException
	 *             Caso o sistema não consiga ler a String do objeto mensagem
	 */
	public String getMessage(MQQueue queue) throws MQException, EOFException,
			IOException {
		// Cria um objeto encapsulador dos dados da mensagem
		MQMessage mqMesg = new MQMessage();

		// Aceita as opcoes definidas
		MQGetMessageOptions mqGmo = new MQGetMessageOptions();

		// Captura a saida da fila
		queue.get(mqMesg, mqGmo);
		String conteudo = mqMesg.readString(mqMesg.getMessageLength());
		return conteudo;
	}

	/**
	 * Metodo para obter o tamanha atual de uma fila (quantas mensagens ainda
	 * estão na fila)
	 *
	 * @param fila
	 *            String com o nome da fila
	 * @param option
	 *            int com as opções da fila
	 * @return int com valor da quantia de mensagens na fila
	 * @throws MQException
	 *             caso ocorra algum erro na conexão a fila e na tentativa de
	 *             obtenção da profundidade
	 */
	public int getCurrentDepth(String fila, int option) throws MQException {
		MQQueue queue = mqQueueManager.accessQueue(fila, option);
		int depth = 0;
		depth = queue.getCurrentDepth();
		queue.close();
		return depth;
	}

	/**
	 * Metodo para encerrar a conexão com o queue Manager
	 *
	 * @throws MQException
	 *             caso não fechar o queuemanager e/ou desconectar
	 */
	public void close() throws MQException {
		mqQueueManager.disconnect();
		mqQueueManager.close();
	}
}
