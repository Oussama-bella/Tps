package com.ouss.msa.jmsactivemqtopicexample.jms;


import jakarta.jms.JMSException;

public class ProducerMain {
public static void main(String[] args) {
	JmsTopicProducer producer = new JmsTopicProducer(IConstants.TOPIC_NAME, IConstants.BROKER_URL);
	try {
		producer.send(new Article(1L, "ARTICLE_1", 1522.0));
		producer.send(new Article(2L, "ARTICLE_2", 2100.0));
		producer.send(new Article(3L, "ARTICLE_3", 15000.0));
		producer.send(new Article(4L, "ARTICLE_4", 900.0));
	} catch (JMSException e) {
		e.printStackTrace();
	}
}
}
