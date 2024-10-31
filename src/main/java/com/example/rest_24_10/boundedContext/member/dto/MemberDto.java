package com.example.rest_24_10.boundedContext.member.dto;

import com.example.rest_24_10.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private LocalDateTime regDate;
    private String userName;

    private MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUsername();
        this.regDate = member.getCreateDate();
    }

    public static MemberDto of(Member member) {
        return new MemberDto(member);
    }
}