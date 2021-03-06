package com.taotao.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T>{

    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     * 
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     * 
     * @param record
     * @return
     */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }

    /**
     * 根据条件查询数据列表
     * 
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) {
        return this.mapper.select(record);
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(record);
        return new PageInfo<T>(list);
    }

    /**
     * 新增数据，返回成功的条数
     * 
     * @param record
     * @return
     */
    public int save(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insert(record);
    }

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     * 
     * @param record
     * @return
     */
    public int saveSelective(T record) {
       return this.mapper.insertSelective(record);
    }

    /**
     * 修改数据，返回成功的条数
     * 
     * @param record
     * @return
     */
    public int updateById(T record) {
        return this.mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     * 
     * @param record
     * @return
     */
    public int updateSelectiveById(T record) {
        //record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 根据id批量更新数据
     */
    public int updateSelectiveByIds(T record, String property, List<Object> values) {
        Example example = new Example(record.getClass());
        example.createCriteria().andIn(property, values);
        record.setUpdated(new Date());
        return this.mapper.updateByExampleSelective(record, example);
    }
    

    /**
     * 根据id删除数据
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public int deleteByIds(Class<T> clazz, String property, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return this.mapper.deleteByExample(example);
    }
    
    /**
     * 根据条件做删除
     * 
     * @param record
     * @return
     */
    public int deleteByWhere(T record) {
        return this.mapper.delete(record);
    }

}
