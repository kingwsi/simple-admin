package com.simple.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.common.bean.AuthUser;
import com.simple.common.entity.member.Member;
import com.simple.common.entity.member.MemberConvertMapper;
import com.simple.common.entity.member.MemberVO;
import com.simple.common.enumerate.AuthType;
import com.simple.common.enumerate.RespCodeEnum;
import com.simple.common.exception.CustomException;
import com.simple.common.utils.TokenUtils;
import com.simple.common.wechat.WechatAuth;
import com.simple.common.wechat.WechatCode2Session;
import com.simple.common.wechat.WechatUserInfo;
import com.simple.feign.WechatFeignClient;
import com.simple.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * description: 会员 Service <br>
 * date: 2021-04-13 <br>
 * author: ws <br>
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    private final MemberConvertMapper memberConvertMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final WechatFeignClient wechatFeignClient;
    
    private final ParamsService paramsService;
    
    private final ObjectMapper objectMapper;

    /**
     * 插入数据
     *
     * @param memberVO
     * @return
     */
    public boolean create(MemberVO memberVO) {
        return memberMapper.insert(memberConvertMapper.toMember(memberVO)) > 0;
    }

    /**
     * 插入数据
     *
     * @param member
     * @return
     */
    public boolean create(Member member) {
        return memberMapper.insert(member) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return memberMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<MemberVO> listOfPage(Page<MemberVO> page, MemberVO vo) {
        return memberMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     *
     * @param memberVO
     * @return
     */
    public boolean updateById(MemberVO memberVO) {
        return memberMapper.updateById(memberConvertMapper.toMember(memberVO)) > 0;
    }

    public MemberVO getById(Integer id) {
        Member member = memberMapper.selectById(id);
        return memberConvertMapper.toMemberVO(member);
    }

    public String authByPwd(AuthUser authUser) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile", authUser.getMobile());
        Member member = memberMapper.selectOne(memberQueryWrapper);

        Boolean authResult = Optional.ofNullable(member)
                .map(o -> bCryptPasswordEncoder.matches(authUser.getPassword(), o.getPassword())).orElse(false);
        if (!authResult) {
            throw new CustomException("用户名或密码错误！");
        }
        if (member.getAccountStatus() != 1) {
            throw new CustomException("账户不可用！");
        }
        return TokenUtils.createMemberToken(authUser);
    }

    public void register(AuthUser authUser) {
        if (StringUtils.isEmpty(authUser.getUsername()) ||
                StringUtils.isEmpty(authUser.getMobile()) ||
                StringUtils.isEmpty(authUser.getPassword())) {
            throw new CustomException("参数错误！");
        }
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery(Member.class)
                .eq(Member::getMobile, authUser.getMobile());
        Integer count = memberMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new CustomException("账户已存在！");
        }
        Member member = new Member();
        member.setMobile(authUser.getMobile());
        member.setNickName(authUser.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(authUser.getPassword()));
        member.setCreatedDate(LocalDateTime.now());
        memberMapper.insert(member);
    }

    public String authByMobile(AuthUser authUser) {
        return null;
    }

    public Member getMemberByOpenid(String openid) {
        return memberMapper.selectOne(Wrappers.lambdaQuery(Member.class)
                .eq(Member::getDeleted, false)
                .eq(Member::getOpenid, openid));
    }

    public WechatAuth wechatOauth(String code) {
        String wechatSecret = paramsService.getValueByCode("wechat_secret");
        String wechatAppid = paramsService.getValueByCode("wechat_appid");
        String responseStr = wechatFeignClient.code2Session(wechatAppid, wechatSecret, code);
        ObjectMapper objectMapper = new ObjectMapper();
        WechatCode2Session wechatCode2Session = null;
        try {
            wechatCode2Session = objectMapper.readValue(responseStr, WechatCode2Session.class);
            log.info("code2Session->{}", wechatCode2Session);
            if (wechatCode2Session.getErrcode() != 0) {
                String errmsg = wechatCode2Session.getErrmsg();
                throw new CustomException(StringUtils.hasText(errmsg) ? errmsg : "登陆异常请重试！");
            }
        } catch (JsonProcessingException e) {
            log.info("wechatOauth异常->{}", e.getMessage());
            throw new CustomException("登陆异常请重试！");
        }

        Member member = this.getMemberByOpenid(wechatCode2Session.getOpenid());
        if (member == null) {
            member= new Member();
            // 存入sessionKey
            member.setAccountStatus(0);
            member.setOpenid(wechatCode2Session.getOpenid());
            member.setSessionKeyTime(LocalDateTime.now());
            member.setSessionKey(wechatCode2Session.getSessionKey());
            memberMapper.insert(member);
            return new WechatAuth(wechatCode2Session.getOpenid(), 2);
        } else {
            if (member.getAccountStatus() != 1) {
                // 未绑定 更新sessionKey
                member.setAccountStatus(0);
                member.setSessionKey(wechatCode2Session.getSessionKey());
                member.setSessionKeyTime(LocalDateTime.now());
                memberMapper.updateById(member);
                return new WechatAuth(wechatCode2Session.getOpenid(), 2);
            }
            // 返回token
            String memberToken = TokenUtils.createMemberToken(new AuthUser(member.getId(), "", AuthType.WECHAT));
            return new WechatAuth(memberToken, 0);
        }
    }

    public String bindWechatInfo(WechatAuth wechatAuth) {
        try {
            Member member = this.getMemberByOpenid(wechatAuth.getToken());
            if (member == null || member.getSessionKey() == null || member.getSessionKeyTime() == null) {
                throw new CustomException("绑定失败 请重新授权！");
            }
            Duration between = Duration.between(member.getSessionKeyTime(), LocalDateTime.now());

            if (between.toMinutes() > 9) {
                throw new CustomException("授权超时，请重新授权！");
            }
            // 解析微信加密字符串
            log.info("开始解密 sessionKey->{}, text->{}, iv->{}", member.getSessionKey(), wechatAuth.getEncryptedData(), wechatAuth.getIv());
            WechatUserInfo userInfo = this.getUserInfo(wechatAuth.getEncryptedData(), member.getSessionKey(), wechatAuth.getIv());
            member.setNickName(userInfo.getNickName());
            member.setAccountStatus(1);
            member.setAvatar(userInfo.getAvatarUrl());
            member.setOpenid(wechatAuth.getToken());
            member.setCreatedDate(LocalDateTime.now());
            member.setGender(userInfo.getGender() == 1 ? "男" : "女");
            member.setLastLoginTime(LocalDateTime.now());
            memberMapper.updateById(member);
            return TokenUtils.createMemberToken(new AuthUser(member.getId(), userInfo.getNickName(), AuthType.WECHAT));
        } catch (Exception e) {
            log.info("绑定异常！{}", e.getMessage());
            throw new CustomException(RespCodeEnum.MEMBER_UNBIND);
        }
    }


    /**
     * 获取信息
     *
     * @return
     */
    public WechatUserInfo getUserInfo(String encryptedData, String sessionKey, String iv) throws Exception {
        byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);

        // 创建AES算法的密钥规范对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(sessionKeyBytes, "AES");

        // 创建AES算法的初始化向量参数对象
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // 创建AES算法的加密/解密对象
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // 设置加密/解密模式和密钥、初始化向量参数
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // 解密密文数据
        byte[] decryptedDataBytes = cipher.doFinal(encryptedDataBytes);

        // 将解密后的明文数据转换为字符串并返回
        String userInfoData = new String(decryptedDataBytes, StandardCharsets.UTF_8);
        return objectMapper.readValue(userInfoData, WechatUserInfo.class);
    }

    public Object getCurrentMemberInfo() {
        Integer currentUserId = TokenUtils.getCurrentPrincipalId();
        return this.getById(currentUserId);
    }
}
