package com.nkm.mypracticespring.security.custom;

import com.nkm.mypracticespring.dto.access.CustomUserDetails;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.IShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class FakeDaoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private IShopRepository shopRepository;

    private final PasswordEncoder passwordEncoder;

    public FakeDaoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<ShopModel> shopModel = shopRepository.findFirstByEmail(email);
        if (shopModel.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Not found user : %s", email));
        }

        if (!this.passwordEncoder.matches(password, shopModel.get().getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : shopModel.get().getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(shopModel.get(), authorities);

        return new UsernamePasswordAuthenticationToken(customUserDetails, authentication, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
