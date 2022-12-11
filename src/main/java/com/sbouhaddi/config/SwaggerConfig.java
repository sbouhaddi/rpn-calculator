package com.sbouhaddi.config;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(tags = {
		@Tag(name = "RpnCalculator", description = "RpnCalculator operations."), }, info = @Info(title = "RpnCalculator API with Quarkus", version = "0.0.1", contact = @Contact(name = "Bouhaddi Sabri", url = "https://github.com/sbouhaddi", email = "sabri.bouhaddi@gmail.com")))
public class SwaggerConfig extends Application {

}
