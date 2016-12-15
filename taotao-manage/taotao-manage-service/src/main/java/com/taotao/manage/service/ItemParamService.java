package com.taotao.manage.service;


import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.ItemParam;

public interface ItemParamService extends BaseService<ItemParam>{
    

    public PageInfo<ItemParam> queryPageList(Integer page,Integer rows);

    public int addItemParam(Long itemCatId,String paramData);

    public int updateItemParam(Long id, String paramData) ;
}
