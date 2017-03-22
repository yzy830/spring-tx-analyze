package org.springframework.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.domain.Blog;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private JdbcTemplate jtm;

    @Override
    @Transactional
    public Blog test() {
        String sql = "select * from blog where id = 1";
        
        return jtm.queryForObject(sql, BeanPropertyRowMapper.newInstance(Blog.class));
    }
}
