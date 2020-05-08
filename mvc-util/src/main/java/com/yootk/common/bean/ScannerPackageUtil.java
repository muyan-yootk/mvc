package com.yootk.common.bean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScannerPackageUtil {
    // 此时key描述的是请求路径，而Value描述的是该路径对应的Action以及Method反射对象
    // 目的：通过路径获取Action和Method信息并且通过反射进行控制层方法调用
    private final static Map<String, ControllerRequestMapping> ACTION_MAP = new HashMap<>() ;
    public static Map<String, ControllerRequestMapping> getActionMap() {
        return ACTION_MAP;
    }

    /**
     * 根据指定的扫描包的配置进行所有类的扫描处理
     * @param clazz 获取程序的起点类，通过这个起点类获取当前的工作路径
     * @param packages 所有动态配置的扫描包
     */
    public static void scannerHandle(Class<?> clazz, String packages) {    // 进行扫描包的配置
        if (packages == null || "".equals(packages)) {  // 当前的扫描包配置为空
            return ; // 操作直接结束
        }
        // 自定义开发框架的第一个痛点：需要考虑不同的操作系统平台；
        String resultPackages[] = packages.split(";"); // 不同的扫描包之间通过“;”进行分割
        String baseDir = clazz.getResource("/").getPath(); // 获取当前项目的父路径
        for (int x = 0; x < resultPackages.length; x++) {   // 此时配置了多个扫描包
            String subDir = resultPackages[x].replace(".", File.separator) ;
            File file = new File(baseDir + subDir.trim()) ;
            listPackageDir(baseDir.replace("/", File.separator).substring(1), file); // 列出所有的子目录组成
        }
    }

    /**
     * 根据指定的“父目录 + 扫描包名称”获取此包目录下的所有类文件
     * @param baseDir 项目父目录
     * @param file 子路径
     */
    private static void listPackageDir(String baseDir, File file) {
        if (file.isDirectory()) {   // 给定的File是一个目录
            File result [] = file.listFiles() ; // 列出目录组成
            if (result != null) {
                for (int x = 0 ; x < result.length ; x ++) {
                    listPackageDir(baseDir, result[x]); // 持续列出子目录
                }
            }
        } else {
            if (file.isFile()) {
                String className = file.getAbsolutePath().replace(baseDir, "").replace(File.separator, ".").replace(".class", "");
                try {
                    ACTION_MAP.putAll(new ConfigAnnotationParseUtil(Class.forName(className)).getResult());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
