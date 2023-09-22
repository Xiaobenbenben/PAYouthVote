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

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult healthcheck() {
        return CommonResult.success(null, "Backend service is working well");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String firstName, String lastName, @RequestParam(required = false) String phone,
                                 @RequestParam String email, @RequestParam String authCode) {
        String status = memberService.register(firstName, lastName, phone, email, authCode);
        return CommonResult.success(null, status);
    }

    @RequestMapping(value = "/checkin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult checkIn(@RequestParam String firstName, String lastName, @RequestParam(required = false) String phone,
                                @RequestParam String email, @RequestParam String authCode, long birth, String streetAddress, String city,
                                 int zipCode, String country) throws ParseException {
        Date birthday = simpleDateFormat.parse(String.valueOf(birth));
        String status = memberService.checkin(firstName, lastName, phone, email, authCode, birthday, streetAddress, city, zipCode, country);
        return CommonResult.success(null, status);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String email,
                              @RequestParam String authCode) {
        boolean successful = memberService.login(email, authCode);
        if (!successful) {
            return CommonResult.validateFailed("Incorrect email or authCode");
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
        try {
            memberService.sendAuthCode(email, authCode);
        } catch (Exception e) {
            return CommonResult.success(e, "sent verification code failed");
        }
        return CommonResult.success(null, "sent verification code successfully");
    }

}
