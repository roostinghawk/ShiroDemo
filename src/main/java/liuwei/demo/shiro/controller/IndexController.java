package liuwei.demo.shiro.controller;

import liuwei.demo.shiro.consts.Const;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liuwei
 * @date 2019/3/29
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public ModelAndView index(){
//        String userName = SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_KEY_USERNAME).toString();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");
//        mv.addObject("userName", userName);
        return mv;
    }

    @GetMapping("/403")
    public ModelAndView unAuthorized(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/common/403");
        return mv;
    }
}
