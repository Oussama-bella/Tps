package com.ouss.msa.jmsactivemqqueueexample.jms;


import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsQueueProducer {

private final String queueName;
private final String brokerUrl;

public JmsQueueProducer(String queueName, String brokerUrl) {
	this.queueName = queueName;
	this.brokerUrl = brokerUrl;
}

public void send(Article article) throws JMSException {
	// Permettre la désérialisation d'objets pour l'exemple (en test seulement)
	System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
	
	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
	Connection connection = factory.createConnection();
	connection.start();
	
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Queue queue = session.createQueue(queueName);
	MessageProducer producer = session.createProducer(queue);
	
	ObjectMessage objectMessage = session.createObjectMessage(article);
	producer.send(objectMessage);
	
	System.out.println("Message envoyé : " + article);
	
	producer.close();
	session.close();
	connection.close();
}
}