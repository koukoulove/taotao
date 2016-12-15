package com.taotao.manage.service;


public interface PropertieService {
    
    // 文件新路径
    public String getFilePath(String sourceFileName);
    
    // 生成图片的绝对引用地址
    public String getPicUrl(String filePath);
}
