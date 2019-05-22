package com.pinyougou.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import com.pinyougou.viewEntity.PageResult;
import com.pinyougou.viewEntity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService service;

    @RequestMapping("/findAll.do")
    @ResponseBody
    public List<TbBrand> findAll(){
        List<TbBrand> all = service.findAll();
        return all;
    }

    @RequestMapping("/findPage.do")
    @ResponseBody
    public PageResult findPage(@RequestParam(name = "page",required = true) Integer page,@RequestParam(name = "size",required = true) Integer size){
        PageResult byPage = service.findByPage(page, size);
        return byPage;
    }

    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody TbBrand brand){
        int add = service.add(brand);
        if (add >0){
            return new Result(true,"brand添加成功");
        }else{
            return new Result(false,"brand添加失败");
        }
    }

    @PostMapping("/update.do")
    @ResponseBody
    public Result update(@RequestBody TbBrand brand){
        if (brand.getId() == null){
            return new Result(false,"brand修改失败");
        }
        int i = service.updateBrand(brand);
        if (i >0){
            return new Result(true,"brand修改成功");
        }else{
            return new Result(false,"brand修改失败");
        }
    }

    @GetMapping("/findOne.do")
    @ResponseBody
    public TbBrand findById(Integer id){
        TbBrand byId = service.findById(id);
        return byId;
    }
    @GetMapping("/deletes.do")
    @ResponseBody
    public Result deletes(Long[] ids){
        int i = service.deletes(ids);
        if (i >0){
            return new Result(true,"brand删除成功");
        }else{
            return new Result(false,"brand删除失败");
        }
    }

    @PostMapping("/search.do")
    @ResponseBody
    public PageResult search(@RequestBody TbBrand brand,@RequestParam(name = "page",required = true) Integer page,
                                                    @RequestParam(name = "size",required = true) Integer size){
        PageResult search = service.search(brand, page, size);
        return search;
    }

    @GetMapping("/selectOptionList.do")
    @ResponseBody
    public List<Map> getOptionList(){
        List<Map> maps = service.selectOptionList();
        return maps;
    }
}
