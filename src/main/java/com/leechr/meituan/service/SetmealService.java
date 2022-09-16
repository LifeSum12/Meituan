package com.leechr.meituan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leechr.meituan.dto.SetmealDto;
import com.leechr.meituan.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同事说保存套餐和菜品的关联关系(setmealDish)
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     *通过setmeal的id，查询返回SetmealDto类型
     * @param id
     * @return
     */
    public SetmealDto getWithDish(Long id);

    /**
     * 删除setmeal套餐和对应setmealDish
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
