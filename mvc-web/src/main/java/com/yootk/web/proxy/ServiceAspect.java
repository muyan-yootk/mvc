package com.yootk.web.proxy;

import com.yootk.common.proxy.ProxyAspect;

public class ServiceAspect implements ProxyAspect {
    @Override
    public void before() {
        System.out.println("【ServiceAspect.before()】进行数据库的连接，并且开启数据库事务。");
    }

    @Override
    public void after() {
        System.out.println("【ServiceAspect.after()】进行数据库的关闭，并且提交数据库事务。");
    }

    @Override
    public void exception() {
        System.out.println("【ServiceAspect.exception()】回滚事务，并且关闭数据库连接。");
    }
}
