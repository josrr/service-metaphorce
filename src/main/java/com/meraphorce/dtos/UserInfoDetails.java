package com.meraphorce.dtos;

import com.meraphorce.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Data Transfer Object to represent user info details.
 *
 */
@Slf4j
public class UserInfoDetails implements UserDetails
{
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    /**
     * Creates a new UserInfoDetails from a User instance.
     *
     * @param userInfo a User instance
     */
    public UserInfoDetails(User userInfo) {
        name = userInfo.getName();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRoles().split(","))
            .map((role) -> {
                    return new SimpleGrantedAuthority(String.format("ROLE_%s",
                                                                    role.toUpperCase()));
                })
            .collect(Collectors.toList());
        log.debug("Granted authorities: {}", authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
