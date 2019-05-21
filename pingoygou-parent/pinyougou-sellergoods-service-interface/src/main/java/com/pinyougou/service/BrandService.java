package com.pinyougou.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.viewEntity.PageResult;

import java.util.List;

public interface BrandService {
    /**
     * 查找所有
     * @return
     */
    List<TbBrand> findAll();

    /**
     *  分页查找
     * @param page  第几页
     * @param pagesize 一页显示的数量
     * @return
     */
    PageResult findByPage(Integer page,Integer pagesize);

    /**
     *   添加
     * @param brand
     * @return
     */
    int add(TbBrand brand);

    /**
     *  修改
     * @param brand
     * @return
     */
    int updateBrand(TbBrand brand);

    /**
     *  通过id查找
     * @param id
     * @return
     */
    TbBrand findById(Integer id);

    /**
     *  批量删除
     * @param ids
     * @return
     */
    int deletes(Long[] ids);

    PageResult search(TbBrand brand,Integer page,Integer pagesize);
}
