package com.mobility.purchaserequest.rabbitmq;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class UserReceiver {
	private static CompanyRepository companyRepository;
	private static final String EXCHANGE_NAME = "auth_exchange";
	private static Channel channel;

	static {
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

	public UserReceiver(CompanyRepository companyRepository) {
		UserReceiver.companyRepository = companyRepository;
	}

	private static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("root");
		return factory;
	}

	public static void init() throws IOException {
		receive("company.add", createUser);
	}

	private static void receive(String key, DeliverCallback deliverCallback) throws IOException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, args);

		String queueName = channel.queueDeclare("PR-user-receive", true, false, false, null).getQueue();

		channel.queueBind(queueName, EXCHANGE_NAME, key);
		// doesn't automatically acknowledge the message when it is received
		boolean autoAck = false;
		channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
		});
	}

	private static DeliverCallback createUser = (consumerTag, delivery) -> {
		String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
		JSONObject jsonObject = new JSONObject(data);
		String name = jsonObject.get("name").toString();
		String uuid = jsonObject.get("uuid").toString();

		Company company = new Company(uuid, name);

		if (companyRepository.findByUuid(company.getUuid()) == null) {
			companyRepository.save(company);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(" [x] Done");
		} else {
			System.out.println(" [x] Duplicate");
			channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
		}
	};
}
