package com.pinyougou.service.impl;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.service.TypeTemplateService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.pojo.TbTypeTemplateExample.Criteria;
import org.springframework.stereotype.Component;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Component
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<TbTypeTemplate> results = typeTemplateMapper.selectByExample(null);
		PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(results);
		List<TbTypeTemplate> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
		List<TbTypeTemplate> results = typeTemplateMapper.selectByExample(example);
		PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(results);
		List<TbTypeTemplate> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	@Override
	public List<Map> selectItemCatOptionList() {
        List<Map> maps = typeTemplateMapper.selectItemCatOptionList();
        return maps;
	}
    @Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	@Override
	public List<Map> findSpecList(Long id) {
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        List<Map> specids = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
        for (Map map : specids) {
            Integer id1 = (Integer)map.get("id");
            long ids = Long.parseLong(id1.toString());
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(ids);
            List<TbSpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
            map.put("options",specificationOptions);
        }
        return specids;
	}

}
