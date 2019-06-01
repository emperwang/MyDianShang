package com.pinyougou.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import com.pinyougou.pojo.TbContentExample.Criteria;
import com.pinyougou.content.service.ContentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Component
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbContent> results = contentMapper.selectByExample(null);
		PageInfo<TbContent> pageInfo = new PageInfo<>(results);
		List<TbContent> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total, list);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContent content) {
		contentMapper.insert(content);
		//新添加了一个,那么就把缓存中存储的数据清除,然后下次使用时从数据库查询
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbContent content){
		Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
		redisTemplate.boundHashOps("content").delete(categoryId);
		contentMapper.updateByPrimaryKey(content);
		//如果修改时修改的时categoryId的值 那么新的categoryId对应的缓存也要清除
		Long categoryId1 = content.getCategoryId();
		if (categoryId != categoryId1){
			redisTemplate.boundHashOps("content").delete(categoryId1);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbContent tbContent = contentMapper.selectByPrimaryKey(id);
			//清除缓存中的数据
			redisTemplate.delete(tbContent.getCategoryId());
			contentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		List<TbContent> results = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(results);
		List<TbContent> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		return new PageResult(total,list);
	}

	@Override
	public List<TbContent> findByCategoryId(Long categoryId) {
		//先查询缓存
		List<TbContent> lists = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
		if (lists == null) { //如果缓存不存在,那么就从数据库查找
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);   //通过categoryId进行查找
			example.setOrderByClause("sort_order");  //设置排序字段
			lists = contentMapper.selectByExample(example);
			redisTemplate.boundHashOps("content").put(categoryId,lists);
		}
		return lists;
	}

}
