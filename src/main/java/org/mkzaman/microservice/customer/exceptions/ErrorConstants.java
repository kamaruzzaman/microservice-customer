package org.mkzaman.microservice.customer.exceptions;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final URI DEFAULT_TYPE = URI.create("");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create("constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create("entity-not-found");

    private ErrorConstants() {
    }
}
