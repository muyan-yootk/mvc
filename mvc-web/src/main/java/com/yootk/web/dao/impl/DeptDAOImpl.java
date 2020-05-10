package com.yootk.web.dao.impl;

import com.yootk.common.annotation.Repository;
import com.yootk.web.dao.IDeptDAO;
import com.yootk.web.vo.Dept;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DeptDAOImpl implements IDeptDAO {
    @Override
    public boolean doCreate(Dept vo) throws SQLException {
        System.out.println("【DeptDAO.doCreate()】" + vo);
        return true;
    }

    @Override
    public List<Dept> findAll() throws SQLException {
        System.out.println("【DeptDAO.findAll()】查询数据表的全部数据记录。");
        List<Dept> allDepts = new ArrayList<>() ;
        for (int x = 0; x < 10; x++) {
            Dept dept = new Dept() ;
            dept.setDeptno(x + 10L);
            dept.setDname("沐言科技 - " + x);
            dept.setLoc("北京");
            allDepts.add(dept) ;
        }
        return allDepts;
    }
}
