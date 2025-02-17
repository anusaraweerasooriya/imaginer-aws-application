package com.imaginer.project.entity;

import java.awt.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images;

  public User(Long id, String username, String password)
  {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public User(Long id, String username, String password, List<Image> images)
  {
    this.id = id;
    this.username = username;
    this.password = password;
    this.images = images;
  }

  public User()
  {
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getUsername()
  {
    return username;
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled()
  {
    return UserDetails.super.isEnabled();
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return null;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
}
