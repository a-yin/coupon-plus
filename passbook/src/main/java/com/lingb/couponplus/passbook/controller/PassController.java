package com.lingb.couponplus.passbook.controller;

import com.lingb.couponplus.passbook.log.LogConstants;
import com.lingb.couponplus.passbook.log.LogGenerator;
import com.lingb.couponplus.passbook.service.IFeedbackService;
import com.lingb.couponplus.passbook.service.IGainPassTemplateService;
import com.lingb.couponplus.passbook.service.IInventoryService;
import com.lingb.couponplus.passbook.service.IUserPassService;
import com.lingb.couponplus.passbook.vo.FeedbackVO;
import com.lingb.couponplus.passbook.vo.GainPassTemplateReqVO;
import com.lingb.couponplus.passbook.vo.PassVO;
import com.lingb.couponplus.passbook.vo.ResultVO;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 优惠券 Controller
 *
 * @author lingb
 * @date 2018.11.18 23:53
 */
@Slf4j
@RestController
@RequestMapping("/passbook")
public class PassController {

    /**
     * 用户优惠券 Service
     */
    private final IUserPassService userPassService;

    /**
     * 优惠券库存 Service
     */
    private final IInventoryService inventoryService;

    /**
     * 用户领取优惠券 Service
     */
    private final IGainPassTemplateService gainPassTemplateService;

    /**
     * 用户反馈 Service
     */
    private final IFeedbackService feedbackService;

    /**
     * HttpServletRequest
     */
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PassController(IUserPassService userPassService,
                          IInventoryService inventoryService,
                          IGainPassTemplateService gainPassTemplateService,
                          IFeedbackService feedbackService,
                          HttpServletRequest httpServletRequest) {
        this.userPassService = userPassService;
        this.inventoryService = inventoryService;
        this.gainPassTemplateService = gainPassTemplateService;
        this.feedbackService = feedbackService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 获取用户未被使用的优惠券
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     * */
    @ResponseBody
    @GetMapping("/userpassinfo")
    ResultVO getUserPassInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_PASS_INFO,
                null
        );
        return userPassService.listUserPass(userId);
    }

    /**
     * 获取用户已经使用的优惠券
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("userusedpassinfo")
    ResultVO listUserUsedPassInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId, LogConstants.ActionName.USER_USED_PASS_INFO,
                null
        );
        return userPassService.listUserUsedPass(userId);
    }

    /**
     * 用户使用优惠券
     *
     * @param passVO {@link PassVO}
     * @return {@link ResultVO}
     */
    @ResponseBody
    @PostMapping("/userusepass")
    ResultVO userUsePass(@RequestBody PassVO passVO) {

        LogGenerator.genLog(
                httpServletRequest,
                passVO.getUserId(),
                LogConstants.ActionName.USER_USE_PASS,
                passVO
        );
        return userPassService.userUsePass(passVO);
    }

    /**
     * 获取库存信息
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/inventoryinfo")
    ResultVO inventoryInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.INVENTORY_INFO,
                null
        );
        return inventoryService.listInventory(userId);
    }

    /**
     * 用户领取优惠券
     *
     * @param request {@link GainPassTemplateReqVO}
     * @return {@link ResultVO}
     * */
    @ResponseBody
    @PostMapping("/gainpasstemplate")
    ResultVO gainPassTemplate(@RequestBody GainPassTemplateReqVO request)
            throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                request.getUserId(),
                LogConstants.ActionName.GAIN_PASS_TEMPLATE,
                request
        );
        return gainPassTemplateService.gainPassTemplate(request);
    }

    /**
     * 用户创建反馈
     *
     * @param feedbackVO {@link FeedbackVO}
     * @return {@link ResultVO}
     * */
    @ResponseBody
    @PostMapping("/createfeedback")
    ResultVO createFeedback(@RequestBody FeedbackVO feedbackVO) {

        LogGenerator.genLog(
                httpServletRequest,
                feedbackVO.getUserId(),
                LogConstants.ActionName.CREATE_FEEDBACK,
                feedbackVO
        );
        return feedbackService.createFeedback(feedbackVO);
    }

    /**
     * 用户获取反馈
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * */
    @ResponseBody
    @GetMapping("/getfeedback")
    ResultVO listFeedback(Long userId) {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.GET_FEEDBACK,
                null
        );
        return feedbackService.listFeedback(userId);
    }

    /**
     * 异常接口
     * @return {@link ResultVO}
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/exception")
    ResultVO exception() throws Exception {
        throw new Exception("Exception...");
    }
}
