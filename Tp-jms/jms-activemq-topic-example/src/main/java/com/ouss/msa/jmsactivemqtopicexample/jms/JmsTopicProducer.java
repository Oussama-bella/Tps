package com.ouss.msa.jmsactivemqtopicexample.jms;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsTopicProducer {
private final String destinationName;
private final String brokerUrl;

public JmsTopicProducer(String destinationName, String brokerUrl) {
	this.destinationName = destinationName;
	this.brokerUrl = brokerUrl;
}

public void send(Article article) throws JMSException {
	// si on envoie des ObjectMessage, autoriser la désérialisation pour les tests (voir remarque sécurité)
	System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
	
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
	Connection connection = connectionFactory.createConnection();
	connection.start();
	
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Destination destination = session.createTopic(destinationName);
	MessageProducer producer = session.createProducer(destination);
	
	ObjectMessage msg = session.createObjectMessage(article);
	producer.send(msg);
	
	System.out.println("Message envoyé : " + article);
	
	producer.close();
	session.close();
	connection.close();
}
}

