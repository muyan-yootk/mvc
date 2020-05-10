package com.yootk.test;

import com.yootk.common.bean.MethodParameterUtil;
import com.yootk.web.action.DeptAction;
import org.junit.Test;

public class TestMethod {
    @Test
    public void get() {
        System.out.println(MethodParameterUtil.getMethodParameter(DeptAction.class, "add"));
    }
}
