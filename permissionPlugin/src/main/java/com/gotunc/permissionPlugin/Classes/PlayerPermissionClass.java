package com.gotunc.permissionPlugin.Classes;

public class PlayerPermissionClass {
    public boolean isLogin;
    public String group;

    public PlayerPermissionClass(boolean isLogin, String group) {
        this.isLogin = isLogin;
        this.group = group;
    }
}