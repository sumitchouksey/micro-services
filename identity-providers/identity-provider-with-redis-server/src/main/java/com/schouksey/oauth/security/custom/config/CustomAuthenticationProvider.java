package com.schouksey.oauth.security.custom.config;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.custom.config
 * Class Name     : CustomAuthenticationProvider
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */


import com.schouksey.identity.provider.entity.ClientEntity;
import com.schouksey.identity.provider.entity.RolesEntity;
import com.schouksey.identity.provider.entity.UserEntity;
import com.schouksey.identity.provider.service.IdentityProviderService;
import com.schouksey.identity.provider.utility.ResponseConstant;
import com.schouksey.utility.api.encryption.HmacEncryption;
import com.schouksey.utility.api.util.Utility;
import com.schouksey.utility.api.vos.ClientVo;
import com.schouksey.utility.api.vos.RoleVo;
import com.schouksey.utility.api.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    private IdentityProviderService identityProviderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientName =  httpServletRequest.getParameter("client_id");
        String email = authentication.getName();
        String password  = null;
        Long clientId= 0L;

        /*Email Validation*/
        if(email==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.EMAIL_ID_NULL.getStatus(),"400","Bad Request");
        else if(org.apache.commons.lang.StringUtils.isBlank(email))
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.EMAIL_ID_EMPTY.getStatus(),"400","Bad Request");
        /*Password Validation*/
        if(authentication.getCredentials()==null)
            throw  CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.PASSWORD_IS_NULL.getStatus(),"400","Bad Request");
        else if(org.apache.commons.lang.StringUtils.isBlank(authentication.getCredentials().toString()))
            throw  CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.PASSWORD_IS_EMPTY.getStatus(),"400","Bad Request");
        else
        {
            password  = authentication.getCredentials().toString();
            try{
                password = URLDecoder.decode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if(!pattern.matcher(password).find())
                throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.IMPROPER_PASSWORD_PATTERN.getStatus(),"400","Bad Request");
        }
        /*User Validations*/
        UserEntity userEntity = identityProviderService.getUserEntity(email.trim());
        if(userEntity==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.USER_NOT_FOUND.getStatus(),"404","Not Found");
        if(!userEntity.getIsActive())
            throw  CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.USER_IS_INACTIVE.getStatus(),"404","Not Found");
        if(userEntity.getAttempts()>5)
            throw  CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.ATTEMPT_LIMIT_EXCEEDED.getStatus(),"410","Failure");
        if(!HmacEncryption.validateHmac(userEntity.getPasswordCreatedOn(),userEntity.getPassword(),password)) {
            userEntity.setAttempts((userEntity.getAttempts()+1));
            identityProviderService.saveOrUpdateUserEntity(userEntity);
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.INVALID_PASSWORD.getStatus(), "410", "Unauthorized");
        }

        ClientEntity clientEntity  = identityProviderService.getClientEntity(clientName);
        if(clientEntity!=null)
            clientId=clientEntity.getId();
        boolean flag=false;
        Set<RolesEntity> rolesEntity = userEntity.getRolesEntity();
        List<RoleVo> roleVOs = new ArrayList<RoleVo>();
        List<Long> roleIds  = new ArrayList<Long>();
        List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
        if(rolesEntity!=null){
            if(!rolesEntity.isEmpty()){
                for(RolesEntity re : rolesEntity){
                    if(re.getIsActive()){
                        authorities.add(new SimpleGrantedAuthority(re.getRoleName()));
                        ClientEntity clientEntity1= re.getClientEntity();
                        if(clientId==clientEntity1.getId()){
                            flag=true;
                            RoleVo roleVO  = new RoleVo();
                            roleVO.setRoleId(re.getId());
                            roleVO.setRoleName(re.getRoleName());
                            roleVOs.add(roleVO);
                            roleIds.add(re.getId());
                        }
                    }
                }
            }
        }
        if(!flag)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.USER_DOESNOT_BELONG_TO_ORGANIZATION.getStatus(), "400", "Bad Request");


        userEntity.setAttempts(0);
        identityProviderService.saveOrUpdateUserEntity(userEntity);
        UserVo userVo  = getUserVO(userEntity,clientEntity,roleVOs);
        UsernamePasswordAuthenticationToken authenticationObject = new UsernamePasswordAuthenticationToken(email,password,authorities);
        authenticationObject.setDetails(userVo);
        return authenticationObject;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


    /*Initialize UserVo*/
    public UserVo getUserVO(UserEntity entity, ClientEntity clientEntity, List<RoleVo> roleVOs)
    {
        UserVo userVo = new UserVo();
        userVo.setUserId(entity.getId());
        userVo.setName(entity.getName());
        userVo.setAddress(entity.getAddress());
        userVo.setDateOfBirth(entity.getDateOfBirth());
        userVo.setEmail(entity.getEmail());
        userVo.setMobile(entity.getMobile());
        userVo.setProfilePic(entity.getProfilePic());
        userVo.setRoles(roleVOs);
        userVo.setClient(initializeClientVo(clientEntity));
        return userVo;
    }

    private Collection<ClientVo> initializeClientVo(ClientEntity clientEntity) {
        List<ClientVo> clientVos  = new ArrayList<>();
        ClientVo clientVo  = new ClientVo();
        clientVo.setClientName(clientEntity.getClientName());
        clientVo.setClientId(clientEntity.getId());
        clientVo.setParentClientId(clientEntity.getParentClientId());
        clientVo.setConfigurations(clientEntity.getConfiguration());
        clientVos.add(clientVo);
        return clientVos;
    }
}
