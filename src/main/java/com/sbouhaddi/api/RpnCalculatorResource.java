package com.sbouhaddi.api;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import com.sbouhaddi.model.RpnStack;
import com.sbouhaddi.validation.OperandValidation;

@Path("/rpn")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RpnCalculatorResource {

	@GET
	@Path("/stack")
	@Operation(summary = "List all stacks", description = "Find all stacks in database")
	@APIResponse(responseCode = "200", description = "Get All Stacks", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = RpnStack.class)))
	public Response listAllStacks() {
		return Response.ok(RpnStack.listAll()).build();
	}

	@GET
	@Path("/stack/{id}")
	@Operation(summary = "Find a stack", description = "Find a stack by id")
	@APIResponse(responseCode = "200", description = "Get Stack by id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = RpnStack.class)))
	@APIResponse(responseCode = "404", description = "Stack does not exist for id", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(@Parameter(name = "id", required = true) @PathParam("id") Long id) {
		return RpnStack.findByIdOptional(id).map(stack -> Response.ok(stack).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@POST
	@Transactional
	@Path("/stack")
	@Operation(summary = "Create a new stack", description = "Create a new stack empty or with values")
	@APIResponse(responseCode = "201", description = "Stack Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = RpnStack.class)))
	@APIResponse(responseCode = "400", description = "Invalid Stack", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Stack already exists", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response createStack(@NotNull @Valid RpnStack rpnStack) {
		RpnStack.persist(rpnStack);
		return Response.created(URI.create("/rpn/stack/" + rpnStack.getId())).entity(rpnStack).build();
	}

	@POST
	@Transactional
	@Path("/stack/{id}")
	@Operation(summary = "Push a new value a stack", description = "Pushes a new value to a stack found by id")
	@APIResponse(responseCode = "204", description = "Stack updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = RpnStack.class)))
	@APIResponse(responseCode = "400", description = "Invalid Stack", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Stack found for id provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response pushValuetoStack(@Parameter(name = "id", required = true) @PathParam("id") Long id,
			@NotNull Double value) {

		RpnStack rpnStack = RpnStack.findById(id);
		if (rpnStack != null) {
			rpnStack.getStack().push(value);
			RpnStack.persist(rpnStack);
			return Response.ok(rpnStack).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@POST
	@Transactional
	@Path("/op/{op}/stack/{id}")
	@Operation(summary = "Apply an operation to a stack", description = "Apply a specified operation to a stack found by id")
	@APIResponse(responseCode = "204", description = "Stack updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = RpnStack.class)))
	@APIResponse(responseCode = "400", description = "Invalid Stack", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Stack found for id provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response applyOperationtoStack(@Parameter(name = "op", required = true) @NotNull @OperandValidation @PathParam("op") String op,
			@Parameter(name = "id", required = true) @PathParam("id") Long id) {
		RpnStack rpnStack = RpnStack.findById(id);
		if (rpnStack != null) {
			RpnStack result = RpnStack.applyOperationToAstack(op, rpnStack);
			return Response.ok(result).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@DELETE
	@Transactional
	@Path("/stack/{id}")
	@Operation(summary = "Deletes a stack", description = "Deletes a stack by id")
	@APIResponses(value = { @APIResponse(responseCode = "204", description = "Success"),
			@APIResponse(responseCode = "404", description = "Stack not found", content = @Content(mediaType = "application/json")) })
	public Response delete(@PathParam("id") Long id) {
		if (RpnStack.findById(id) == null) {
			Response.status(Response.Status.NOT_FOUND).build();
		}
		RpnStack.deleteById(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}