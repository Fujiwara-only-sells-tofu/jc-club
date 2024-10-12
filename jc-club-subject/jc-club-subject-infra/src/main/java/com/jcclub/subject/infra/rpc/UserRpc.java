package com.jcclub.subject.infra.rpc;



import com.jcclub.auth.api.UserFeignService;
import com.jcclub.auth.entity.AuthUserDTO;
import com.jcclub.auth.entity.Result;
import com.jcclub.subject.infra.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName：UserRpc
 * @Author: gouteng
 * @Date: 2024/10/9 20:49
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Component
@RequiredArgsConstructor
public class UserRpc {

    private final UserFeignService userFeignService;

    public UserInfo getUserInfo(String username){
        AuthUserDTO authUserDTO =new AuthUserDTO();
        authUserDTO.setUserName(username);
        Result<AuthUserDTO> result = userFeignService.getUserInfo(authUserDTO);
        UserInfo userInfo = new UserInfo();
        if(!result.getSuccess()){
            return userInfo;
        }
        AuthUserDTO data = result.getData();
        userInfo.setUserName(data.getUserName());
        userInfo.setNickName(data.getNickName());
        userInfo.setAvatar(data.getAvatar());
        return userInfo;

    }

}
