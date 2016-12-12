package com.taotao.manage.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.enu.ITEM_STATUS;
import com.taotao.common.exception.Exceptions;
import com.taotao.common.exception.TaoTaoErrorCodes;
import com.taotao.common.track.Track;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemParamItemService itemParamItemService;
    @Autowired
    private ItemMapper itemMapper;
    /**
     * 新增商品信息
     */
    public void saveItem(Item item, String desc,String itemParams) {
       Track.service("item is {}, desc is {},itemParams is {} ", item,desc,itemParams);
       //商品表
       item.setStatus(Integer.valueOf(ITEM_STATUS.NORMAL.getCode()));
       item.setId(null);
       if(this.save(item) != 1){
           throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
       }
       //商品描述表
       ItemDesc record = new ItemDesc();
       record.setItemId(item.getId());
       record.setItemDesc(desc);
       if(this.itemDescService.saveSelective(record) != 1){
           throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
       }
       //商品规格参数表
       ItemParamItem itemParamItem = new ItemParamItem();
       itemParamItem.setId(null);
       itemParamItem.setParamData(itemParams);
       itemParamItem.setItemId(item.getId());
       if(this.itemParamItemService.saveSelective(itemParamItem) != 1){
           throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
       }
    }

    /**
     * 更新商品信息
     */
    public void updateItem(Item item, String desc,String itemParams) {
        Track.service("item is {}, desc is {}, itemParams is {} ", item,desc,itemParams);
        item.setStatus(null);
        //商品表
        if(this.updateSelectiveById(item) != 1){
            throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
        }
        //商品描述表
        ItemDesc record = new ItemDesc();
        record.setItemId(item.getId());
        record.setItemDesc(desc);
        if(this.itemDescService.updateSelectiveById(record) != 1){
            throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
        }
        //商品规格参数表
        if(this.itemParamItemService.updateItemParamItemByItemId(item.getId(), itemParams) != 1){
            throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
        }
    }
    /**
     * 查询商品分页信息
     */
    public PageInfo<Item> queryPageList(Integer page,Integer rows){
        Track.service("page is {}, rows is {}", page,rows);
        PageHelper.startPage(page, rows);
        Example ex = new Example(Item.class);
        ex.setOrderByClause("id desc");
        List<Item> list = this.itemMapper.selectByExample(ex);
        return new PageInfo<Item>(list);
    }
}
