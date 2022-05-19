package com.mobility.purchaserequest.util;

import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobility.purchaserequest.models.Jwt;

public class JwtParser {

	public Jwt ParseToken(String token) throws JsonMappingException, JsonProcessingException {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));

		ObjectMapper mapper = new ObjectMapper();
		Jwt map = mapper.readValue(payload, Jwt.class);

		return map;

	}
}
