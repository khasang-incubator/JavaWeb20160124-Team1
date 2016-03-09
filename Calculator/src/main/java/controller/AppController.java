package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dmitriy on 09.03.2016.
 */
@Controller
public class AppController {
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Calculator login page");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/calc**", method = {RequestMethod.GET})
    public ModelAndView protectedPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title");
        modelAndView.setViewName("calc");
        return modelAndView;
    }
}
