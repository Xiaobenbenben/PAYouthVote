package com.citi.service;

import com.citi.api.Member;

import java.util.Date;

/**
 */
public interface MemberService {
    /**
     */
    Member getByUsername(String username);

    /**
     */
    Member getById(Long id);

    /**
     */
    void register(String fristName, String lastName, String password, String telephone, String email, String authCode);

    void checkin(String fristName, String lastName, String password, String telephone, String email, String authCode,
                 Date birthday, String streetAddress, String city, int zipCode, String country);

    /**
     */
    String generateAuthCode(String email);

    boolean sendAuthCode(String telephone, String authCode);

    /**
     */
    void updatePassword(String telephone, String password, String authCode);

    /**
     */
    Member getCurrentMember();

    /**
     */
    boolean login(String username, String password);

    /**
     */
    String refreshToken(String token);
}
