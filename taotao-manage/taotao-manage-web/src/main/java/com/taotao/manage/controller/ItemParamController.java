package com.taotao.manage.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.exception.Exceptions;
import com.taotao.common.exception.TaoTaoErrorCodes;
import com.taotao.common.track.Track;
import com.taotao.common.utils.Checks;
import com.taotao.common.utils.TaoTaoUtils;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemParamService;
import com.taotao.manage.vo.ItemParamVO;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    
    @Autowired
    private ItemParamService itemParamService;
    
    @Autowired
    private ItemCatService itemCatService;
    
    /**
     * 新增规格参数
     */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addItemParam(@PathVariable("itemCatId") Long itemCatId,@RequestParam(value="paramData")String paramData){
        Track.request("itemCatID is {} , paramData is {} ", itemCatId,paramData);
        Checks.notNull(itemCatId);
        Checks.notNull(paramData);
        if(this.itemParamService.addItemParam(itemCatId, paramData)!=1){
            throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
        }
        ResponseEntity<Void> resp = ResponseEntity.ok().build();
        Track.response("resp ", resp);
        return resp;
    }
    /**
     * 更新规格参数
     * 
     */
    @RequestMapping(value="{id}",method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateItemParam(@PathVariable("id") Long id,@RequestParam(value="paramData")String paramData){
        Track.request("id is {} , paramData is {} ", id,paramData);
        Checks.notNull(id);
        Checks.notNull(paramData);
        if(this.itemParamService.updateItemParam(id, paramData)!=1){
            throw Exceptions.fail(TaoTaoErrorCodes.OP_FAILURE);
        }
        ResponseEntity<Void> resp = ResponseEntity.ok().build();
        Track.response("resp ", resp);
        return resp;
    }
    
    /**
     * 根据IDS删除规格参数 物理删除
     */
    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> queryItemParam(String ids){
        Track.request("ids is {} ", ids);
        Checks.notNull(ids);
        this.itemParamService.deleteByIds(ItemParam.class, "id", TaoTaoUtils.idsToList(ids));
        ResponseEntity<Void> result = ResponseEntity.ok().build();
        Track.response("result is {} ", result);
        return result;
    }
    
    /**
     * 根据商品类目ID选择对应的规格参数模板
     */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemParam> queryItemParam(@PathVariable("itemCatId") Long itemCatId){
        Track.request("itemCatId is {} ", itemCatId);
        ItemParam model = new ItemParam();
        model.setItemCatId(itemCatId);
        ItemParam itemParam = this.itemParamService.queryOne(model);
        ResponseEntity<ItemParam> result = ResponseEntity.ok(itemParam);
        Track.response("result is {} ", result);
        return result;
    }
    
    /**
     * 查询规格参数模板分页列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EasyUIResult> queryItemPageList(Integer page, Integer rows) {
        Track.request("page is {},rows is {} ", page, rows);
        PageInfo<ItemParam> pageInfo = this.itemParamService.queryPageList(page, rows);
        List<ItemParamVO> voList = new ArrayList<ItemParamVO>();
        if(CollectionUtils.isNotEmpty(pageInfo.getList())){
            for (ItemParam itemParam : pageInfo.getList()) {
                ItemParamVO vo = new ItemParamVO();
                BeanUtils.copyProperties(itemParam, vo);
                ItemCat itemCat = itemCatService.queryById(itemParam.getItemCatId());
                vo.setItemCatName(itemCat.getName());
                voList.add(vo);
            }
        }
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), voList);
        Track.response("result is {} ", result);
        return ResponseEntity.ok(result);
    }
}
