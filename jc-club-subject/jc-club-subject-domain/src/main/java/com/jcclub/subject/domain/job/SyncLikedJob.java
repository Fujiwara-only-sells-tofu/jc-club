package com.jcclub.subject.domain.job;

import com.jcclub.subject.domain.service.SubjectLikedDomainService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 同步点赞数据
 * @data:* @param null
 * @return:
 * @return: null
 * @Author: ZCY
 * @Date: 2024-10-12 13:34:08
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncLikedJob {


    private final SubjectLikedDomainService subjectLikedDomainService;

    /**
     * @Description: 同步点赞数据任务
     * @data:[]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-12 13:36:50
     */
    
    @XxlJob("syncLikedJobHandler")
    public void syncLikedJobHandler() throws Exception {
        XxlJobHelper.log("syncLikedJobHandler.start");
        try{
            subjectLikedDomainService.syncLikedData();
        }catch (Exception e){
            XxlJobHelper.log("syncLikedJobHandler.error",e.getMessage());
        }

    }





}
