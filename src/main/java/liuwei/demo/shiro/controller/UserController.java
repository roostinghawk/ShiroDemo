package liuwei.demo.shiro.controller;

import liuwei.demo.shiro.consts.Const;
import liuwei.demo.shiro.model.User;
import liuwei.demo.shiro.service.UserService;
import liuwei.demo.shiro.util.PasswordUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author liuwei
 * @date 2019/4/11
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("user:view")
    public ModelAndView list(){
        List<User> users = userService.findUsers();

        ModelAndView mv = new ModelAndView();
        mv.addObject("users", users);
        mv.setViewName("/user/list");
        return mv;
    }

    /**
     * 添加用户页面
     * @return
     */

    @GetMapping("/add")
    @RequiresPermissions("user:add")
    public String addUser(){
        return "/user/add";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions("user:add")
    public String create(@ModelAttribute User user){

        PasswordUtil.entryptPassword(user);
        user.setState("1");
        userService.addUser(user);

        return "redirect:/users";
    }


}
