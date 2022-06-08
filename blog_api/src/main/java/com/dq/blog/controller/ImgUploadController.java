package com.dq.blog.controller;

import com.dq.blog.util.QiniuUtils;
import com.dq.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文章图片上传-API
 * @project: blog_parent
 * @ClassName: ImgUploadController
 * @author: dq
 * @creat: 2022/5/27 14:19
 */
@RestController
@RequestMapping("upload")
public class ImgUploadController {
    /**
     * 注入七牛 而不再直接使用
     */
    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //原始文件名 比如orgin.png
        String originalFilename = file.getOriginalFilename();
        //文件名 乱序处理 生成唯一的文件名称
        String fileName = UUID.randomUUID().toString() + StringUtils.substringAfter(originalFilename, ".");
        //上传文件 云服务器按量付费 速度快 发放最近的服务器上 降低自身应用的带宽消耗
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            //成功返回七牛的路径
            return Result.success(QiniuUtils.url+fileName);
        }
        //失败返回
        return Result.error(20001,"上传失败");
    }
}
