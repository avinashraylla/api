package com.moneytransfer.rest.exception;

import com.moneytransfer.common.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MoneyTransferExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {

       return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
    }
}
