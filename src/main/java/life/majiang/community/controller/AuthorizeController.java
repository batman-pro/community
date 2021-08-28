package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GiteeUserDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GiteeOauthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                            HttpServletRequest request) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setClientId(clientId);
        accessTokenDto.setClientSecret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirectUri(redirectUri);
        String accessToken = giteeOauthProvider.getAccessToken(accessTokenDto);
        GiteeUserDTO giteeUser = giteeOauthProvider.getGiteeUser(accessToken);
        if (giteeUser != null) {
            //登录成功
            User user = new User();
            user.setName(giteeUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user", giteeUser);
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
