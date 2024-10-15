package com.jcclub.practice.server.mapper;


import com.jcclub.practice.server.entity.po.PracticeSetDetailPO;

import java.util.List;

public interface PracticeSetDetailMapper {

    /**
     * 新增套题
     */
    int add(PracticeSetDetailPO po);

    List<PracticeSetDetailPO> selectBySetId(Long setId);


}