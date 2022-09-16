package com.leechr.meituan.dto;

import com.leechr.meituan.entity.OrderDetail;
import com.leechr.meituan.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails;
}
