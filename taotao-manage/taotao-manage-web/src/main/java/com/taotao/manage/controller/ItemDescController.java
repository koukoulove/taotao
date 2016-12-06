package com.taotao.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.track.Track;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;
    
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemDesc> queryItemCat(@PathVariable("itemId") Long itemId){
        Track.request("itemId is {} ", itemId);
        ItemDesc itemDesc = this.itemDescService.queryById(itemId);
        ResponseEntity<ItemDesc> result = ResponseEntity.ok(itemDesc);
        Track.response("result is {} ", result);
        return result;
    }
}
