package com.nero.fastdfs.client.controller;

import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 上传Controller
 */
@RestController
public class UpLoadController1 {
    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @PostMapping("/upload")
    public String upLoad(MultipartFile file, HttpServletRequest request) throws IOException {
        //打开tracker
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //从输入流读取文件
        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        isr.re
//        storageClient.uploadfile

        String path = "";
        return path;
    }


//
//    /**
//     * 以附件形式下载文件
//     *
//     * @param filePath 文件地址
//     * @param response
//     */
//    @GetMapping("/download")
//    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws FastDFSException {
//        try {
//            FastDFSClient fastDFSClient = new FastDFSClient();
//            fastDFSClient.downloadFile(filePath, response);
//        } catch (FastDFSException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    /**
//     * 根据指定的路径删除服务器文件，适用于没有保存数据库记录的文件
//     *
//     * @param filePath
//     */
//    @DeleteMapping("/delete")
//    public FileResponseData deleteFile(@RequestParam("filePath") String filePath, Locale locale) {
//        FileResponseData responseData = new FileResponseData();
//        FastDFSClient fastDFSClient = new FastDFSClient();
//        try {
//            fastDFSClient.deleteFile(filePath);
//        } catch (FastDFSException e) {
//            e.printStackTrace();
//            responseData.setSuccess(false);
//            responseData.setCode(e.getCode());
//            responseData.setMessage(e.getMessage());
//        }
//        return responseData;
//    }
}
