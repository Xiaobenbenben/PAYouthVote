package com.citi.service.impl;

import com.citi.api.Member;
import com.citi.exception.Asserts;
import com.citi.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 */
@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    private static final String AUTH_CODE = "AUTH_CODE";
    private Map<String, String> authCodeCache = new HashMap<>();
    private Map<String, Member> memberCache = new HashMap<>();
    private Map<String, String> passwordCache = new HashMap<>();

    @Autowired
    private JavaMailSender javaMailSender;

    //    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private UmsMemberMapper memberMapper;
//    @Autowired
//    private UmsMemberLevelMapper memberLevelMapper;
//    @Autowired
//    private UmsMemberCacheService memberCacheService;
//    @Value("${redis.key.authCode}")
//    private String REDIS_KEY_PREFIX_AUTH_CODE;
//    @Value("${redis.expire.authCode}")
//    private Long AUTH_CODE_EXPIRE_SECONDS;
//
    @Override
    public Member getByUsername(String username) {
        return null;
    }

    @Override
    public Member getById(Long id) {
//        return memberMapper.selectByPrimaryKey(id);
        return null;
    }

    @Override
    public void checkin(String firstName, String lastName, String password, String phone, String email, String authCode, Date birthday,
                         String streetAddress, String city, int zipCode, String country) {
        if (!verifyAuthCode(authCode, email)) {
            Asserts.fail("Verification code error");
        }
        Member member = memberCache.get(email);
        if (Objects.nonNull(member) && StringUtils.equals(member.getEmail(), email)) {
            Asserts.fail("The user already exists");
        }
        Member current = new Member();
        current.setFirstName(firstName);
        current.setLastName(lastName);
        current.setEmail(email);
        current.setPassword(password);
        current.setBirthday(birthday);
        current.setStreetAddress(streetAddress);
        current.setCreateTime(new Date());
        current.setCity(city);
        current.setZipCode(zipCode);
        current.setCountry(country);
        memberCache.put(email, current);
    }

    public void register(String firstName, String lastName, String password, String telephone, String email, String authCode) {
        if (!verifyAuthCode(authCode, email)) {
            Asserts.fail("Verification code error");
        }
        Member member = memberCache.get(email);
        if (Objects.nonNull(member) && StringUtils.equals(member.getEmail(), email)) {
            Asserts.fail("The user already exists");
        }
        Member current = new Member();
        current.setFirstName(firstName);
        current.setLastName(lastName);
        current.setEmail(email);
        current.setPassword(password);
        current.setCreateTime(new Date());
        memberCache.put(email, current);
    }

    @Override
    public String generateAuthCode(String email) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        authCodeCache.put(AUTH_CODE + ":" + email.trim(), sb.toString());
        return sb.toString();
    }

    @Override
    public boolean sendAuthCode(String email, String authCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("PA Youth Vote");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("PA Youth Vote AuthCode");
            simpleMailMessage.setText("Your PA Youth Vote AuthCode is: " + authCode);
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
//        UmsMemberExample example = new UmsMemberExample();
//        example.createCriteria().andPhoneEqualTo(telephone);
//        List<UmsMember> memberList = memberMapper.selectByExample(example);
//        if(CollectionUtils.isEmpty(memberList)){
//            Asserts.fail("该账号不存在");
//        }
//        //验证验证码
//        if(!verifyAuthCode(authCode,telephone)){
//            Asserts.fail("验证码错误");
//        }
//        UmsMember umsMember = memberList.get(0);
//        umsMember.setPassword(passwordEncoder.encode(password));
//        memberMapper.updateByPrimaryKeySelective(umsMember);
//        memberCacheService.delMember(umsMember.getId());
    }

    @Override
    public Member getCurrentMember() {
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Authentication auth = ctx.getAuthentication();
//        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
//        return memberDetails.getUmsMember();
        return null;
    }

    @Override
    public boolean login(String email, String password) {
        String expectedPassword = passwordCache.get(email);
        return StringUtils.equals(expectedPassword, password);
    }

    @Override
    public String refreshToken(String token) {
//        return jwtTokenUtil.refreshHeadToken(token);
        return null;
    }

    private boolean verifyAuthCode(String authCode, String email) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = authCodeCache.get(AUTH_CODE + ":" + email.trim());
        return authCode.equals(realAuthCode);
    }

}
