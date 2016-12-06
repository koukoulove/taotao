package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.track.Track;
import com.taotao.common.utils.Checks;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    
    /**
     * 根据父类ID查询类别
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ItemCat>> queryItemCat(@RequestParam(value="id",defaultValue="0")Long id){
        Track.request("id is {} ", id);
        ItemCat record= new ItemCat();
        record.setParentId(id);
        List<ItemCat> list = this.itemCatService.queryListByWhere(record);
        ResponseEntity<List<ItemCat>> result = ResponseEntity.ok(list);
        Track.response("result is {} ", result);
        return result;
    }
    /**
     * 根据ID查询
     */
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemCat> queryItemCatById(@PathVariable("id") Long id){
        Track.request("id is {} ", id);
        Checks.notNull(id);
        ItemCat itemCat = this.itemCatService.queryById(id);
        ResponseEntity<ItemCat> result = ResponseEntity.ok(itemCat);
        Track.response("result is {} ", result);
        return result;
    }
    
    
}
