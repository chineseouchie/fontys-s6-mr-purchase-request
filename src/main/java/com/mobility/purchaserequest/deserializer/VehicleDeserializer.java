package com.mobility.purchaserequest.deserializer;

import com.google.gson.*;
import com.mobility.purchaserequest.models.Vehicle;

import java.lang.reflect.Type;

public class VehicleDeserializer implements JsonDeserializer<Vehicle> {
    @Override
    public Vehicle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject vehicle = jsonElement.getAsJsonObject();
        String uuid = vehicle.get("uuid").getAsString();
        String brand_name = vehicle.get("brand").getAsJsonObject().get("name").getAsString();
        String model_name = vehicle.get("model").getAsString();
        String image_url = vehicle.get("image_url").getAsString();

        return new Vehicle(uuid, brand_name, model_name, image_url);
    }
}
