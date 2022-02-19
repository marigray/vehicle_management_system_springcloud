package com.dra.login.dao;

import com.dra.pojo.login.Menu;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MenuMapper {
    Page<Menu> findMenu(String powerId);

    int addMenu(Menu menu);
    int updateMenu(Menu menu);
    int deleteMenu(@Param("menuId") String menuId);
    Menu searchMenuById(@Param("menuId") String menuId);
    Page<Menu> searchAllMenuByUsername();
}
