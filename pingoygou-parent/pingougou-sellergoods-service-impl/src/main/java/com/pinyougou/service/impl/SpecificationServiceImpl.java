package com.pinyougou.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojoGroup.Specification;
import com.pinyougou.service.SpecificationService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Component
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<TbSpecification> results = specificationMapper.selectByExample(null);
		PageInfo<TbSpecification> pageInfo = new PageInfo<>(results);
		List<TbSpecification> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	/**
	 * 增加
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
	public void add(Specification specification) {
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.insert(tbSpecification);
		Long id = tbSpecification.getId();
		List<TbSpecificationOption> tbSpecificationOption = specification.getSpecificationList();
		for (TbSpecificationOption option : tbSpecificationOption) {
			option.setSpecId(id);
			specificationOptionMapper.insert(option);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSpecification specification){
		specificationMapper.updateByPrimaryKey(specification);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification specification = new Specification();
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);

		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> tbSpecificationOptionsList = specificationOptionMapper.selectByExample(example);
		specification.setSpecificationList(tbSpecificationOptionsList);
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		List<TbSpecification> results = specificationMapper.selectByExample(example);
		PageInfo<TbSpecification> pageInfo = new PageInfo<>(results);
		List<TbSpecification> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}
	
}
