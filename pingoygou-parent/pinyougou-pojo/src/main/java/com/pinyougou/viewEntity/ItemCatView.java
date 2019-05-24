package com.pinyougou.viewEntity;

import com.pinyougou.pojo.TbItemCat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ItemCatView implements Serializable {
    private TbItemCat itemCat;

    private List<Map> templateJson;

    public ItemCatView(TbItemCat itemCat, List<Map> templateJson) {
        this.itemCat = itemCat;
        this.templateJson = templateJson;
    }

    public TbItemCat getItemCat() {
        return itemCat;
    }

    public void setItemCat(TbItemCat itemCat) {
        this.itemCat = itemCat;
    }

    public List<Map> getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(List<Map> templateJson) {
        this.templateJson = templateJson;
    }
}
