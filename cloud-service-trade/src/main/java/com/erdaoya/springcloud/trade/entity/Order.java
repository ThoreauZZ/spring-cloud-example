package com.erdaoya.springcloud.trade.entity;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author erdaoya
 */
@Data
@ToString
public class Order {
    private Long id;
    private Double price;
    private Long customerId;
    private Long itemId;
    private Long sellerId;
}
