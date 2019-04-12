package liuwei.demo.shiro.controller;

import liuwei.demo.shiro.consts.Const;
import liuwei.demo.shiro.model.User;
import liuwei.demo.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liuwei
 * @date 2019/3/29
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login/login");

        return mv;
    }

    @PostMapping("/login")
    public String login(@RequestParam("loginName") String loginName, @RequestParam("password") String password, Model model){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginName, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(usernamePasswordToken);
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("login","password error");
            return "error";
        }catch (UnknownAccountException uae) {
            model.addAttribute("login","userName error");
            return "error";
        }catch (ExcessiveAttemptsException eae) {
            model.addAttribute("login","time error");
            return "error";
        }
        User user = userService.findUserByLoginName(loginName);
        SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_KEY_USERNAME, user.getName());

        return "redirect:/index";
    }
}
