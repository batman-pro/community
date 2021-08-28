package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GiteeUserDTO;
import life.majiang.community.provider.GiteeOauthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GiteeOauthProvider giteeOauthProvider;

    @Value("${gitee.clientId}")
    private String clientId;
    @Value("${gitee.clientSecret}")
    private String clientSecret;
    @Value("${gitee.redirectUri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setClientId(clientId);
        accessTokenDto.setClientSecret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirectUri(redirectUri);
        String accessToken = giteeOauthProvider.getAccessToken(accessTokenDto);
        GiteeUserDTO giteeUser = giteeOauthProvider.getGiteeUser(accessToken);
        return "index";
    }
}
