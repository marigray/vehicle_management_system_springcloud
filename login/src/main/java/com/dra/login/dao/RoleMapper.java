package com.dra.login.dao;

import com.dra.pojo.login.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RoleMapper {
    Page<Role> findRole(@Param("id") String id);

    int addRole(Role role);
    int updateRole(Role role);
    int deleteRole(@Param("roleId") String roleId);
    Role searchRoleById(@Param("roleId") String roleId);
    Page<Role> searchAllRole();

}
