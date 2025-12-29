package com.ch.shop.dto;

import lombok.Data;

@Data
public class KakaoAccount {
    // 사용자가 이메일 제공에 동의했는지 여부
    private Boolean has_email;
    private String email;
    
    // 프로필 정보 (닉네임, 이미지 등)
    private Profile profile;
    
    private String gender;
    private String age_range;
    private String birthday;

    @Data
    public static class Profile {
        private String nickname;
        private String thumbnail_image_url;
        private String profile_image_url;
    }
}