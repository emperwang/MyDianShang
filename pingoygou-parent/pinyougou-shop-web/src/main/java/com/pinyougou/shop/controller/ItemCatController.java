package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.ItemCatService;
import com.pinyougou.service.TypeTemplateService;
import com.pinyougou.viewEntity.ItemCatView;
import com.pinyougou.viewEntity.PageResult;
import com.pinyougou.viewEntity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	@Reference
	private TypeTemplateService templateService;

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll.do")
	public List<TbItemCat> findAll(){			
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage.do")
	public PageResult findPage(int page, int rows){
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add.do")
	public Result add(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.add(itemCat);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update.do")
	public Result update(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.update(itemCat);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体 格式:{"itemCat":{"id":495,"parentId":0,"name":"玩具乐器","typeId":35},"templateJson":[{"id":35,"text":"手机"}]}
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne.do")
	public ItemCatView findOne(Long id){
		TbItemCat itemCat = itemCatService.findOne(id);
		TbTypeTemplate typeTemplate = templateService.findOne(itemCat.getTypeId());
		Long id1 = typeTemplate.getId();
		String name = typeTemplate.getName();
		Map<String,Object> map = new HashMap<>();
		map.put("id",id1);
		map.put("text",name);
		ArrayList<Map> maps = new ArrayList<>();
		maps.add(map);
		ItemCatView itemCatView = new ItemCatView(itemCat, maps);
		return itemCatView;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete.do")
	public Result delete(Long [] ids){
		try {
			itemCatService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search.do")
	public PageResult search(@RequestBody TbItemCat itemCat, int page, int rows  ){
		return itemCatService.findPage(itemCat, page, rows);		
	}
	@RequestMapping("/findByParentId.do")
	public List findByParentId(Long parentId){
		List<TbItemCat> lists = itemCatService.findByParentId(parentId);
		return lists;
	}

	@RequestMapping("/findByParentIdPage.do")
	public PageResult findByParentIdPage(Long parentId,int page, int size){
		PageResult result = itemCatService.findByParentIdPage(parentId, page, size);
		return result;
	}
}
