package com.pinyougou.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.service.ItemCatService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.pojo.TbItemCatExample.Criteria;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Component
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<TbItemCat> results = itemCatMapper.selectByExample(null);
		PageInfo<TbItemCat> pageInfo = new PageInfo<>(results);
		List<TbItemCat> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * 进行判断,如果要删除的条目下面有子类那么就不能删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			if (isParent(id)) {
				itemCatMapper.deleteByPrimaryKey(id);
			}else{
				throw new RuntimeException(id +" is parent ,can not be delete");
			}
		}		
	}

	/**
	 *  判断此id对应的记录是否有子目录,没有则返回true
	 * @param id
	 * @return
	 */
	private boolean isParent(Long id){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> lists = itemCatMapper.selectByExample(example);
		if (lists!=null && lists.size()>0){
			return false;   //有子节点,不能删除
		}
		return true;
	}
	
		@Override
	public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
			List<TbItemCat> results = itemCatMapper.selectByExample(example);
			PageInfo<TbItemCat> pageInfo = new PageInfo<>(results);
			List<TbItemCat> list = pageInfo.getList();
			long total = pageInfo.getTotal();
			return new PageResult(total,list);
	}

	@Override
	public PageResult findByParentIdPage(Long parentId,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> lists = itemCatMapper.selectByExample(example);
		PageInfo<TbItemCat> pageInfo = new PageInfo<>(lists);
		List<TbItemCat> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 并且缓存对应的typeid，name为key
	 * @param parentId 父节点的id
	 * @return
	 */
	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> lists = itemCatMapper.selectByExample(example);
		// 找出所有，并进行缓存
		List<TbItemCat> all = findAll();
		for (TbItemCat itemCat : all) {
			String name = itemCat.getName();
			Long typeId = itemCat.getTypeId();
			redisTemplate.boundHashOps("itemCat").put(name,typeId);
		}
		System.out.println("itemCat's typeid 缓存成功");
		return lists;
	}

}
