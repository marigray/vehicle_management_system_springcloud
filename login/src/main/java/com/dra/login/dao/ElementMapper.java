package com.dra.login.dao;

import com.dra.pojo.login.Element;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ElementMapper {
    Page<Element> findElement(@Param("powerId") String powerId);

    int addElement(Element element);
    int updateElement(Element element);
    int deleteElement(@Param("elementId") String elementId);
    Element searchElementById(@Param("elementId") String elementId);
    Page<Element> searchAllElementByUsername();
}
