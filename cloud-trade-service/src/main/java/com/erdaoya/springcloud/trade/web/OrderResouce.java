package com.erdaoya.springcloud.trade.web;

import com.erdaoya.springcloud.trade.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/trade/order")
public class OrderResouce {
    @RequestMapping(method = RequestMethod.GET)
    public Order doGet(@RequestParam Long id) {
        Order order = new Order();
        order.setId(123L);
        order.setCustomerId(1L);
        order.setPrice(12.32);
        order.setSellerId(2L);
        return order;
    }
}
