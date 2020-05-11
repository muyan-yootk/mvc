package com.yootk.common.util;

import com.yootk.common.servlet.bean.MultipartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class ParameterUtil {
    public static final long MAX_SIZE = 3145728L; // 表单允许的最大上传数量为3M
    public static final long FILE_MAX_SIZE = 1048576L ; // 表示单个文件允许上传的最大长度，本次为1M
    public static final String DEFAULT_TEMP_DIR = "/tmp" ; // 设置临时的目录
    public static final String DEFAULT_UPLOAD_DIR = "/upload" ; // 设置临时的目录
    public static final String DEFAULT_ENCODING = "UTF-8" ; // 设置参数的接收编码
    private HttpServletRequest request ;
    private String uploadFile ;
    private String tempFile ;
    private String encoding ;
    private long maxSize ;
    private long fileMaxSize ;
    private boolean uploadFlag = false ; // 如果现在表单有封装此内容为true，如果没有封装为false
    private ServletFileUpload fileUpload ; // FileUpload的核心处理类
    // 对于普通的文本型的参数保存的是表单提交的内容
    // 如果现在需要进行的是二进制的参数，保存的是存储的文件名称
    private Map<String,String[]> paramMap = new HashMap<>() ;   // 保存最终的处理结果
    // 保存所有的上传文件内容，但是所有的文件都必须包装，目的是为了获取具体的文件详情
    private Map<String, List<MultipartFile>> allUploadFile = new HashMap<>() ;
    private List<String> tempFileNames = new ArrayList<>() ; // 保存临时文件的名称
    /**
     * 构建一个默认的参数处理操作实例
     * @param request HTTP请求对象
     */
    public ParameterUtil(HttpServletRequest request) {
        this(request,DEFAULT_UPLOAD_DIR,DEFAULT_TEMP_DIR,MAX_SIZE,FILE_MAX_SIZE,DEFAULT_ENCODING) ;
    }
    /**
     * 构建参数工具类，同时设置上传文件保存的父目录
     * @param request HTTP请求对象
     * @param uploadFile 文件存储的父目录
     */
    public ParameterUtil(HttpServletRequest request,String uploadFile) {
        this(request,uploadFile,DEFAULT_TEMP_DIR,MAX_SIZE,FILE_MAX_SIZE,DEFAULT_ENCODING) ;
    }
    /**
     * 构建一个上传工具类
     * @param request HTTP请求对象
     * @param uploadFile 文件存储的父目录
     * @param tempFile 临时存放目录
     * @param maxSize 上传的最大限制
     * @param fileMaxSize 每个文件的最大限制
     * @param encoding 默认读取文字编码
     */
    public ParameterUtil(HttpServletRequest request,String uploadFile,String tempFile,long maxSize,long fileMaxSize,String encoding) {
        this.request = request ;
        if (uploadFile.endsWith("/")) {
            this.uploadFile = uploadFile;
        } else {
            this.uploadFile = uploadFile + "/";
        }
        this.tempFile = tempFile ;
        this.maxSize = maxSize ;
        this.fileMaxSize = fileMaxSize ;
        this.encoding = encoding ;
        this.handleParameter(); // 进行参数的处理
    }
    private void handleParameter() {
        if ((this.uploadFlag = this.request.getContentType() != null &&
                this.request.getContentType().startsWith("multipart/form-data"))) {  // 此时存在有文件上传
            DiskFileItemFactory factory = new DiskFileItemFactory() ;
            File temp = new File(this.tempFile) ;
            if (!temp.exists()) {
                temp.mkdirs();
            }
            factory.setRepository(temp); // 设置所有上传的临时存储目录
            this.fileUpload = new ServletFileUpload(factory) ; // 定义上传处理类
            this.fileUpload.setSizeMax(this.maxSize);    // 设置允许上传的总长度限制
            this.fileUpload.setFileSizeMax(this.fileMaxSize); // 设置单个上传文件的大小限制
            if (fileUpload.isMultipartContent(this.request)) {   // 判断当前的表单是否属于封装状态
                try {
                    Map<String, List<FileItem>> map = this.fileUpload.parseParameterMap(request) ; // 解析所有的上传参数
                    for (Map.Entry<String,List<FileItem>> entry : map.entrySet()) {
                        String paramName = entry.getKey(); // 获取请求参数的名称
                        List<FileItem> allItems = entry.getValue(); // 获取请求参数的内容
                        String [] values = new String[allItems.size()] ;   // 保存数据集合
                        int foot = 0 ;
                        for (FileItem item : allItems) {
                            if (item.isFormField()) {   // 内容为普通文本
                                String value = item.getString(this.encoding);
                                values[foot ++] = value ; // 保存内容
                            } else {    // 现在不是普通文本数据，需要考虑文件存储
                                values[foot ++] = this.saveUploadFile(paramName, item) ; // 保存文件并返回文件名称
                            }
                        }
                        this.paramMap.put(paramName,values) ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String saveUploadFile(FileItem item) throws Exception {
        if (item.getSize() > 0) {   // 现在有上传的文件
            String fileName = UUID.randomUUID() + "." + item.getContentType().substring(item.getContentType().lastIndexOf("/") + 1);
            String filePath = this.request.getServletContext().getRealPath(this.uploadFile) + fileName;  // 文件保存路径
            item.write(new File(filePath)); // 进行文件存储
            return fileName ;
        }
        return "nophoto.jpg";
    }

    public Map<String, List<MultipartFile>> getAllUploadFile() {
        return allUploadFile;
    }

    public void clean() {   // 清除所有临时目录中的文件内容
        if (this.tempFileNames != null && this.tempFileNames.size() > 0) {
            for (String fileName : this.tempFileNames) {
                String filePath = this.request.getServletContext().getRealPath(this.tempFile) + File.separator + fileName ;
                File file = new File(filePath) ;
                if (file.exists()) {
                    file.delete() ;
                }
            }
        }
    }

    public String saveUploadFile(String paramName, FileItem item) throws Exception {
        if (item.getSize() > 0) {   // 有文件上传
            String fileName = UUID.randomUUID() + ".tmp" ; // 生成一个临时文件名称
            this.tempFileNames.add(fileName) ; // 如果现在保存成功或者不保存，临时文件必须删除
            String filePath = this.request.getServletContext().getRealPath(this.tempFile) + File.separator + fileName ; // 临时文件路径
            MultipartFile file = new MultipartFile(filePath) ; // 实例化媒体文件
            file.setOriginFilename(item.getName()); // 得到原始文件名称
            file.setContentType(item.getContentType()); // 获得原始文件MIME类型
            item.write(file); // FileUpload组件写入需要File支持
            List<MultipartFile> list = null ;
            if (this.allUploadFile.containsKey(paramName)) {    // 存在有这样的文件集合
                list = this.allUploadFile.get(paramName) ;
            } else {
                list = new ArrayList<>() ;
            }
            list.add(file) ; // 临时文件
            this.allUploadFile.put(paramName, list) ; // 待上传文件
            return fileName ;
        }
        return null ;
    }


    public String getParameter(String paramName) {  // 根据参数名称获取参数内容
        if (this.uploadFlag) {  // 有上传
            if (this.paramMap.containsKey(paramName)) {
                return this.paramMap.get(paramName)[0] ; // 获取里面的第1个元素
            }
            return null ;
        }
        return this.request.getParameter(paramName) ;
    }
    public String[] getParameterValues(String paramName) {  // 根据参数的名称获取一组参数内容
        if (this.uploadFlag) {  // 有上传
            if (this.paramMap.containsKey(paramName)) {
                return this.paramMap.get(paramName) ; // 获取里面的第1个元素
            }
            return null ;
        }
        return this.request.getParameterValues(paramName) ;
    }
    public Set<String> getParameterNames() {    // 返回所有参数的名称
        if (this.uploadFlag) {  // 有上传
            return this.paramMap.keySet() ;
        }
        return this.request.getParameterMap().keySet() ;
    }
    public Map<String,String[]> getParameterMap() { // 返回参数和内容的Map集合
        if (this.uploadFlag) {  // 有上传
            return this.paramMap ;
        }
        return this.request.getParameterMap();
    }
}