package com.shaun.authorize.entity;


import java.io.Serializable;

/**
 * @Author luotao
 * @Description 用户信息
 * @Date Created on 2017/10/19.
 */
public class UserAccount implements Serializable{
    private static final long serialVersionUID  = 1L;
    private String userName;
    private String name;
    private String userId;
    private String phone;
    private String roleId;
    private String email;
    private String sex;
    private String deptId;
    private String avatar;

    public String getDeptId() {
        return deptId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
