package com.ouss.msa.jmsactivemqqueueexample.jms;


import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsQueueConsumer {

private final String queueName;
private final String brokerUrl;

public JmsQueueConsumer(String queueName, String brokerUrl) {
	this.queueName = queueName;
	this.brokerUrl = brokerUrl;
}

public void receive() throws JMSException {
	// Permettre la désérialisation d'objets pour l'exemple (en test seulement)
	System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
	
	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
	Connection connection = factory.createConnection();
	connection.start();
	
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Queue queue = session.createQueue(queueName);
	
	MessageConsumer consumer = session.createConsumer(queue);
	
	System.out.println("En attente de messages dans la QUEUE : " + queueName);
	
	consumer.setMessageListener(msg -> {
		if (msg instanceof ObjectMessage om) {
			try {
				Article article = (Article) om.getObject();
				System.out.println("Message reçu : " + article);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Message reçu (autre type) : " + msg);
		}
	});
	
	// Maintient le programme en écoute jusqu'à Entrée (ou kill)
	try {
		System.out.println("Appuie sur Entrée pour arrêter le consumer...");
		System.in.read();
	} catch (Exception ignored) {}
	
	consumer.close();
	session.close();
	connection.close();
}
}
