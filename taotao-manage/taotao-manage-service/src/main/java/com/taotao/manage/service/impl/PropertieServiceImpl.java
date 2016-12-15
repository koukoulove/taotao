package com.taotao.manage.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.manage.service.PropertieService;

@Service
public class PropertieServiceImpl implements PropertieService{

    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;

    @Override
    public String getPicUrl(String filePath) {
        return IMAGE_BASE_URL + StringUtils.replace(StringUtils.substringAfter(filePath, REPOSITORY_PATH),"\\", "/");
    }

    @Override
    //D:\\taotao-upload\images\2016\01\17\2016011704532743905941.jpg
    public String getFilePath(String sourceFileName) {
        String baseFolder = REPOSITORY_PATH + File.separator + "images";
        Date nowDate = new Date();
        // yyyy/MM/dd
        String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
                + File.separator + new DateTime(nowDate).toString("MM") + File.separator
                + new DateTime(nowDate).toString("dd");
        File file = new File(fileFolder);
        if (!file.isDirectory()) {
            // 如果目录不存在，则创建目录
            file.mkdirs();
        }
        // 生成新的文件名
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
                + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder + File.separator + fileName;
    }
    
}
