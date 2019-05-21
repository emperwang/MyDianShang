package com.pinyougou.viewEntity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {

    private Long total;
    private List results;

    public PageResult(Long sum, List results) {
        this.total = sum;
        this.results = results;
    }

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getResults() {
        return results;
    }

    public void setResults(List results) {
        this.results = results;
    }
}
