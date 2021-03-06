package com.taotao.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.track.Track;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@Service
public class ItemParamServiceImpl extends BaseServiceImpl<ItemParam> implements ItemParamService{
    
    @Autowired
    private ItemParamMapper itemParamMapper;

    public PageInfo<ItemParam> queryPageList(Integer page,Integer rows){
        Track.service("page is {}, rows is {}", page,rows);
        PageHelper.startPage(page, rows);
        Example ex = new Example(Item.class);
        ex.setOrderByClause("id desc");
        List<ItemParam> list = this.itemParamMapper.selectByExample(ex);
        return new PageInfo<ItemParam>(list);
    }

    public int addItemParam(Long itemCatId,String paramData){
        Track.service("itemCatId is {} ,paramData is {} ", itemCatId,paramData);
        ItemParam record = new ItemParam();
        record.setId(null);
        record.setItemCatId(itemCatId);
        record.setParamData(paramData);
        return this.saveSelective(record);
    }

    public int updateItemParam(Long id, String paramData) {
        Track.service("id is {} ,paramData is {} ", id,paramData);
        ItemParam record = new ItemParam();
        record.setId(id);
        record.setParamData(paramData);
        return this.updateSelectiveById(record);
    }
}
