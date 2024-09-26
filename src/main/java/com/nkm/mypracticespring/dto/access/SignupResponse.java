package com.nkm.mypracticespring.dto.access;

import lombok.Data;

@Data
public class SignupResponse {

    private ShopInfo shop;

    private String accessToken;

    private String refreshToken;

}
