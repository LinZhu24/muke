package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.controller.vo.UserVO;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.UserService;
import com.miaosha.miaosha.service.model.UserInfoModel;
import com.miaosha.miaosha.util.response.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 19:37
 * @Desc:
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * 获得手机验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping(value = "/getOpt", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public ResponseEntity getOpt(String telephone) {
        //1：按照一定的规则生成Opt验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);
        //2：将opt验证码与用户的手机号关联，使用httpSession的方式绑定手机号和optCode
        httpServletRequest.getSession().setAttribute(telephone, optCode);
        //3：将opt验证码通过短信通道发送给用户，该步骤省略（可使用邮箱代替）

        System.out.println("telephone=" + telephone + " and optCode=" + optCode);
        Map<String, String> map = new HashMap<>(2);
        map.put("telephone", telephone);
        map.put("optCode", optCode);
        return ResponseEntity.create(map);
    }

    /**
     * 获取用户对象VO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("/getUser")
    @ResponseBody
    public ResponseEntity getUser(Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象，并返回给前端
        UserInfoModel userInfoModel = userService.getUserById(id);
        //把核心领域模型对象转化为可供UI使用的ViewObject
        UserVO userVO = convertFromModel(userInfoModel);
        if (userVO == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        return ResponseEntity.create(userVO);
    }

    /**
     * 用户登录接口
     *
     * @param telephone
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public ResponseEntity login(String telephone, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"手机号或者密码为空");
        }
        //用户登录服务，检查登录是否合法
        UserInfoModel userInfoModel = userService.validateLogin(telephone, this.encodeByMD5(password));
        //将登录凭证加入到用户登录成功的session内（分布式会话session的机制去解决分布式环境下用户登录的问题，当前的环境下假设用户是单点的session登录）
        this.httpServletRequest.getSession().setAttribute("isLogin",true);
        this.httpServletRequest.getSession().setAttribute("loginUser",userInfoModel);
        return ResponseEntity.create(null);
    }

    /**
     * 用户注册接口
     *
     * @param telephone
     * @param optCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public ResponseEntity register(String telephone, String optCode, String name,
                                   Integer gender, Integer age, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String embeddedSessionCode = (String) httpServletRequest.getSession().getAttribute(telephone);
        if (!StringUtils.equals(embeddedSessionCode, optCode)) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "验证码不一致");
        }
        UserInfoModel model = new UserInfoModel();
        model.setAge(age);
        model.setTelephone(telephone);
        model.setName(name);
        Byte genderByte = gender.byteValue();
        model.setGender(genderByte);
        model.setRegisterMode("byPhone");
        model.setEncrpyPassword(this.encodeByMD5(password));
        userService.register(model);
        return ResponseEntity.create(null);
    }

    /**
     * 对密码进行加密
     * MD5加密后不可逆
     * @param str
     * @return
     */
    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        return base64Encoder.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }



    /**
     * 将 Model 转化为 VO
     *
     * @param userInfoModel
     * @return
     */
    public UserVO convertFromModel(UserInfoModel userInfoModel) {
        UserVO userVO = new UserVO();
        if (userInfoModel == null) {
            return null;
        }
        BeanUtils.copyProperties(userInfoModel, userVO);
        return userVO;
    }

}
