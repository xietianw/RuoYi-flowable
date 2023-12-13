package com.ruoyi.flowable.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.flowable.domain.bo.FlowBatchBO;
import com.ruoyi.flowable.domain.dto.FlowTaskDto;
import com.ruoyi.flowable.domain.vo.FlowQueryVo;
import com.ruoyi.flowable.domain.vo.FlowTaskVo;
import com.ruoyi.flowable.mapper.FlowBatchMapper;
import com.ruoyi.flowable.service.IFlowBatchService;
import com.ruoyi.flowable.service.IFlowTaskService;
import lombok.val;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlowBatchServiceImpl implements IFlowBatchService {

    @Resource
    protected TaskService taskService;

    @Resource
    protected FlowBatchMapper flowBatchMapper;

    @Autowired
    private IFlowTaskService flowTaskService;

    @Override
    public void createBatch(String[] taskIds) {
        List<FlowBatchBO> list = new ArrayList<>();
        String uuid = UUID.fastUUID().toString();
        for (String taskId : taskIds) {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            FlowBatchBO batchBO = new FlowBatchBO();
            batchBO.setBatchId(uuid);
            batchBO.setFlowInstanceId(task.getProcessInstanceId());
            batchBO.setBatchName("批次测试" + task.getName());
            batchBO.setCreateTime(new Date());
            batchBO.setDelete(false);
            batchBO.setTaskId(taskId);
            list.add(batchBO);
        }
        flowBatchMapper.insertList(list);
    }

    @Override
    public AjaxResult list(FlowQueryVo queryVo) {
        List<FlowBatchBO> backList = new ArrayList<>();
        List<FlowBatchBO> result = flowBatchMapper.list();
        Map<String,List<FlowBatchBO>> map = result.stream().collect(
                Collectors.groupingBy(FlowBatchBO::getBatchId)
        );
        for (String s : map.keySet()) {
            FlowBatchBO batchBO = map.get(s).get(0);
            batchBO.setNodeDec(batchBO.getBatchName().substring(batchBO.getBatchName().indexOf("批次测试")+4));
            backList.add(batchBO);
        }
        Page<FlowBatchBO> page = new Page<>();
        page.setTotal(backList.size());
        page.setRecords(backList);
        return AjaxResult.success(page);
    }

    @Override
    public void complete(String batchId) {
        List<FlowBatchBO> result = flowBatchMapper.list();
        List<FlowBatchBO> collect = result.stream().filter(re -> re.getBatchId().equals(batchId)).collect(Collectors.toList());
        for (FlowBatchBO batchBO : collect) {
            String taskId = batchBO.getTaskId();
            String flowInstanceId = batchBO.getFlowInstanceId();
            FlowTaskVo flowTaskVo = new FlowTaskVo();
            flowTaskVo.setTaskId(taskId);
            flowTaskVo.setInstanceId(flowInstanceId);
            flowTaskVo.setComment("同意");
            flowTaskService.complete(flowTaskVo);
        }
        flowBatchMapper.updateFlagByBatchId(batchId);

    }
}
