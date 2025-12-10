package com.ouss.msa.jmsactivemqqueueexample;

/**
 * Classe "launcher" neutre : ne dépend pas de Spring.
 * Remplace la classe annotée @SpringBootApplication si tu n'utilises pas Spring.
 */
public class JmsActivemqQueueExampleApplication {
public static void main(String[] args) {
	System.out.println("Projet JMS (Queue). Exécute les mains ConsumerMain ou ProducerMain.");
	System.out.println("Exemples :");
	System.out.println("mvn exec:java -Dexec.mainClass=\"com.ouss.msa.jms.ConsumerMain\"");
	System.out.println("mvn exec:java -Dexec.mainClass=\"com.ouss.msa.jms.ProducerMain\"");
}
}
