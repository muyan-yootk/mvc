package com.yootk.common.bean;

import java.io.File;

public class ScannerPackageUtil {
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
            listPackageDir(file); // 列出所有的子目录组成
        }
    }
    private static void listPackageDir(File file) {
        if (file.isDirectory()) {   // 给定的File是一个目录
            File result [] = file.listFiles() ; // 列出目录组成
            if (result != null) {
                for (int x = 0 ; x < result.length ; x ++) {
                    listPackageDir(result[x]); // 持续列出子目录
                }
            }
        } else {
            if (file.isFile()) {
                System.out.println(file);
            }
        }
    }
}
