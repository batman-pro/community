package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GiteeUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeOauthProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code="+accessTokenDto.getCode()
                        +"&client_id="+accessTokenDto.getClientId()+"&redirect_uri="+accessTokenDto.getRedirectUri()
                        +"&client_secret="+accessTokenDto.getClientSecret())
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String all = response.body().string();
            String access_token = JSON.parseObject(all).get("access_token").toString();
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUserDTO getGiteeUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            GiteeUserDTO giteeUser = JSON.parseObject(str, GiteeUserDTO.class);
            return giteeUser;
        } catch (IOException e) {
        }
        return null;
    }
}
