package com.yootk.web.action;

import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.bean.ModeAndView;

@Controller
@RequestMapping("/pages/back/admin/dept/")
public class DeptAction {
    @RequestMapping("dept_add_pre")
    public ModeAndView add_pre() {
        ModeAndView mav = new ModeAndView("/pages/back/admin/dept/dept_add.jsp") ;
        mav.add("dname", "沐言科技");
        mav.add("loc", "北京");
        return mav ; // 直接返回页面路径
    }
    @RequestMapping("dept_add")
    public void add() {
        System.out.println("******************【DeptAction.add()】部门增加。");
    }
    @RequestMapping("dept_list")
    public void list() {
        System.out.println("******************【DeptAction.list()】部门列表");
    }
}
