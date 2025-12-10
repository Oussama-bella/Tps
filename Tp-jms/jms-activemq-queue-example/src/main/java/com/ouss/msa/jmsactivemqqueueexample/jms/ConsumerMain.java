package com.ouss.msa.jmsactivemqqueueexample.jms;


public class ConsumerMain {
public static void main(String[] args) {
	try {
		JmsQueueConsumer consumer = new JmsQueueConsumer(IConstants.QUEUE_NAME, IConstants.BROKER_URL);
		consumer.receive();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}