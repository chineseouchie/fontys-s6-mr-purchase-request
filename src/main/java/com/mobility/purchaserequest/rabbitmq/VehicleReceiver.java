package com.mobility.purchaserequest.rabbitmq;

import com.google.gson.GsonBuilder;
import com.mobility.purchaserequest.deserializer.VehicleDeserializer;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.models.repositories.VehicleRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class VehicleReceiver {
	private static final Dotenv env = Dotenv.load();

	private static VehicleRepository vehicleRepository;

	private static final String EXCHANGE_NAME = "vehicle_exchange";

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

	public VehicleReceiver(VehicleRepository vehicleRepository) {
		VehicleReceiver.vehicleRepository = vehicleRepository;
	}

	private static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(env.get("RABBITMQ_HOST"));
		factory.setUsername(env.get("RABBITMQ_USER"));
		factory.setPassword(env.get("RABBITMQ_PASSWORD"));
		return factory;
	}

	public static void init() throws IOException {
		receive("vehicle.add", createVehicle);
	}

	private static void receive(String key, DeliverCallback deliverCallback) throws IOException {
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();

		channel.queueBind(queueName, EXCHANGE_NAME, key);
		// doesn't automatically acknowledge the message when it is received
		boolean autoAck = false;
		channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
		});
	}

	private static DeliverCallback createVehicle = (consumerTag, delivery) -> {
		try {
			String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
			GsonBuilder gsonBldr = new GsonBuilder();
			gsonBldr.registerTypeAdapter(Vehicle.class, new VehicleDeserializer());
			Vehicle vehicle = gsonBldr.create().fromJson(data, Vehicle.class);
			if (vehicleRepository.findByUuid(vehicle.getUuid()) == null) {
				vehicleRepository.save(vehicle);
				System.out.println(" [x] Vehicle saved from RabbitMQ: " + data);
			} else {
				System.out.println(" [x] Vehicle skipped from RabbitMQ (Duplicate): " + data);
			}
		} finally {
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(" [x] Done");
		}
	};
}
