package com.erdaoya.springcloud.user.dao;

import com.erdaoya.springcloud.user.entity.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author erdaoya
 * @date 16/12/25 下午5:54
 */
@Repository
public class UserDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public User selectUserById(String id){
        return sqlSessionTemplate.selectOne("selectUserById", id);
    }

    public void insertUser(User user) {
        sqlSessionTemplate.insert("insertUser", user);
    }
}
