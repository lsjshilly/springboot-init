package com.lsj.usercenter.model.common;

import lombok.Data;

@Data
public class PageRequest {
    /**
     * 当前页
     */
    private final long pageNum = 1;

    /**
     * 也大小
     */
    private final long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;


}
