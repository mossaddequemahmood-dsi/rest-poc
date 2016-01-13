package com.dsi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dsi.rest.interfaces.Rest;

@Rest("helloworld")
public class HelloWorldResource {
	public static final String CLICHED_MESSAGE = "Hellooooooo World!";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello() {
		return CLICHED_MESSAGE;
	}

}