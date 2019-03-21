package com.app.model.user;

import com.app.api.user.request.UserRegisterRequest;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
public class User implements UserDetails {

    public final static String emailRegex = "[a-zA-z0-9.]+@[a-zA-Z0-9]+.[a-zA-Z]+";

    private final String ROLE_PREFIX = "ROLE_";
    private String role;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Size(min = 1, max = 20)
    private String username;
    @Size(min = 8)
    private String password;
    @Pattern(regexp = emailRegex)
    private String email;

    private GrantedAuthority authority;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        role = "USER";
    }

    public User() {
    }

    public User(UserRegisterRequest request) {
        new User(request.getUsername(), request.getEmail(),request.getPassword());
    }


    public static String getEmailRegex() {
        return emailRegex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(GrantedAuthority authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return list;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
