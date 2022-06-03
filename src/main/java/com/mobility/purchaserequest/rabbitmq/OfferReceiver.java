package com.mobility.purchaserequest.rabbitmq;

import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.repositories.OfferRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class OfferReceiver {

	private static OfferRepository offerRepository;
	private static VehicleRepository vehicleRepository;

	private static final String EXCHANGE_NAME = "offer_exchange";

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

	public OfferReceiver(VehicleRepository vehicleRepository, OfferRepository offerRepository) {
		OfferReceiver.vehicleRepository = vehicleRepository;
		OfferReceiver.offerRepository = offerRepository;
	}

	private static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("root");
		return factory;
	}

	public static void init() throws IOException {
		receive("offer.add", createOffer);
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

	private static DeliverCallback createOffer = (consumerTag, delivery) -> {
		try {
			String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
			try {
				System.out.println(data);
				JSONObject jsonObject = new JSONObject(data);
				Vehicle vehicle = vehicleRepository.findByUuid(jsonObject.getJSONObject("vehicle").getString("uuid"));
				Offer offer = new Offer(jsonObject, vehicle);
				offerRepository.save(offer);
			} catch (JSONException err) {
				System.out.println("Error" + err.toString());
			}
			System.out.println(" [x] Offer received from RabbitMQ");
		} finally {
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(" [x] Done");
		}
	};
}
