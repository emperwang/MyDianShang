package com.pinyougou.pojoGroup;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {

    private TbSpecification specification;

    private List<TbSpecificationOption> specificationList;

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<TbSpecificationOption> specificationList) {
        this.specificationList = specificationList;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "specification=" + specification +
                ", specificationList=" + specificationList +
                '}';
    }
}
