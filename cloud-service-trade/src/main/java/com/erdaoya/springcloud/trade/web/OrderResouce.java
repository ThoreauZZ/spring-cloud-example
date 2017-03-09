package com.erdaoya.springcloud.trade.web;

import com.erdaoya.springcloud.common.exception.code4xx.C404Exception;
import com.erdaoya.springcloud.trade.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RefreshScope
@RequestMapping("/trade/order")
public class OrderResouce {

    @Value("${testvalue.price}")
    private Double price;

    @RequestMapping(method = RequestMethod.GET)
    public Order doGet(@RequestParam Long id) {
        if (id == 1) {
            Order order = new Order();
            order.setId(id);
            order.setCustomerId(1L);
            order.setPrice(price);
            order.setSellerId(2L);
            return order;
        } else {
            throw new C404Exception("订单不存在");
        }

    }
}
