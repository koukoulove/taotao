package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.bean.Message;
import com.taotao.common.bean.Success;
import com.taotao.common.enu.ITEM_STATUS;
import com.taotao.common.track.Track;
import com.taotao.common.utils.Checks;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemService;
import com.taotao.manage.vo.ItemVO;

@Controller
@RequestMapping("/item")
public class ItemController {


    private String OP_DELETE = "DELETE";// 删除

    private String OP_INSTOCK = "INSTOCK";// 下架

    private String OP_RESHELF = "RESHELF";// 上架

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCatService itemCatService;
    
    

    /**
     * 新增商品
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addItem(Item item, String desc,String itemParams) {
        Track.request("item is {},desc is {},itemParams is {}", item, desc,itemParams);
        validateItemParams(item);
        this.itemService.saveItem(item, desc,itemParams);
        ResponseEntity<Void> resp = ResponseEntity.status(HttpStatus.CREATED).build();
        Track.response("resp is {}", resp);
        return resp;
    }
    /**
     * 更新商品
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateItem(Item item, String desc,String itemParams) {
        Track.request("item is {},desc is {},itemParams is {}", item, desc,itemParams);
        validateItemParams(item);
        this.itemService.updateItem(item, desc,itemParams);
        ResponseEntity<Void> resp = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        Track.response("resp is {}", resp);
        return resp;
    }

    /**
     * 查询商品分页列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EasyUIResult> queryItem(Integer page, Integer rows) {
        Track.request("page is {},rows is {} ", page, rows);
        PageInfo<Item> pageInfo = this.itemService.queryPageList(page, rows);
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        if(CollectionUtils.isNotEmpty(pageInfo.getList())){
            for (Item item : pageInfo.getList()) {
                Long cid = item.getCid();
                ItemCat itemCat = itemCatService.queryById(cid);
                ItemVO vo = new ItemVO();
                BeanUtils.copyProperties(item, vo);
                vo.setCid_(itemCat.getName());
                itemVOList.add(vo);
            }
        }
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), itemVOList);
        Track.response("result is {} ", result);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除 上架 下架 商品
     */
    @RequestMapping(value = "operation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message> operatorItem(String ids, String op) {
        Track.request("ids is {},op is {}", ids, op);
        List<Object> idsList = new ArrayList<Object>();
        String[] split = ids.split(",");
        for (String id : split) {
            idsList.add(id);
        }
        Item record = new Item();
        if (OP_DELETE.equals(op)) {
            record.setStatus(Integer.valueOf(ITEM_STATUS.DELETE.getCode()));
        } else if (OP_INSTOCK.equals(op)) {
            record.setStatus(Integer.valueOf(ITEM_STATUS.OFF_LINE.getCode()));
        } else if (OP_RESHELF.equals(op)) {
            record.setStatus(Integer.valueOf(ITEM_STATUS.ON_LINE.getCode()));
        }
        this.itemService.updateSelectiveByIds(record, "id", idsList);
        Message message = new Success();
        ResponseEntity<Message> resp = ResponseEntity.ok(message);
        
        Track.response("resp is {}", resp);
        return resp;
    }

    /**
     * 商品信息验证
     */
    private void validateItemParams(Item item) {
        Checks.notNull(item);
        Checks.notNull(item.getTitle());
        Checks.notNull(item.getCid());
        Checks.notNull(item.getPrice());
    }
}
