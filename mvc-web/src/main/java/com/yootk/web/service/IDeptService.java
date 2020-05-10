package com.yootk.web.service;

import com.yootk.web.vo.Dept;

import java.util.List;

public interface IDeptService {
    public List<Dept> list() throws Exception ; // 获取全部数据
    public boolean add(Dept vo) throws Exception ; // 增加部门数据
}
