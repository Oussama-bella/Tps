package ma.formations.kafka.tp12.controllers;


import ma.formations.kafka.tp12.dtos.Notification;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.*;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
public class NotificationController {

private final StreamBridge streamBridge;
private final InteractiveQueryService queryService;

public NotificationController(StreamBridge streamBridge,
							  InteractiveQueryService queryService) {
	this.streamBridge = streamBridge;
	this.queryService = queryService;
}

@GetMapping("/publish/{topic}/{registrationNumber}")
public Notification publish(@PathVariable String topic,
							@PathVariable String registrationNumber) {
	
	Notification notification = Notification.builder()
										.code(UUID.randomUUID().toString())
										.date(new Date())
										.authorizedSpeed(60d)
										.currentSpeed(90d)
										.registrationNumber(registrationNumber)
										.build();
	
	streamBridge.send(topic, notification);
	return notification;
}

@GetMapping(path = "/analytics", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<Map<String, Long>> analytics() {
	return Flux.interval(Duration.ofSeconds(1))
				   .map(seq -> {
					   Map<String, Long> map = new HashMap<>();
					   ReadOnlyWindowStore<String, Long> store =
							   queryService.getQueryableStore("counts",
									   QueryableStoreTypes.windowStore());
					   
					   Instant now = Instant.now();
					   Instant from = now.minusSeconds(5);
					   
					   KeyValueIterator<Windowed<String>, Long> iterator =
							   store.fetchAll(from, now);
					   
					   while (iterator.hasNext()) {
						   KeyValue<Windowed<String>, Long> next = iterator.next();
						   map.put(next.key.key(), next.value);
					   }
					   return map;
				   });
}
}
