package ma.formations.kafka.tp12.service;

import ma.formations.kafka.tp12.dtos.Notification;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class NotificationService {

// Consumer
@Bean
public Consumer<Notification> notificationConsumer() {
	return notification -> {
		System.out.println("***************");
		System.out.println(notification);
		System.out.println("***************");
	};
}

// Supplier
@Bean
public Supplier<Notification> notificationSupplier() {
	List<String> matricules = List.of("A-2 5643", "A-6 9876", "A-45 6549");
	Random random = new Random();
	
	return () -> Notification.builder()
						 .code(UUID.randomUUID().toString())
						 .date(new Date())
						 .authorizedSpeed(60d)
						 .currentSpeed(80 + random.nextDouble() * 40)
						 .registrationNumber(matricules.get(random.nextInt(matricules.size())))
						 .build();
}

// Function
@Bean
public Function<Notification, Map<String, String>> notificationFunction() {
	return input -> {
		Map<String, String> map = new HashMap<>();
		map.put(
				input.getRegistrationNumber(),
				"CS=" + input.getCurrentSpeed() + ", AS=" + input.getAuthorizedSpeed()
		);
		return map;
	};
}

// Kafka Streams Function
@Bean
public Function<KStream<String, Notification>, KStream<String, Long>> kStreamFunction() {
	return input -> input
							.filter((k, v) -> v.getRegistrationNumber().equals("A-2 5643"))
							.map((k, v) -> new KeyValue<>(v.getRegistrationNumber(), 1L))
							.groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
							.windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
							.count(Materialized.as("counts"))
							.toStream()
							.map((k, v) -> new KeyValue<>(k.key(), v));
}
}
