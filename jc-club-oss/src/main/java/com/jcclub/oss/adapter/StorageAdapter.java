package com.jcclub.oss.adapter;

import com.jcclub.oss.entity.FIleInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @ClassName：StorageService
 * @Author: gouteng
 * @Date: 2024/10/2 21:50
 * @Description: 文件存储适配器
 */

public interface StorageAdapter {




    /**
     * @Description: 创建bucket
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */


    void createBucket(String bucketName);

    /**
     * @Description: 上传文件
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    void uploadFile(MultipartFile uploadFile, String bucket, String objectName);


    /**
     * @Description: 查询所有bucket名字
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    List<String> getAllBucket();


    /**
     * @Description: 查询当前桶及文件
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    List<FIleInfo> getAllFile(String bucket);
    /*
     * @Description: 文件下载
     * @data:* @param null
     * @return:
     * @return: null
     * @Author: ZCY
     * @Date: 2024-10-02 21:22:47
     */

    InputStream downLoad(String bucket, String objectName);



    /**
     * @Description: 删除bucket
     * @data:[bucket]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:25:35
     */

    void deleteBucket(String bucket);

    /**
     * @Description: 删除文件
     * @data:[bucket, objectName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:26:38
     */

    void deleteObject(String bucket, String objectName) ;
}
