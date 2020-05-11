package com.yootk.common.servlet.bean;

import java.io.*;

public class MultipartFile extends File { // 该类需要拥有文件的全部功能
    private String contentType ; // MIME类型
    private String originFilename ; // 原始文件名称
    public MultipartFile(String path) {
        super(path) ;
    }
    public MultipartFile(File parent, String path) {
        super(parent, path) ;
    }
    public MultipartFile(String path, String contentType, String originFilename) {
        super(path) ;
        this.contentType = contentType ;
        this.originFilename = originFilename ;
    }
    public MultipartFile(File parent, String path, String contentType, String originFilename) {
        super(parent, path) ;
        this.contentType = contentType ;
        this.originFilename = originFilename ;
    }

    public boolean transfer(String savePath) { // 文件转存
        boolean flag = false ;
        InputStream input = null ;
        OutputStream output = null ;
        try {
            File outFile = new File(savePath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs() ;
            }
            output = new FileOutputStream(savePath) ;
            input = new FileInputStream(this) ; // 输入流
            byte data[] = new byte[1024] ; // 每次拷贝的长度
            int temp = 0 ;
            while ((temp = input.read(data)) != -1) {
                output.write(data, 0, temp);
            }
            flag = true ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag ;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public void setOriginFilename(String originFilename) {
        this.originFilename = originFilename;
    }
}
