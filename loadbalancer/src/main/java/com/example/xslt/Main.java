package com.example.xslt;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.ws.Endpoint;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException {
		final String endpoint = System.getenv().getOrDefault("ENDPOINT", "http://localhost:8080/transformer").toLowerCase();
		try {
			Endpoint.publish(endpoint, new TransformerService());
			log.info("Service is running at '{}'.", endpoint);
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				log.info("Shutting down service at '{}'.", endpoint);
			}));
			new CountDownLatch(1).await();
		} catch (InterruptedException e) {
			log.error("[{}] Error: {}", endpoint, e.getMessage());
		}
	}
}
