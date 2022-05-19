package com.mobility.purchaserequest.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class TokenSender {
	private static final Dotenv env = Dotenv.load();

	private static String REQUEST_QUEUE_NAME = "jwt_queue";

	private static Channel channel;

	static {
		try {
			if (channel == null) {
				channel = getFactory().newConnection().createChannel();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
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

	public static String auth(String jwt) throws IOException, InterruptedException {
		final String corId = UUID.randomUUID().toString();

		String replyQueueName = channel.queueDeclare().getQueue();
		BasicProperties props = new BasicProperties.Builder().correlationId(corId).replyTo(replyQueueName).build();

		channel.basicPublish("", REQUEST_QUEUE_NAME, props, jwt.getBytes(StandardCharsets.UTF_8));

		final BlockingQueue<String> responseResult = new ArrayBlockingQueue<>(1);

		String response = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corId)) {
				responseResult.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
			}
		}, consumerTag -> {
		});

		String result = responseResult.take();
		channel.basicCancel(response);
		return result;
	}
}
