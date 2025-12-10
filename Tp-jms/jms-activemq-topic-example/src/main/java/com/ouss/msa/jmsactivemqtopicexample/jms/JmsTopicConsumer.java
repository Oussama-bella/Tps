package com.ouss.msa.jmsactivemqtopicexample.jms;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;

public class JmsTopicConsumer {
private final String destinationName;
private final String brokerUrl;

public JmsTopicConsumer(String destinationName, String brokerUrl) {
	this.destinationName = destinationName;
	this.brokerUrl = brokerUrl;
}

public void receive() throws JMSException, IOException {
	// Permet la désérialisation d'objets pour le TP (attention en PROD : restreindre)
	System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
	
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
	Connection connection = connectionFactory.createConnection();
	
	// clientID requis pour durable subscriber
	connection.setClientID("142");
	connection.start();
	
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Topic destination = session.createTopic(destinationName);
	
	TopicSubscriber subscriber1 = session.createDurableSubscriber(destination, "subscriber1");
	TopicSubscriber subscriber2 = session.createDurableSubscriber(destination, "subscriber2");
	
	subscriber1.setMessageListener(msg -> {
		if (msg instanceof ObjectMessage objectMessage) {
			try {
				Article article = (Article) objectMessage.getObject();
				System.out.println("Message reçu par subscriber 1 : " + article);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	});
	
	subscriber2.setMessageListener(msg -> {
		if (msg instanceof ObjectMessage objectMessage) {
			try {
				Article article = (Article) objectMessage.getObject();
				System.out.println("Message reçu par subscriber 2 : " + article);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	});
	
	// Maintient le programme en écoute jusqu'à une entrée clavier
	System.out.println("Consumer en écoute. Appuie sur Entrée pour quitter...");
	System.in.read();
	
	// fermeture propre
	subscriber1.close();
	subscriber2.close();
	session.close();
	connection.close();
}
}
