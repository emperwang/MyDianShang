package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import com.pinyougou.viewEntity.PageResult;
import com.pinyougou.viewEntity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll.do")
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage.do")
	public PageResult findPage(int page, int rows){
		return typeTemplateService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * 可以使用参数接收,名字就是独享的成员变量名字
	 * 这里使用map是因为arrayList转换到对象的String类型成员变量失败
	 * @param string
	 * @return
	 */
	@RequestMapping("/add.do")//TbTypeTemplate typeTemplate customAttributeItems name brandIds specIds
	public Result add(@RequestBody String string){
		try {
			TbTypeTemplate tbTypeTemplate = JSON.parseObject(string, TbTypeTemplate.class);
			typeTemplateService.add(tbTypeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 修改
	 * @param string
	 * @return
	 */
	@RequestMapping("/update.do")
	public Result update(@RequestBody String string){
		try {
			TbTypeTemplate tbTypeTemplate = JSON.parseObject(string, TbTypeTemplate.class);
			typeTemplateService.update(tbTypeTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne.do")
	public TbTypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete.do")
	public Result delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
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
	public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int page, int rows  ){
		return typeTemplateService.findPage(typeTemplate, page, rows);		
	}

	@RequestMapping("/selectItemCatOptionList.do")
	public List<Map> selectItemCatOptionList(){
		List<Map> maps = typeTemplateService.selectItemCatOptionList();
		return maps;
	}

	@RequestMapping("/findSpecList.do")
	public List<Map> findSpecList(Long id){
		List<Map> specList = typeTemplateService.findSpecList(id);
		return specList;
	}
}
