package com.leechr.meituan.dto;

import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.entity.SetmealDish;
import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
