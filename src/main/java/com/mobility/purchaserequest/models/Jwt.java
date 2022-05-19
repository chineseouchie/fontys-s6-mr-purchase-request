package com.mobility.purchaserequest.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Jwt {
	private String exp;
	private String iat;
	private String sub;
	private List<String> roles;

}
