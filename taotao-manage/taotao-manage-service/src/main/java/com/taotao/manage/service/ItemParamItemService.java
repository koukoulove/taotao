package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemParamItem;

public interface ItemParamItemService extends BaseService<ItemParamItem>{
    
    /**
     * 根据商品Id更新规格参数
     */
    public int updateItemParamItemByItemId(Long itemId,String itemParams);
}
