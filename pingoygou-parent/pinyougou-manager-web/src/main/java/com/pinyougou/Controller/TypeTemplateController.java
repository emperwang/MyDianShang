package com.pinyougou.Controller;
import java.util.List;
import java.util.Map;

import com.pinyougou.service.TypeTemplateService;
import com.pinyougou.viewEntity.PageResult;
import com.pinyougou.viewEntity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
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
	 * @param map
	 * @return
	 */
	@RequestMapping("/add.do")//TbTypeTemplate typeTemplate customAttributeItems name brandIds specIds
	public Result add(@RequestBody Map map){
		try {
			TbTypeTemplate tbTypeTemplate = getParamrterFromRequest(map);
			typeTemplateService.add(tbTypeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	private TbTypeTemplate getParamrterFromRequest(Map map){
		TbTypeTemplate tbTypeTemplate = new TbTypeTemplate();
		//获取参数
		String customAttributeItems = map.get("customAttributeItems").toString();
		String name = (String) map.get("name").toString();
		String brandIds = (String) map.get("brandIds").toString();
		String specIds = (String) map.get("specIds").toString();
		//封装参数到对象中
		tbTypeTemplate.setCustomAttributeItems(customAttributeItems);
		tbTypeTemplate.setName(name);
		tbTypeTemplate.setBrandIds(brandIds);
		tbTypeTemplate.setSpecIds(specIds);
		return tbTypeTemplate;
	}

	/**
	 * 修改
	 * @param map
	 * @return
	 */
	@RequestMapping("/update.do")
	public Result update(@RequestBody Map map){
		try {
			TbTypeTemplate tbTypeTemplate = getParamrterFromRequest(map);
			Long id = Long.parseLong(map.get("id").toString());
			tbTypeTemplate.setId(id);
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
	
}
