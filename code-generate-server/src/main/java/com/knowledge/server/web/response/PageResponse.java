package com.knowledge.server.web.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class PageResponse<T> implements Serializable {
    // 总记录数
    private int total;

    // 列表数据
    private List<T> list;

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageResponse(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }
}
