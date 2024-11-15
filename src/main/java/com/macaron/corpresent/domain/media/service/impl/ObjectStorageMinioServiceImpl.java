package com.macaron.corpresent.domain.media.service.impl;


import cn.hutool.core.io.resource.ResourceUtil;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.media.FileResourceUtil;
import com.macaron.corpresent.common.util.media.MediaUtil;
import com.macaron.corpresent.domain.media.service.ObjectStorageService;
import com.macaron.corpresent.monio.config.MinioConfig;
import com.macaron.corpresent.monio.engine.MinioBucketEngine;
import com.macaron.corpresent.monio.engine.MinioEngine;
import com.macaron.corpresent.monio.enums.MinioPolicyTemplateEnum;
import com.macaron.corpresent.monio.template.DefaultPolicyTemplate;
import com.macaron.corpresent.template.engine.TextEngine;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-23
 * Time: 10:32
 */

/**
 * 本服务由 SPI 加载，故必须提供无参构造方法
 */
public class ObjectStorageMinioServiceImpl implements ObjectStorageService, InitializingBean {

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private TextEngine textEngine;

    @Resource
    private MinioBucketEngine minioBucketEngine;

    @Resource
    private MinioEngine minioEngine;

    @Override
    public void upload(String originalName, byte[] bytes) {
        try {
            // 上传资源
            minioEngine.upload(originalName, bytes);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_UPLOAD_FAILED);
        }
    }

    @Override
    public void upload(MultipartFile file) {
        upload(FileResourceUtil.getOriginalName(file), MediaUtil.getBytes(file));
    }

    @Override
    public String getObjectUrl(String fileName, Boolean hidden) {
        try {
            return minioEngine.getObjectUrl(fileName, hidden);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_GET_OBJECT_URL_FAILED);
        }
    }

    @Override
    public byte[] load(String fileName) {
        try {
            return minioEngine.load(fileName);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_LOAD_FAILED);
        }
    }

    @Override
    public void preview(String fileName, HttpServletResponse response) {
        try {
            minioEngine.preview(fileName, response);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_PREVIEW_FAILED);
        }
    }

    @Override
    public void download(String downloadName, String fileName, HttpServletResponse response) {
        try {
            minioEngine.download(downloadName, fileName, response);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_DOWNLOAD_FAILED);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            minioEngine.remove(fileName);
        } catch (Exception e) {
            throw new GlobalServiceException(e.getMessage(), GlobalServiceStatusCode.FILE_RESOURCE_REMOVE_FAILED);
        }
    }

    @Override
    public String compressImage(Long userId, String fileName) throws Exception {
        // 加载图片
        byte[] bytes = load(fileName);
        // 检查是不是图片
        FileResourceUtil.checkImage(MediaUtil.getContentType(bytes));
        // 删除原图片
        remove(fileName);
        // 压缩图片
        bytes = MediaUtil.compressImage(bytes);
        // 上传图片
        String uniqueFileName = FileResourceUtil.getUniqueFileName(userId, MediaUtil.COMPRESS_FORMAT_SUFFIX);
        minioEngine.upload(uniqueFileName, bytes);
        return uniqueFileName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果不存在，则初始化桶
        String bucketName = minioConfig.getBucketName();
        // 设置规则：所有人都能读（否则就只能获取）
        DefaultPolicyTemplate policyTemplate = DefaultPolicyTemplate.builder()
                .bucketName(bucketName)
                .build();
        String policy = textEngine.builder()
                .append(MinioPolicyTemplateEnum.ALLOW_ALL_GET.getTemplate(), policyTemplate)
                .build();
        minioBucketEngine.tryMakeBucket(bucketName, policy);
    }
}
