package com.yootk.web.service.impl;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.annotation.Service;
import com.yootk.web.dao.IDeptDAO;
import com.yootk.web.service.IDeptService;
import com.yootk.web.vo.Dept;

import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private IDeptDAO deptDAO ;
    @Override
    public List<Dept> list() throws Exception {
        System.out.println("【DeptService.list()】部门信息列表");
        return this.deptDAO.findAll();
    }

    @Override
    public boolean add(Dept vo) throws Exception {
        System.out.println("【DeptService.add()】部门信息增加");
        return this.deptDAO.doCreate(vo);
    }
}
