package com.yootk.web.dao;

import com.yootk.web.vo.Dept;

import java.sql.SQLException;
import java.util.List;

public interface IDeptDAO {
    public boolean doCreate(Dept vo) throws SQLException ;
    public List<Dept> findAll() throws SQLException ;
}
