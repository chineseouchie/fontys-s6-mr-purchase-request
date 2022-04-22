package com.mobility.purchaserequest.rabbitmq;

import com.google.gson.Gson;
import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class PurchaseRequestService {
	private static final Dotenv env = Dotenv.load();

	private static final String EXCHANGE_NAME = "purchase_request_exchange";

	private static Channel channel;

	static {
		try {
			if (channel == null) {
				channel = getFactory().newConnection().createChannel();
				System.out.println("RabbitMQ connected");
			}
		} catch (IOException e) {
			System.out.println("RabbitMQ failed to connect");
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("RabbitMQ timed out");
			e.printStackTrace();
		}
	}

	private static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(env.get("RABBITMQ_HOST"));
		factory.setUsername(env.get("RABBITMQ_USER"));
		factory.setPassword(env.get("RABBITMQ_PASSWORD"));
		return factory;
	}

	public static void publishAcceptedPurchaseRequest(PurchaseRequestCompany pr) throws IOException {
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String json = new Gson().toJson(pr);
		channel.basicPublish(EXCHANGE_NAME, "purchaserequest.accepted", null, json.getBytes(StandardCharsets.UTF_8));
		System.out.println("PR sent to other MS");
	}

}
