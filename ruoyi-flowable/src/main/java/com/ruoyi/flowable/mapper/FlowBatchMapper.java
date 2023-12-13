package com.ruoyi.flowable.mapper;



import com.ruoyi.flowable.domain.bo.FlowBatchBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowBatchMapper {
    int insertList(@Param("list") List<FlowBatchBO> list);

    List<FlowBatchBO> list();

    void updateFlagByBatchId(@Param("batchId") String batchId);

    int listByInstIdAndTaskId(@Param("taskId") String taskId,@Param("instId") String instId);
}
