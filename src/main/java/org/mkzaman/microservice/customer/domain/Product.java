package org.mkzaman.microservice.customer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Product.
 */
@Data
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    private String description;

    private String modelNumber;

    @NotNull
    private String manufacturerName;

    @NotNull
    @Min(value = 0, message = "Price cannot be less than zero")
    private Double price;

    private String detailInfo;

    private String imageUrl;

    @NotNull
    @Min(value = 0, message = "Quantity cannot be less than zero")
    private Integer quantity;
}
