package com.demo.service.impl;

import com.demo.api.Member;
import com.demo.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


/**
 */
@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    private static final String AUTH_CODE = "AUTH_CODE";
    private Map<String, String> authCodeCache = new HashMap<>();
    private Map<String, Member> memberCache = new HashMap<>();
    private Map<String, Member> checkinMemberCache = new HashMap<>();

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Override
    public Member getByUsername(String username) {
        return null;
    }

    @Override
    public Member getById(Long id) {
        return null;
    }

    @Override
    public String checkin(String firstName, String lastName, String phone, String email, String authCode, Date birthday,
                        String streetAddress, String city, int zipCode, String country) {
        if (!verifyAuthCode(authCode, email)) {
            return "Verification code error";
        }
        Member member = checkinMemberCache.get(email);
        if (Objects.nonNull(member) && StringUtils.equals(member.getEmail(), email)) {
           return "The user already exists";
        }
        Member current = new Member();
        current.setFirstName(firstName);
        current.setLastName(lastName);
        current.setEmail(email);
        current.setPhone(phone);
        current.setBirthday(birthday);
        current.setStreetAddress(streetAddress);
        current.setCreateTime(new Date());
        current.setCity(city);
        current.setZipCode(zipCode);
        current.setCountry(country);
        checkinMemberCache.put(email, current);
        memberCache.put(email, current);
        return "checkin successfully";
    }

    public String register(String firstName, String lastName, String telephone, String email, String authCode) {
        if (!verifyAuthCode(authCode, email)) {
           return "Verification code error";
        }
        Member member = memberCache.get(email);
        if (Objects.nonNull(member) && StringUtils.equals(member.getEmail(), email)) {
            return "The user already exists";
        }
        Member current = new Member();
        current.setFirstName(firstName);
        current.setLastName(lastName);
        current.setEmail(email);
        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("Email", AttributeValue.builder().s(email).build());
        itemValues.put("FirstName", AttributeValue.builder().s(firstName).build());
        itemValues.put("LastName", AttributeValue.builder().s(lastName).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("PA_YOUTH_VOTE_1025")
                .item(itemValues)
                .build();
        memberCache.put(email, current);
        try {
            PutItemResponse response = dynamoDbClient.putItem(request);
            System.out.println("PA_YOUTH_VOTE_1025" + " was successfully updated. The request id is " + response.responseMetadata().requestId());
            return "sign up was successful";
        } catch (Exception e) {
            System.out.println("insert db failed " + e.getMessage());
            return "sign up was successful";
        }
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
            Properties pros = new Properties();
            pros.put("mail.smtp.host", "mx.mailslurp.com");
            pros.put("mail.smtp.port", "2525");
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(pros, new Authenticator() {
                String userName = System.getenv("USER_NAME");
                String password = System.getenv("PASSWORD");

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
            MimeMessage message=new MimeMessage(session);
            message.setSubject("PV Youth Vote Authentication Code");
            message.setText("Your PV Youth Vote Authentication Code is " + authCode);
            message.setFrom(new InternetAddress("9dcb30d5-aeea-4346-9610-41acd02c3dba@mailslurp.mx"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            Transport.send(message);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
    }

    @Override
    public Member getCurrentMember() {
        return null;
    }

    @Override
    public boolean login(String email, String password) {
        String expectedPassword = authCodeCache.get(AUTH_CODE + ":" + email.trim());
        return StringUtils.equals(expectedPassword, password);
    }

    @Override
    public String refreshToken(String token) {
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
