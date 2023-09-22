package com.demo.service;

import com.demo.api.Member;

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
    String register(String fristName, String lastName, String telephone, String email, String authCode);

    String checkin(String fristName, String lastName, String telephone, String email, String authCode,
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
