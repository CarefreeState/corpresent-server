package com.macaron.corpresent.domain.user.security;

import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 11:38
 */
@RequiredArgsConstructor
public class UserResourceDetails implements UserDetails {

    //用户
    private final User user;

    //拥有资源列表
    private final List<Resource> resourceList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户所拥有的资源
        return resourceList.stream()
                .map(resource ->new SimpleGrantedAuthority(resource.getId()+":"+resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.FALSE.equals(user.getIsBlocked());
    }


}
