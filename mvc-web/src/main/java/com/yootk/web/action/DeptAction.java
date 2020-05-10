package com.yootk.web.action;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.bean.ModeAndView;
import com.yootk.web.service.IDeptService;

@Controller
@RequestMapping("/pages/back/admin/dept/")
public class DeptAction {
    @Autowired // 此时成员是需要通过外部注入配置的
    private IDeptService deptService ; // 业务层接口对象
    @RequestMapping("dept_add_pre")
    public ModeAndView add_pre() {
        ModeAndView mav = new ModeAndView("/pages/back/admin/dept/dept_add.jsp") ;
        mav.add("dname", "沐言科技");
        mav.add("loc", "北京");
        return mav ; // 直接返回页面路径
    }
    @RequestMapping("dept_add")
    public void add() {
        try {
            this.deptService.add(null) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("dept_list")
    public void list() {
        try {
            System.out.println(this.deptService.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
