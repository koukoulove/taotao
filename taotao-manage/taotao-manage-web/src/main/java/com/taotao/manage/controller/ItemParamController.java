package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.track.Track;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    
    @Autowired
    private ItemParamService itemParamService;
    
    /**
     * 根据商品类目ID选择对应的规格参数模板
     */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemParam> queryItemCat(@PathVariable("itemCatId") Long itemCatId){
        Track.request("itemCatId is {} ", itemCatId);
        ItemParam model = new ItemParam();
        model.setItemCatId(itemCatId);
        ItemParam itemParam = this.itemParamService.queryOne(model);
        ResponseEntity<ItemParam> result = null;
//        if(null == itemParam){
//            result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }else{
            result = ResponseEntity.ok(itemParam);
//        }
        Track.response("result is {} ", result);
        return result;
    }
}
