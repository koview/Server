package com.koview.koview_server.review.repository;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    List<Review> findAllByMember(Member member);
}