package com.mobility.purchaserequest.rabbitmq;

import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.repositories.OfferRepository;
//import com.mobility.purchaserequest.repositories.VehicleRepository;
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
public class OfferReceiveService {

    private static OfferRepository offerRepository;
    // private static VehicleRepository vehicleRepository;

    private static final Dotenv env = Dotenv.load();

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

    public OfferReceiveService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    private static ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.get("RABBITMQ_HOST"));
        factory.setUsername(env.get("RABBITMQ_USER"));
        factory.setPassword(env.get("RABBITMQ_PASSWORD"));
        return factory;
    }

    public static void startReceiving() throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "offer.add");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    Vehicle vehicle = new Vehicle(jsonObject);
                    Offer offer = new Offer(jsonObject, vehicle);
                    offerRepository.save(offer);
                } catch (JSONException err) {
                    System.out.println("Error" + err.toString());
                }

                // Offer offer = new Offer(data.id, "Hoi", "hoi", data.vehicle, 1);
                // vehicleRepository.save(data.vehicle);
                // offerRepository.save(offer);
                System.out.println(" [x] Offer received from RabbitMQ");
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println(" [x] Done");
            }
        };
        // doesn't automatically acknowledge the message when it is received
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
        });
    }

    // public static void startReceiving() throws IOException, TimeoutException {
    // ConnectionFactory factory = getFactory();
    //
    // Connection connection = factory.newConnection();
    // Channel channel = connection.createChannel();
    //
    // // makes sure that the messages are saved for when RabbitMQ stops
    // boolean durable = true;
    // channel.queueDeclare("Offer.add", durable, false, false, null);
    // System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    //
    // channel.basicQos(1);
    //
    // DeliverCallback deliverCallback = (consumerTag, delivery) -> {
    // try {
    // String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
    // Offer offer = new Gson().fromJson(data, Offer.class);
    // offerRepository.save(offer);
    // System.out.println(" [x] Offer received from RabbitMQ");
    // } finally {
    // channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    // System.out.println(" [x] Done");
    // }
    // };
    // // doesn't automatically acknowledge the message when it is received
    // boolean autoAck = false;
    // channel.basicConsume("Offer.add", autoAck, deliverCallback, consumerTag -> {
    // });
    // }
}
