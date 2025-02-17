package com.imaginer.project.repository;

import java.util.List;

import com.imaginer.project.entity.Image;
import com.imaginer.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>
{
  List<Image> findByUser(User user);
}
