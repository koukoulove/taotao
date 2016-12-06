package com.taotao.manage.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.enu.ITEM_STATUS;
import com.taotao.common.track.Track;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemMapper itemMapper;
    
    public void saveItem(Item item, String desc) {
       Track.service("item is {}, desc is {}", item,desc);
       item.setStatus(Integer.valueOf(ITEM_STATUS.NORMAL.getCode()));
       item.setId(null);
       this.saveSelective(item);
       
       ItemDesc record = new ItemDesc();
       record.setItemId(item.getId());
       record.setItemDesc(desc);
       this.itemDescService.saveSelective(record);
    }

    
    public PageInfo<Item> queryPageList(Integer page,Integer rows){
        Track.service("page is {}, rows is {}", page,rows);
        PageHelper.startPage(page, rows);
        Example ex = new Example(Item.class);
        ex.setOrderByClause("id desc");
        List<Item> list = this.itemMapper.selectByExample(ex);
        return new PageInfo<Item>(list);
    }


    public void updateItem(Item item, String desc) {
        Track.service("item is {}, desc is {}", item,desc);
        item.setStatus(null);
        super.updateSelective(item);
        
        ItemDesc record = new ItemDesc();
        record.setItemId(item.getId());
        record.setItemDesc(desc);
        this.itemDescService.updateSelective(record);
    }
}
