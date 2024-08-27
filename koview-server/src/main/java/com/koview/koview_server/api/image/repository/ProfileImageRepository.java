package com.koview.koview_server.api.image.repository;

import com.koview.koview_server.api.image.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}