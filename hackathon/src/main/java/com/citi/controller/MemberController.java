package com.citi.controller;

import com.citi.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.citi.service.MemberService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String firstName, String lastName, @RequestParam String password,
                                 @RequestParam String phone, @RequestParam String email,
                                 @RequestParam String authCode) throws ParseException {
        memberService.register(firstName, lastName, password, phone, email, authCode);
        return CommonResult.success(null, "sign up was successful");
    }

    @RequestMapping(value = "/checkin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult checkIn(@RequestParam String firstName, String lastName, @RequestParam String password, @RequestParam String phone,
                                @RequestParam String email, @RequestParam String authCode, long birth, String streetAddress, String city,
                                 int zipCode, String country) throws ParseException {
        Date birthday = simpleDateFormat.parse(String.valueOf(birth));
        memberService.checkin(firstName, lastName, password, phone, email, authCode, birthday, streetAddress, city, zipCode, country);
        return CommonResult.success(null, "sign up was successful");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String email,
                              @RequestParam String password) {
        boolean successful = memberService.login(email, password);
        if (!successful) {
            return CommonResult.validateFailed("Incorrect email or password");
        }
        return CommonResult.success("Login successful");
    }

    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String email) {
        String authCode = memberService.generateAuthCode(email);
        return CommonResult.success(authCode, "Successfully obtained verification code");
    }

    @RequestMapping(value = "/sendAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult sendAuthCode(@RequestParam String email) {

        String authCode = memberService.generateAuthCode(email);
        boolean successful = memberService.sendAuthCode(email, authCode);
        if(successful) {
            return CommonResult.success(authCode, "sent verification code successfully");
        } else {
            return CommonResult.success(authCode, "sent verification code failed");
        }
    }

}
