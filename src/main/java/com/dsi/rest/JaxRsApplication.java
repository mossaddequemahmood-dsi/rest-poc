package com.dsi.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;

import com.dsi.rest.interfaces.Rest;

public class JaxRsApplication extends ResourceConfig {

	public JaxRsApplication() {

		packages("com.dsi.rest");

		Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path("jelloworld");

		ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod("GET");
		methodBuilder.produces(MediaType.TEXT_PLAIN_TYPE).handledBy(
				new Inflector<ContainerRequestContext, String>() {

					public String apply(
							ContainerRequestContext containerRequestContext) {
						return "Jello World!";
					}
				});

		Resource resource = resourceBuilder.build();
		registerResources(resource);

		try {

			final Class cls = Class.forName("com.dsi.rest.HelloWorldResource");

			boolean present = cls.isAnnotationPresent(Rest.class);
			System.out.println(present);

			Rest rest = (Rest) cls.getAnnotation(Rest.class);
			System.out.println("value: " + rest.value());

			resourceBuilder = Resource.builder();
			resourceBuilder.path(rest.value());

			methodBuilder = resourceBuilder.addMethod("GET");
			methodBuilder.produces(MediaType.TEXT_PLAIN_TYPE).handledBy(
					new Inflector<ContainerRequestContext, String>() {

						public String apply(
								ContainerRequestContext containerRequestContext) {

							Method[] method = cls.getDeclaredMethods();
							try {

								return (String) method[0].invoke(cls
										.newInstance());
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							return null;
						}
					});

			resource = resourceBuilder.build();
			registerResources(resource);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}