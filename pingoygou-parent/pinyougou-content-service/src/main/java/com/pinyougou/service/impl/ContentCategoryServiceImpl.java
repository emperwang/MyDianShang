package com.pinyougou.service.impl;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbContentCategoryMapper;
import com.pinyougou.pojo.TbContentCategory;
import com.pinyougou.pojo.TbContentCategoryExample;
import com.pinyougou.pojo.TbContentCategoryExample.Criteria;
import com.pinyougou.content.service.ContentCategoryService;
import org.springframework.stereotype.Component;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Component
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContentCategory> findAll() {
		return contentCategoryMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbContentCategory> results = contentCategoryMapper.selectByExample(null);
		PageInfo<TbContentCategory> pageInfo = new PageInfo<>(results);
		List<TbContentCategory> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContentCategory contentCategory) {
		contentCategoryMapper.insert(contentCategory);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbContentCategory contentCategory){
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContentCategory findOne(Long id){
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			contentCategoryMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		
		if(contentCategory!=null){			
						if(contentCategory.getName()!=null && contentCategory.getName().length()>0){
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
	
		}
		List<TbContentCategory> results = contentCategoryMapper.selectByExample(example);
		PageInfo<TbContentCategory> pageInfo = new PageInfo<>(results);
		List<TbContentCategory> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	@Override
	public List<Map> selectContentCategoryOptionList() {
		List<Map> maps = contentCategoryMapper.selectContentCategoryList();
		return maps;
	}

}
