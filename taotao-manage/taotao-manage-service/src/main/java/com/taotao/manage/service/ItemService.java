package com.taotao.manage.service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item>{

    /**
     * 新增商品信息
     */
    public void saveItem(Item item, String desc,String itemParams) ;

    /**
     * 更新商品信息
     */
    public void updateItem(Item item, String desc,String itemParams);
    /**
     * 查询商品分页信息
     */
    public PageInfo<Item> queryPageList(Integer page,Integer rows);
}
