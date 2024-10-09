package com.jcclub.oss.entity;

import lombok.Data;

/**
 * @ClassName：FIleInfo
 * @Author: gouteng
 * @Date: 2024/10/2 21:14
 * @Description: 文件类
 */

@Data
public class FileInfo {

    /**
     * 文件名
     */

    private String fileName;

    /**
     * 是否是目录
     */
    private Boolean directoryFlag;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 最后修改时间
     */
    private Long lastModifiedTime;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件的etag
     */
    private String etag;


}
