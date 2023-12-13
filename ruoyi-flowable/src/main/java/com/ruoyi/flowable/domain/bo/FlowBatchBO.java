package com.ruoyi.flowable.domain.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class FlowBatchBO {
    private int id;
    private String batchId;
    private String batchName;
    private String flowInstanceId;
    private Date createTime;
    private Date updateTime;
    private boolean delete;
    private String nodeDec;
    private String taskId;
}
