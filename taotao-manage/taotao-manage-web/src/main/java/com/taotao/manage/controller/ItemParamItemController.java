package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.track.Track;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;
    /**
     * 根据商品ID查询规格参数值
     */
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemParamItem> queryItemParam(@PathVariable("itemId") Long itemId){
        Track.request("itemId is {} ", itemId);
        ItemParamItem model = new ItemParamItem();
        model.setItemId(itemId);
        ItemParamItem itemParamItem = this.itemParamItemService.queryOne(model);
        ResponseEntity<ItemParamItem> result = ResponseEntity.ok(itemParamItem);
        Track.response("result is {} ", result);
        return result;
    }
    
}
