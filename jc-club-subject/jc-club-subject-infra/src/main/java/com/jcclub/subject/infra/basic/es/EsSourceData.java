package com.jcclub.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 相当于一个dto，用于es的CRUD时候的转换
 * @data:* @param null
 * @return:
 * @return: null
 * @Author: ZCY
 * @Date: 2024-10-11 10:34:16
 */

@Data
public class EsSourceData implements Serializable {

    private String docId;

    private Map<String, Object> data;

}
