package life.majiang.community.dto;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;

@Data
public class AccessTokenDTO {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
}
