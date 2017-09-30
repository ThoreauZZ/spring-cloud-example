package com.erdaoya.springcloud.config.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 17/9/30 下午7:42.
 *
 * @author thoreau
 */
@Entity
@Data
public class Config {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String property;
    @Column(nullable = false)
    private String value;
}
