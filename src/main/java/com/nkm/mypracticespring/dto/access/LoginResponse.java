package com.nkm.mypracticespring.dto.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private ShopInfo shop;

    private String accessToken;

    private String refreshToken;

}

