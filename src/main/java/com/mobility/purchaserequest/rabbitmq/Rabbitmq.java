package com.mobility.purchaserequest.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Getter
public class Rabbitmq {
	public Channel channel;

	public Rabbitmq() {
		try {
			if (channel == null) {
				System.out.println("Trying to connect with RabbitMQ...");
				channel = getFactory().newConnection().createChannel();
				System.out.println("Connected with RabbitMQ");
			}
		} catch (IOException e) {
			System.out.println("Failed to connect with RabbitMQ");
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("RabbitMQ timedout");
			e.printStackTrace();
		}
	}

	private ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("root");
		return factory;
	}
}
