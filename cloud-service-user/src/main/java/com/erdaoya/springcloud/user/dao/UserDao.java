package com.erdaoya.springcloud.user.dao;

import com.erdaoya.springcloud.user.entity.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Repository;

/**
 * 2017/1/13
 *
 * @author erdaoya
 * @since 1.0
 */
@Repository
public class UserDao {

    @Autowired
    private Tracer tracer;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public User selectUserById(Long id) {
        Span span = tracer.createSpan("selectUserById-mysql",tracer.getCurrentSpan());
        span.tag("id", ""+id);
        User user;
        try {
            user = sqlSessionTemplate.selectOne("selectUserById", id);
        }finally {
            tracer.close(span);
        }
        return user;
    }

    public void insertUser(User user) {
        sqlSessionTemplate.insert("insertUser", user);
    }
}
