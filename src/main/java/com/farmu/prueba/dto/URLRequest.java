package com.farmu.prueba.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class URLRequest {

	@Getter
	@Setter
	private String longUrl;
	@Getter
	@Setter
	private Date expiresDate;
}
