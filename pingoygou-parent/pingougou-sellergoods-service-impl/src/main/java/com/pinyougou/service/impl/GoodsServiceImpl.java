package com.pinyougou.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import com.pinyougou.viewEntity.GoodsView;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
@Component
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> results = goodsMapper.selectByExample(null);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(results);
        List<TbGoods> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total, list);
    }

    /**
     * 增加
     * goodsDesc是goods的扩展表
     * item是goods的sku表，是多对一的关系
     */
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbBrandMapper brandMapper;

    @Autowired
    private TbSellerMapper sellerMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void add(GoodsView goodsView) {
        TbGoods goods = goodsView.getGoods();
        goodsMapper.insert(goods);
        TbGoodsDesc goodsDesc = goodsView.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId());
        goodsDescMapper.insert(goodsDesc);
        InsetItemList(goodsView);
    }

    private void InsetItemList(GoodsView goodsView){
        TbGoods goods = goodsView.getGoods();
        String isEnableSpec = goods.getIsEnableSpec();
        List<TbItem> items = goodsView.getItemList();

        //启动规格
        if ("1".equals(isEnableSpec)) {
            for (TbItem item : items) {
                //名字是goods名字+ 规格名字
                String title=goods.getGoodsName();
                String spec = item.getSpec();
                Map<String,Object> map = JSON.parseObject(spec, Map.class);
                for (String key:map.keySet()) {
                    title+=" "+map.get(key);
                }
                item.setTitle(title);
                ItemSetField(item,goods);
                itemMapper.insert(item);
            }
        } else { //不启用规格
            TbItem item = new TbItem();
            item.setTitle(goods.getGoodsName()); //名字和商品名字一样
            item.setPrice(goods.getPrice()); //价格
            item.setNum(9999); //库存
            item.setStatus("1");//状态
            item.setIsDefault("1");//默认
            item.setSpec("{}");  //规格
            ItemSetField(item,goods);

            itemMapper.insert(item);
        }
    }

    private void ItemSetField(TbItem item,TbGoods goods ){
        //分类id
        item.setCategoryid(goods.getCategory3Id());
        //状态
        item.setStatus("0");
        //时间
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        //商品ID
        item.setGoodsId(goods.getId());
        //商家id
        item.setSellerId(goods.getSellerId());
        //分类名称
        item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());
        //品牌名字
        item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());
        //店铺名称
        item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName());
    }

    /**
     * 修改
     */
    @Override
    public void update(GoodsView goods) {
        //更新基本信息表
        goodsMapper.updateByPrimaryKey(goods.getGoods());
        //更新扩展表
        goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
        //先删除itemlist
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getGoods().getId());
        itemMapper.deleteByExample(example);
        //在添加itemlist
        InsetItemList(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public GoodsView findOne(Long id) {
        GoodsView goodsView = new GoodsView();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goodsView.setGoods(tbGoods);
        TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goodsView.setGoodsDesc(goodsDesc);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        goodsView.setItemList(tbItems);
        return goodsView;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                //criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        List<TbGoods> results = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(results);
        List<TbGoods> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total, list);
    }

}
