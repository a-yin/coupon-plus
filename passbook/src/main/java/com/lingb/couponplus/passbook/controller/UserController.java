package com.lingb.couponplus.passbook.controller;

import com.lingb.couponplus.passbook.log.LogConstants;
import com.lingb.couponplus.passbook.log.LogGenerator;
import com.lingb.couponplus.passbook.service.IUserService;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.lingb.couponplus.passbook.vo.UserVO;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户 Controller
 *
 * @author lingb
 * @date 2018.11.22 19:53
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户 Service
     */
    private final IUserService userService;

    /**
     * HttpServletRequest
     */
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public UserController(IUserService userService,
                                HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 创建用户
     *
     * @param userVO {@link UserVO}
     * @return {@link Response}
     * */
    @ResponseBody
    @PostMapping("/createuser")
    ResultVO createUser(@RequestBody UserVO userVO) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                -1L,
                LogConstants.ActionName.CREATE_USER,
                userVO
        );
        return userService.createUser(userVO);
    }
}
