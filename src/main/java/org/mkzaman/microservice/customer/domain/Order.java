package org.mkzaman.microservice.customer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * A Order.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotBlank
    private String id;

    @NotBlank
    private String customerId;

    private Instant createdAt;

    private Instant updatedAt;

    private OrderStatus status = OrderStatus.CREATED;

    private Boolean paymentStatus = Boolean.FALSE;

    public Integer version;

    @NotNull
    private PaymentType paymentMethod;

    @NotBlank
    private String paymentDetails;

    @NotNull
    @Valid
    private Address shippingAddress;

    @NotEmpty
    private Set<@Valid Product> products;


    public Order addProduct(Product product) {
        this.products.add(product);
        return this;
    }

    public Order removeProduct(Product product) {
        this.products.remove(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
