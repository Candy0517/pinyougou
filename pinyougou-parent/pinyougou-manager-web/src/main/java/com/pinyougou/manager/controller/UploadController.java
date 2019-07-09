package com.pinyougou.manager.controller;


import com.pinyougou.uitls.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private  String fILE_SERVER_URL;



    @RequestMapping("/upload")
    public Result upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();//文件名全称
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");

            String path = fastDFSClient.uploadFile(file.getBytes(), extName);

            String url=fILE_SERVER_URL+path;

            return new Result(true,url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败！");
        }
    }

}
