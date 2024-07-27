package com.koview.koview_server.review.repository;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    Slice<Review> findAllByMember(Member member, Pageable pageable);
}