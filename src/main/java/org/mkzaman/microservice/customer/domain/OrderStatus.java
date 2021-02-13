package org.mkzaman.microservice.customer.domain;

public enum OrderStatus {
    CREATED("CREATED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    DELETED("DELETED"),
    RETURNING("RETURNING"),
    RETURNED("RETURNED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
