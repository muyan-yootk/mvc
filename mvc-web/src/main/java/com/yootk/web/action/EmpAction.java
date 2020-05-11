package com.yootk.web.action;

import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/back/admin/emp/")
public class EmpAction {
    @RequestMapping("emp_add")
    public void add(long empno) {}
}
