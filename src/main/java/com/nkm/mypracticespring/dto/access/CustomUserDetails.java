package com.nkm.mypracticespring.dto.access;

import com.nkm.mypracticespring.models.ShopModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final ShopModel shopModel;

    private final Set<GrantedAuthority> customAuthorities;

    public CustomUserDetails(ShopModel shopModel, Set<GrantedAuthority> customAuthorities) {
        this.shopModel = shopModel;
        this.customAuthorities = customAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.customAuthorities;
    }

    @Override
    public String getPassword() {
        return this.shopModel.getPassword();
    }

    @Override
    public String getUsername() {
        return this.shopModel.getEmail();
    }
}
