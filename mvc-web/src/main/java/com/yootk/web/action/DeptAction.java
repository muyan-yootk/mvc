package com.yootk.web.action;

import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/back/admin/dept/")
public class DeptAction {
    @RequestMapping("dept_add")
    public void add() {
        System.out.println("******************【DeptAction.add()】部门增加。");
    }
    @RequestMapping("dept_list")
    public void list() {
        System.out.println("******************【DeptAction.list()】部门列表");
    }
}
