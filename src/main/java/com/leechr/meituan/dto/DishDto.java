package com.leechr.meituan.dto;

import com.leechr.meituan.entity.Dish;
import com.leechr.meituan.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
