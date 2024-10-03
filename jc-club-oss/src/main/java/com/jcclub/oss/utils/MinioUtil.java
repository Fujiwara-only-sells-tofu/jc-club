package com.jcclub.oss.utils;

import com.jcclub.oss.entity.FIleInfo;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName：MinioUtil
 * @Author: gouteng
 * @Date: 2024/10/2 20:51
 * @Description: minio文件操作工具
 */
@Component
@RequiredArgsConstructor
public class MinioUtil {

    private final MinioClient minioClient;


    /**
     * @Description: 创建bucket
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */


    public void createBucket(String bucketName) throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * @Description: 上传文件
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    public void uploadFile(InputStream inputStream, String bucket, String objectName) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName)
                .stream(inputStream, -1, Integer.MAX_VALUE).build());
    }


    /**
     * @Description: 查询所有bucket名字
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    public List<String> getAllBucket() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets.stream().map(Bucket::name).collect(Collectors.toList());
    }


    /**
     * @Description: 查询当前桶及文件
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    public List<FIleInfo> getAllFile(String bucket) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs
                        .builder()
                        .bucket(bucket)
                        .build()
        );
        List<FIleInfo> fIleInfos = new LinkedList<>();
        for (Result<Item> result : results) {
            FIleInfo fIleInfo = new FIleInfo();
            Item item = result.get();
            fIleInfo.setFileName(item.objectName());
            fIleInfo.setDirectoryFlag(item.isDir());
            fIleInfo.setEtag(item.etag());
            fIleInfos.add(fIleInfo);

        }
        return fIleInfos;
    }
    /*
     * @Description: 文件下载
     * @data:* @param null
     * @return:
     * @return: null
     * @Author: ZCY
     * @Date: 2024-10-02 21:22:47
     */

    public InputStream downLoad(String bucket, String objectName) throws Exception {
        return minioClient
                .getObject(GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(objectName)
                        .build());
    }



    /**
     * @Description: 删除bucket
     * @data:[bucket]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:25:35
     */

    public void deleteBucket(String bucket) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());

    }

    /**
     * @Description: 删除文件
     * @data:[bucket, objectName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:26:38
     */

    public void deleteObject(String bucket, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());

    }

}
