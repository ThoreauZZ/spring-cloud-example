package com.erdaoya.springcloud.user.dao;

import com.erdaoya.springcloud.user.entity.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private SqlSessionTemplate sqlSessionTemplate;

    public User selectUserById(Long id) {
        return sqlSessionTemplate.selectOne("selectUserById", id);
    }

    public void insertUser(User user) {
        sqlSessionTemplate.insert("insertUser", user);
    }
}
