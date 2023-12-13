package com.ruoyi.flowable.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.flowable.domain.vo.FlowQueryVo;

public interface IFlowBatchService {
    void createBatch(String[] taskIds);

    AjaxResult list(FlowQueryVo queryVo);

    void complete(String batchId);
}
