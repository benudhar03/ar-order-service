package com.order.service.service;

import com.order.service.dto.OrderRequest;
import com.order.service.dto.OrderResponse;
import com.order.service.dto.PagedResponse;
import com.order.service.dto.ResponseObject;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    ResponseObject createOrder(OrderRequest request);
    PagedResponse<OrderResponse> getOrdersByUser(String userId, Pageable pageable);
}

