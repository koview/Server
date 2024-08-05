package com.koview.koview_server.mypage.repository;

import com.koview.koview_server.mypage.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}