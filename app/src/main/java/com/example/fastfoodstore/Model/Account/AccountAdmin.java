package com.example.fastfoodstore.Model.Account;

public class AccountAdmin {
    private String UserNameAdmin;
    private String PassWordAdmin;

    public AccountAdmin(String userNameAdmin, String passWordAdmin) {
        UserNameAdmin = userNameAdmin;
        PassWordAdmin = passWordAdmin;
    }

    public String getUserNameAdmin() {
        return UserNameAdmin;
    }

    public void setUserNameAdmin(String userNameAdmin) {
        UserNameAdmin = userNameAdmin;
    }

    public String getPassWordAdmin() {
        return PassWordAdmin;
    }

    public void setPassWordAdmin(String passWordAdmin) {
        PassWordAdmin = passWordAdmin;
    }
}
