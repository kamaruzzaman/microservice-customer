package org.mkzaman.microservice.customer.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.mkzaman.microservice.customer.domain.Customer;
import org.mkzaman.microservice.customer.domain.Order;
import org.mkzaman.microservice.customer.exceptions.BadRequestAlertException;
import org.mkzaman.microservice.customer.repository.CustomerRepository;
import org.mkzaman.microservice.customer.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CustomerOrderResource {

    private final Logger log = LoggerFactory.getLogger(CustomerOrderResource.class);

    private static final String ENTITY_NAME = "order";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CustomerRepository customerRepository;

    public CustomerOrderResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * {@code POST  /orders/:customerId} : Create a new order for the given "customerId" customer.
     *
     * @param customerId the id of the customer.
     * @param order      the order to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the new order, or with status {@code 400 (Bad Request)} if the order has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customerOrders/{customerId}")
    public ResponseEntity<Order> createOrder(@PathVariable String customerId, @Valid @RequestBody Order order) {
        log.debug("REST request to save Order : {} for Customer ID: {}", order, customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new BadRequestAlertException("No Customer", ENTITY_NAME, "noid");
        }
        final Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            final Customer customer = customerOptional.get();
            customer.addOrder(order);
            customerRepository.save(customer);
            return ResponseEntity.ok()
                    .body(order);
        } else {
            throw new BadRequestAlertException("Invalid Customer", ENTITY_NAME, "invalidcustomer");
        }
    }

    /**
     * {@code PUT  /orders/:customerId} : Updates an existing order for the given "customerId" customer.
     *
     * @param customerId the id of the customer.
     * @param order      the order to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated order,
     * or with status {@code 400 (Bad Request)} if the order is not valid,
     * or with status {@code 500 (Internal Server Error)} if the order couldn't be updated.
     */
    @PutMapping("/customerOrders/{customerId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String customerId, @Valid @RequestBody Order order) {
        if (StringUtils.isBlank(customerId)) {
            throw new BadRequestAlertException("No Customer", ENTITY_NAME, "noid");
        }
        final Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            final Customer customer = customerOptional.get();
            final Set<Order> orderSet = customer.getOrders().stream().map(o -> Objects.equals(o.getId(), order.getId()) ? order : o).collect(Collectors.toSet());
            customer.setOrders(orderSet);
            customerRepository.save(customer);
            return ResponseEntity.ok()
                    .body(order);
        } else {
            throw new BadRequestAlertException("Invalid Customer", ENTITY_NAME, "invalidcustomer");
        }
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @param customerId the id of the customer.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/customerOrders/{customerId}")
    public Set<Order> getAllOrders(@PathVariable String customerId) {
        log.debug("REST request to get all Orders for Customer: {}", customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new BadRequestAlertException("No Customer", ENTITY_NAME, "noid");
        }
        final Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            final Customer customer = customerOptional.get();
            return customer.getOrders();
        } else {
            throw new BadRequestAlertException("Invalid Customer", ENTITY_NAME, "invalidcustomer");
        }
    }

    /**
     * {@code GET  /orders/:customerId/:orderId} : get the "orderId" order for the "customerId" customer.
     *
     * @param customerId the id of the customer.
     * @param orderId    the id of the order to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the order, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customerOrders/{customerId}/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String customerId, @PathVariable String orderId) {

        log.debug("REST request to get Order : {} for Customer: {}", orderId, customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new BadRequestAlertException("No Customer", ENTITY_NAME, "noid");
        }
        final Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            final Customer customer = customerOptional.get();
            final Optional<Order> orderOptional = customer.getOrders().stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst();
            return ResponseUtil.wrapOrNotFound(orderOptional);
        } else {
            throw new BadRequestAlertException("Invalid Customer", ENTITY_NAME, "invalidcustomer");
        }
    }

    /**
     * {@code DELETE  /orders/:customerId/:orderId} : delete the "orderId" order for the "customerId" customer.
     *
     * @param customerId the id of the customer.
     * @param orderId    the id of the order to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customerOrders/{customerId}/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String customerId, @PathVariable String orderId) {
        log.debug("REST request to delete Order : {} for Customer: {}", orderId, customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new BadRequestAlertException("No Customer", ENTITY_NAME, "noid");
        }
        final Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            final Customer customer = customerOptional.get();
            customer.getOrders().removeIf((order) -> Objects.equals(order.getId(), orderId));
            customerRepository.save(customer);
            return ResponseEntity.noContent().build();
        } else {
            throw new BadRequestAlertException("Invalid Customer", ENTITY_NAME, "invalidcustomer");
        }
    }
}
