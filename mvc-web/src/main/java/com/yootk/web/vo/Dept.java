package com.yootk.web.vo;

public class Dept {
    private Long deptno ;
    private String dname ;
    private String loc ;

    public Long getDeptno() {
        return deptno;
    }

    public void setDeptno(Long deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "部门编号 = " + this.deptno + "、部门名称 = " + this.dname + "、部门位置：" + this.loc;
    }
}
