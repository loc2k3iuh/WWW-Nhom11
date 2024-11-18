package com.project.shopapp.services.order;

import com.project.shopapp.dtos.requests.OrderDTO;
import com.project.shopapp.dtos.responses.order.OrderResponse;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrder(int id) throws DataNotFoundException;

    OrderResponse updateOrder(int id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(int id);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> findByUserId(int userId) throws DataNotFoundException;
}
