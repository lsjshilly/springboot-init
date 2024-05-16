package com.lsj.usercenter.model.common;


import lombok.Data;

import java.util.List;

@Data
public class ListResult {
    private List<?> items;
    private Long total;

}
