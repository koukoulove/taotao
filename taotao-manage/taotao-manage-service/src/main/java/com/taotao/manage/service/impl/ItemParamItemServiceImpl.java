package com.taotao.manage.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl extends BaseServiceImpl<ItemParamItem>  implements ItemParamItemService{
    
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    
    /**
     * 根据商品Id更新规格参数
     */
    public int updateItemParamItemByItemId(Long itemId,String itemParams){
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return this.itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
    }
}
