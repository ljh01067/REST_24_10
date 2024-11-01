package com.example.rest_24_10.boundedContext.member.controller;

import com.example.rest_24_10.base.rsData.RsData;
import com.example.rest_24_10.boundedContext.member.dto.MemberDto;
import com.example.rest_24_10.boundedContext.member.entity.Member;
import com.example.rest_24_10.boundedContext.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class LoginResponse {
        private final String accessToken;
    }


    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String accessToken = memberService.genAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        return RsData.of("S-1", "액세스 토큰 생성됨", new LoginResponse(accessToken));
    }

    @AllArgsConstructor
    @Getter
    public static class MeResponse {
        private final MemberDto member;
    }

    //consumes = ALL_VALUE -> Json 형태로 입력받는게 필수가 아니다.
    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "로그인 된 사용자 정보", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<MeResponse> me(@AuthenticationPrincipal User user) {
        Member member = memberService.findByUsername(user.getUsername()).get();

        return RsData.of("S-1",
                "성공",
                new MeResponse(MemberDto.of(member))
        );
    }

}