package com.ouss.msa.jmsactivemqtopicexample.jms;


import jakarta.jms.JMSException;
import java.io.IOException;

public class ConsumerMain {
public static void main(String[] args) {
	JmsTopicConsumer consumer = new JmsTopicConsumer(IConstants.TOPIC_NAME, IConstants.BROKER_URL);
	try {
		consumer.receive();
	} catch (JMSException | IOException e) {
		e.printStackTrace();
	}
}
}
