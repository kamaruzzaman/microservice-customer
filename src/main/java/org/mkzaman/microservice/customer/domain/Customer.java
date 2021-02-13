package org.mkzaman.microservice.customer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Document(collection = "customer")
@Data
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("first_name")
    private String firstName;

    @Field("middle_name")
    private String middleName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @Field("payment_details")
    private String paymentDetails;

    @Field("created_at")
    @CreatedDate
    private Instant createdAt;

    @Field("updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Version
    public Integer version;

    @Field("orders")
    private Set<Order> orders = new HashSet<>();

    @Field("billing_address")
    private Address billingAddress;

    public Customer addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public Customer removeOrder(Order order) {
        this.orders.remove(order);
        return this;
    }
}
