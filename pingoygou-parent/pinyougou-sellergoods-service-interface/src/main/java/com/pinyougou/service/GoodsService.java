package com.pinyougou.service;
import java.util.List;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.viewEntity.GoodsView;
import com.pinyougou.viewEntity.PageResult;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(GoodsView goods);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsView goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsView findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize);

	/**
	 * 更新商品的审核状态
	 * @param ids
	 * @param status
	 */
	void updateGoodsStatus(Long[] ids,String status);

}
