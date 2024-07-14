package com.koview.koview_server.member.domain.dto;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.memberLikedShop.domain.dto.MemberLikedShopDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class SignupResponseDTO {
    private final Long id;
    private final String email;
    private final String nickname;
    private final int age;
    private List<MemberLikedShopDTO> memberLikedShopList;

    /* Entity -> DTO */
    public SignupResponseDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.age = member.getAge();
        this.memberLikedShopList = member.getMemberLikedShopList().stream()
                .map(MemberLikedShopDTO::new)
                .collect(Collectors.toList());    }
}
