package com.ouss.msa.jmsactivemqqueueexample.jms;


public class ProducerMain {
public static void main(String[] args) {
	try {
		JmsQueueProducer producer = new JmsQueueProducer(IConstants.QUEUE_NAME, IConstants.BROKER_URL);
		
		producer.send(new Article(1L, "ARTICLE_1", 100.0));
		producer.send(new Article(2L, "ARTICLE_2", 200.0));
		producer.send(new Article(3L, "ARTICLE_3", 300.0));
		producer.send(new Article(4L, "ARTICLE_4", 400.0));
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}