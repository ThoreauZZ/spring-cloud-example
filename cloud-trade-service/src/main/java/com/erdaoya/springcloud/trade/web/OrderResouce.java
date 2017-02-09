package com.erdaoya.springcloud.trade.web;

import com.erdaoya.springcloud.trade.entity.Order;
import com.gomeplus.oversea.bs.common.exception.code4xx.C404Exception;
import lombok.extern.slf4j.Slf4j;
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
        if (id == 1) {
            Order order = new Order();
            order.setId(1L);
            order.setCustomerId(1L);
            order.setPrice(12.32);
            order.setSellerId(2L);
            return order;
        } else {
            throw new C404Exception("订单不存在");
        }

    }
}
