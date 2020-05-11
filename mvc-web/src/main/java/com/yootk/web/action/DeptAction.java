package com.yootk.web.action;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.bean.ModeAndView;
import com.yootk.common.servlet.bean.MultipartFile;
import com.yootk.common.util.WebObjectUtil;
import com.yootk.web.service.IDeptService;
import com.yootk.web.vo.Dept;

import java.util.UUID;

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
    public void add(Dept dept, MultipartFile photo) { // 上传文件
        try {
            System.out.println("***【上传文件】文件名称：" + photo.getOriginFilename());
            System.out.println("***【上传文件】文件类型：" + photo.getContentType());
            System.out.println("***【上传文件】文件大小：" + photo.length());
            String fileName = UUID.randomUUID() + "." + photo.getContentType().substring(photo.getContentType().lastIndexOf("/") + 1) ;
            String savePath = WebObjectUtil.getApplication().getRealPath("/upload/") + fileName ;
            photo.transfer(savePath) ; // 文件保存
            this.deptService.add(dept) ;
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
