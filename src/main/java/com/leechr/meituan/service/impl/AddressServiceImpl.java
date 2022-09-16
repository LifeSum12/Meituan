package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.AddressBook;
import com.leechr.meituan.mapper.AddressBookMapper;
import com.leechr.meituan.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
