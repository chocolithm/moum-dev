package moum.project.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * packageName    : moum.project.config
 * fileName       : CustomUserDetails
 * author         : narilee
 * date           : 24. 11. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 13.        narilee       최초 생성
 */
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
  private String email;
  private String password;
  private String nickname;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;
  private String nameAttributeKey;

  // 일반 로그인용 생성자
  public CustomUserDetails(String email, String password, String nickname,
      Collection<? extends GrantedAuthority> authorities) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.authorities = authorities;
  }

  // OAuth2 로그인용 생성자
  public CustomUserDetails(String email, String password, String nickname,
      Collection<? extends GrantedAuthority> authorities,
      Map<String, Object> attributes,
      String nameAttributeKey) {
    this(email, password, nickname, authorities);
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
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
    return email;
  }

  @Override
  public String getName() {
    return nickname;
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
