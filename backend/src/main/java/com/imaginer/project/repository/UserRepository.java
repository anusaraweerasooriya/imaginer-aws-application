package com.imaginer.project.repository;

import java.util.Optional;

import com.imaginer.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
  Optional<User> findByUsername(String username);
}
