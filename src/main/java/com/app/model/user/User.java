package com.app.model.user;

import com.app.api.user.request.UserRegisterRequest;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    private boolean isAccountLocked;
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
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = "{noop}" + request.getPassword();
        role = "USER";
    }


    public static String getEmailRegex() {
        return emailRegex;
    }

    public static User getUnauthorizedUser() {
        User user = new User();
        user.isAccountLocked = true;
        return user;
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
        this.password = "{noop}" + password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return list;
    }

    public String getROLE_PREFIX() {
        return ROLE_PREFIX;
    }

    public boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
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
                "ROLE_PREFIX='" + ROLE_PREFIX + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isAccountLocked=" + isAccountLocked +
                ", authority=" + authority +
                '}';
    }
}
