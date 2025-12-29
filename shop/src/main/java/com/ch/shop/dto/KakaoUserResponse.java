package com.ch.shop.dto;

import lombok.Data;

@Data
public class KakaoUserResponse {
    private Long id;              // 카카오의 고유 식별자 (숫자형태)
    private KakaoAccount kakao_account;
    
    // 닉네임 등을 담고 있는 properties (선택사항)
    private Properties properties;

    @Data
    public static class Properties {
        private String nickname;
        private String profile_image;
    }
}