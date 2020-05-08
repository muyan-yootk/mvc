package com.yootk.common.bean;

import java.util.HashMap;
import java.util.Map;

public class ModeAndView {
    private String path ; // 跳转路径
    private Map<String, Object> attributes = new HashMap<String, Object>() ;
    public ModeAndView(String path) {
        this.path = path ;
    }
    public void add(String key, Object value) {
        this.attributes.put(key, value);
    }
    public void addAll(Map<String, Object> attr) {
        this.attributes.putAll(attr);
    }
    public String getPath() {
        return this.path ;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
