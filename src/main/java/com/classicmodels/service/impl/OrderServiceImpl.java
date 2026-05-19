package com.classicmodels.service.impl;

import com.classicmodels.dto.OrderDetailRequestDTO;
import com.classicmodels.dto.OrderDetailResponseDTO;
import com.classicmodels.dto.OrderRequestDTO;
import com.classicmodels.dto.OrderResponseDTO;
import com.classicmodels.dto.OrderStatusUpdateDTO;
import com.classicmodels.entity.Customer;
import com.classicmodels.entity.Order;
import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import com.classicmodels.entity.Product;
import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.repository.ProductRepository;
import com.classicmodels.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository       orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository    customerRepository;
    private final ProductRepository     productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository) {
        this.orderRepository       = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository    = customerRepository;
        this.productRepository     = productRepository;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  POST /api/orders
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        // Validate customer exists
        Customer customer = customerRepository.findById(dto.getCustomerNumber())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + dto.getCustomerNumber()));

        // Build entity from request DTO fields
        Order order = new Order();
        order.setOrderNumber(dto.getOrderNumber());
        order.setOrderDate(dto.getOrderDate());
        order.setRequiredDate(dto.getRequiredDate());
        order.setShippedDate(dto.getShippedDate());
        order.setStatus(dto.getStatus());
        order.setComments(dto.getComments());
        order.setCustomer(customer);

        Order saved = orderRepository.save(order);
        return toOrderResponseDTO(saved);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GET /api/orders
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toOrderResponseDTO)
                .toList();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GET /api/orders/{orderNumber}
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    public OrderResponseDTO getOrderById(Integer orderNumber) {
        Order order = findOrderOrThrow(orderNumber);
        return toOrderResponseDTO(order);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PUT /api/orders/{orderNumber}
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO dto) {

        Order existing = findOrderOrThrow(orderNumber);

        // Update only non-null fields from the request DTO
        if (dto.getOrderDate()    != null) existing.setOrderDate(dto.getOrderDate());
        if (dto.getRequiredDate() != null) existing.setRequiredDate(dto.getRequiredDate());
        if (dto.getShippedDate()  != null) existing.setShippedDate(dto.getShippedDate());
        if (dto.getStatus()       != null) existing.setStatus(dto.getStatus());
        if (dto.getComments()     != null) existing.setComments(dto.getComments());

        // Re-link customer if customerNumber has changed
        if (dto.getCustomerNumber() != null &&
                !dto.getCustomerNumber().equals(existing.getCustomer().getCustomerNumber())) {

            Customer newCustomer = customerRepository.findById(dto.getCustomerNumber())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Customer not found with ID: " + dto.getCustomerNumber()));
            existing.setCustomer(newCustomer);
        }

        return toOrderResponseDTO(orderRepository.save(existing));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PATCH /api/orders/{orderNumber}/status
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderStatusUpdateDTO dto) {
        Order existing = findOrderOrThrow(orderNumber);
        existing.setStatus(dto.getStatus());
        return toOrderResponseDTO(orderRepository.save(existing));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GET /api/customers/{customerNumber}/orders
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber) {
        return orderRepository.findByCustomer_CustomerNumber(customerNumber)
                .stream()
                .map(this::toOrderResponseDTO)
                .toList();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GET /api/orders/search?status=&fromDate=&toDate=
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    public List<OrderResponseDTO> searchOrders(String status,
                                               LocalDate fromDate,
                                               LocalDate toDate) {
        return orderRepository.searchOrders(status, fromDate, toDate)
                .stream()
                .map(this::toOrderResponseDTO)
                .toList();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  POST /api/orders/{orderNumber}/items
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderDetailResponseDTO addItem(Integer orderNumber, OrderDetailRequestDTO dto) {

        // Validate order exists
        Order order = findOrderOrThrow(orderNumber);

        // Validate product exists
        Product product = productRepository.findById(dto.getProductCode())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product not found with code: " + dto.getProductCode()));

        // Check duplicate line item
        if (orderDetailRepository.existsById_OrderNumberAndId_ProductCode(
                orderNumber, dto.getProductCode())) {
            throw new IllegalStateException(
                    "Product " + dto.getProductCode() + " already exists in order " + orderNumber);
        }

        // Build OrderDetail entity using the helper setters on the entity
        // (those setters auto-populate the composite key)
        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantityOrdered(dto.getQuantityOrdered());
        detail.setPriceEach(dto.getPriceEach());
        detail.setOrderLineNumber(dto.getOrderLineNumber());

        return toOrderDetailResponseDTO(orderDetailRepository.save(detail));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GET /api/orders/{orderNumber}/items
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    public List<OrderDetailResponseDTO> getItems(Integer orderNumber) {
        findOrderOrThrow(orderNumber); // guard — ensure order exists
        return orderDetailRepository.findById_OrderNumber(orderNumber)
                .stream()
                .map(this::toOrderDetailResponseDTO)
                .toList();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PUT /api/orders/{orderNumber}/items/{productCode}
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderDetailResponseDTO updateItem(Integer orderNumber,
                                             String productCode,
                                             OrderDetailRequestDTO dto) {

        OrderDetail existing = orderDetailRepository
                .findById(new OrderDetailId(orderNumber, productCode))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order detail not found for order " + orderNumber
                                + " and product " + productCode));

        // Update only non-null fields
        if (dto.getQuantityOrdered() != null) existing.setQuantityOrdered(dto.getQuantityOrdered());
        if (dto.getPriceEach()       != null) existing.setPriceEach(dto.getPriceEach());
        if (dto.getOrderLineNumber() != null) existing.setOrderLineNumber(dto.getOrderLineNumber());

        return toOrderDetailResponseDTO(orderDetailRepository.save(existing));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DELETE /api/orders/{orderNumber}/items/{productCode}
    // ══════════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public OrderDetailResponseDTO removeItem(Integer orderNumber, String productCode) {

        // Fetch the item first so we can return it in the response
        OrderDetail existing = orderDetailRepository
                .findById(new OrderDetailId(orderNumber, productCode))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order detail not found for order " + orderNumber
                                + " and product " + productCode));

        // Convert to DTO before deleting (entity will be gone after delete)
        OrderDetailResponseDTO deletedRow = toOrderDetailResponseDTO(existing);

        orderDetailRepository.deleteByOrderNumberAndProductCode(orderNumber, productCode);

        return deletedRow;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Private helpers — inline entity ↔ DTO conversion (no mapper)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Converts an {@link Order} entity to an {@link OrderResponseDTO}.
     * customerName is pulled from the associated Customer entity.
     */
    private OrderResponseDTO toOrderResponseDTO(Order order) {

        String customerName = (order.getCustomer() != null)
                ? order.getCustomer().getCustomerName()
                : null;

        Integer customerNumber = (order.getCustomer() != null)
                ? order.getCustomer().getCustomerNumber()
                : null;

        return new OrderResponseDTO(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getRequiredDate(),
                order.getShippedDate(),
                order.getStatus(),
                order.getComments(),
                customerNumber,
                customerName
        );
    }

    /**
     * Converts an {@link OrderDetail} entity to an {@link OrderDetailResponseDTO}.
     * productName is pulled from the associated Product entity.
     * lineTotal is calculated inline as quantity × priceEach.
     */
    private OrderDetailResponseDTO toOrderDetailResponseDTO(OrderDetail detail) {

        String productName = (detail.getProduct() != null)
                ? detail.getProduct().getProductName()
                : null;

        BigDecimal lineTotal =
                (detail.getQuantityOrdered() != null && detail.getPriceEach() != null)
                        ? detail.getPriceEach()
                        .multiply(BigDecimal.valueOf(detail.getQuantityOrdered()))
                        : BigDecimal.ZERO;

        Integer orderNumber = (detail.getId() != null) ? detail.getId().getOrderNumber() : null;
        String  productCode = (detail.getId() != null) ? detail.getId().getProductCode() : null;

        return new OrderDetailResponseDTO(
                orderNumber,
                productCode,
                productName,
                detail.getQuantityOrdered(),
                detail.getPriceEach(),
                lineTotal,
                detail.getOrderLineNumber()
        );
    }

    /**
     * Guard helper — throws {@link EntityNotFoundException} if the order does not exist.
     */
    private Order findOrderOrThrow(Integer orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with orderNumber: " + orderNumber));
    }
}