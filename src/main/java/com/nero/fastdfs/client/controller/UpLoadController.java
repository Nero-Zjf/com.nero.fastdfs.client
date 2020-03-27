package com.nero.fastdfs.client.controller;

import com.nero.fastdfs.client.fastdfs.FastDFSClient;
import com.nero.fastdfs.client.fastdfs.FastDFSException;
import com.nero.fastdfs.client.fastdfs.FileResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 上传Controller
 */
@RestController
public class UpLoadController {
    /**
     * 文件服务器地址
     */
    @Value("${file_server_addr}")
    private String fileServerAddr;

    /**
     * FastDFS秘钥
     */
    @Value("${fastdfs.http_secret_key}")
    private String fastDFSHttpSecretKey;

    @GetMapping("/test")
    public String test(){
        return "ok";
    }
    @PostMapping("/upload")
    public FileResponseData upLoad(MultipartFile file, HttpServletRequest request){
          return  uploadSample(file,request);
    }

    /**
     * 上传通用方法，只上传到服务器，不保存记录到数据库
     *
     * @param file
     * @param request
     * @return
     */
    public FileResponseData uploadSample(MultipartFile file, HttpServletRequest request){
        FileResponseData responseData = new FileResponseData();
        FastDFSClient fastDFSClient = new FastDFSClient();
        try {
            // 上传到服务器
            String filepath = fastDFSClient.uploadFileWithMultipart(file);

            responseData.setFileName(file.getOriginalFilename());
            responseData.setFilePath(filepath);
            responseData.setFileType(FastDFSClient.getFilenameSuffix(file.getOriginalFilename()));
            // 设置访文件的Http地址. 有时效性.
            String token = FastDFSClient.getToken(filepath, fastDFSHttpSecretKey);
            responseData.setToken(token);
            responseData.setHttpUrl(fileServerAddr+"/"+filepath+"?"+token);
//            responseData.setHttpUrl(fileServerAddr+"/"+filepath);
        } catch (FastDFSException e) {
            responseData.setSuccess(false);
            responseData.setCode(e.getCode());
            responseData.setMessage(e.getMessage());
        }

        return responseData;
    }

    /**
     * 以附件形式下载文件
     *
     * @param filePath 文件地址
     * @param response
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws FastDFSException {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient();
            fastDFSClient.downloadFile(filePath, response);
        } catch (FastDFSException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据指定的路径删除服务器文件，适用于没有保存数据库记录的文件
     *
     * @param filePath
     */
    @DeleteMapping("/delete")
    public FileResponseData deleteFile(@RequestParam("filePath") String filePath, Locale locale) {
        FileResponseData responseData = new FileResponseData();
        FastDFSClient fastDFSClient = new FastDFSClient();
        try {
            fastDFSClient.deleteFile(filePath);
        } catch (FastDFSException e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setCode(e.getCode());
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}
