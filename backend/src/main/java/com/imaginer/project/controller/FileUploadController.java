package com.imaginer.project.controller;

import java.io.IOException;

import com.imaginer.project.entity.Image;
import com.imaginer.project.entity.User;
import com.imaginer.project.repository.ImageRepository;
import com.imaginer.project.repository.UserRepository;
import com.imaginer.project.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

  private final S3Service s3Service;
  private final UserRepository userRepository;
  private final ImageRepository imageRepository;

  public FileUploadController(S3Service s3Service, UserRepository userRepository, ImageRepository imageRepository) {
    this.s3Service = s3Service;
    this.userRepository = userRepository;
    this.imageRepository = imageRepository;
  }

  @PostMapping("/image")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                            @AuthenticationPrincipal UserDetails userDetails) {
    try {
      User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

      String imageUrl = s3Service.uploadFile(
        file.getInputStream(),
        file.getOriginalFilename(),
        file.getContentType()
      );

      Image image = new Image();
      image.setImageUrl(imageUrl);
      image.setUser(user);
      imageRepository.save(image);

      return ResponseEntity.ok(imageUrl);
    } catch (IOException e) {
      return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
    }
  }

  @GetMapping("/user-images")
  public ResponseEntity<?> getUserImages(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername())
      .orElseThrow(() -> new RuntimeException("User not found"));

    return ResponseEntity.ok(imageRepository.findByUser(user));
  }
}
