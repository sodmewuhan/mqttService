package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.conf.UpdateVersion;
import com.datasensorn.mqttservice.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 版本更新，下载APK服务
 */
@RestController
@RequestMapping(value="/api/updateVersion")
public class UpdateVersionRestControll {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateVersionRestControll.class);

    @Autowired
    private UpdateVersion updateVersion;

    @RequestMapping("/download")
    public Result downloadFile(HttpServletRequest request, HttpServletResponse response) {
        StopWatch stopWatch = new StopWatch();
        ResultGenerator resultGenerator = new ResultGenerator();
        String version = request.getParameter("version");
        if (StringUtils.isEmpty(version)) {
            version = "1.0";
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            stopWatch.start("1. 获取APK文件的下载地址");
            File path = new File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath());
            String filePath = updateVersion.getStoreFilePath() + "/" + version + "/" + Constant.APP_NAME;
            File fileDownloadPath = new File(path.getAbsolutePath(),filePath);
            if (!fileDownloadPath.exists()) {
                return resultGenerator.genFailResult("sorry,the download file is not exist.");
            }
            LOGGER.info("the fileDownloadPath is " + fileDownloadPath.getAbsolutePath());
            stopWatch.stop();

            stopWatch.start("2.开始下载");
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileDownloadPath);// 设置文件名
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(fileDownloadPath);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            stopWatch.stop();
            LOGGER.info(stopWatch.prettyPrint());
        } catch (Exception e) {
            LOGGER.info("the download app file is failure.",e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                LOGGER.info("the close io resource is failed.",e);
            }
        }
        return resultGenerator.genSuccessResult();
    }


}
