package com.nkm.mypracticespring.dto.access;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;

    private String refreshToken;

}
