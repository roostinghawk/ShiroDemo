package liuwei.demo.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liuwei
 * @date 2019/3/29
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();

            mv.setViewName("/index");

        return mv;
    }
}
