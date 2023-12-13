package com.ruoyi.flowable.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.flowable.domain.dto.FlowSaveXmlVo;
import com.ruoyi.flowable.domain.dto.FlowTaskDto;
import com.ruoyi.flowable.domain.vo.FlowQueryVo;
import com.ruoyi.flowable.service.IFlowBatchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flowable/batch")
public class FlowBatchController extends BaseController {

    @Autowired
    private IFlowBatchService flowBatchService;

    @ApiOperation(value = "创建批次流程")
    @PostMapping("/create/{taskIds}")
    public AjaxResult save(@PathVariable String[] taskIds) {
        flowBatchService.createBatch(taskIds);
        return AjaxResult.success("创建成功");
    }
    @ApiOperation(value = "获取批次列表", response = FlowTaskDto.class)
    @GetMapping(value = "/list")
    public AjaxResult list(FlowQueryVo queryVo) {
        return flowBatchService.list(queryVo);
    }
    @ApiOperation(value = "创建批次流程")
    @PostMapping("/complete/{batchId}")
    public AjaxResult complete(@PathVariable String batchId) {
        flowBatchService.complete(batchId);
        return AjaxResult.success("审批成功");
    }
}
