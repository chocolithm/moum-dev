package moum.project.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    private int no;
    private String nickname;
    private String email;
    private String password;
    private boolean admin;

    public User(int no) {
        this.no = no;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return admin ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                     : Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
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
