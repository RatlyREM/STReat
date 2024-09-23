package io.ssafy.p.j11a307.user.entity;

import io.ssafy.p.j11a307.user.vo.KakaoInfoVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long kakaoId;
    private String username;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private String profileImgSrc;

    @CreatedDate
    private LocalDateTime createdAt;

    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean orderStatusAlert;

    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean dibsStoreAlert;

    public User(KakaoInfoVo kakaoInfoVo) {
        this.kakaoId = kakaoInfoVo.getKakaoId();
        this.username = kakaoInfoVo.getNickname();
        this.profileImgSrc = kakaoInfoVo.getImageSrc();
        this.kakaoAccessToken = kakaoInfoVo.getAccessToken();
        this.kakaoRefreshToken = kakaoInfoVo.getRefreshToken();
    }

    public void refreshKakaoTokens(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        if (kakaoRefreshToken != null) { // 경우에 따라 kakao token은 refresh 되지 않을 수 있음
            this.kakaoRefreshToken = kakaoRefreshToken;
        }
    }

    public void logout() {
        this.kakaoAccessToken = null;
        this.kakaoRefreshToken = null;
    }
}