package kz.epam.spadv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Олег on 20.02.2016.
 */
@Controller
public class HomeController {

    @RequestMapping({"/","/index.htm"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout){
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("param_error",error);
        mav.addObject("param_logout",logout);
        return mav;
    }

    @RequestMapping("/uploadUsers")
    public String uploadUsers(){
        return "uploadUsers";
    }

    @RequestMapping("/uploadEvents")
    public String uploadEvents(){
        return "uploadEvents";
    }

    @RequestMapping(value = "findUser", method = RequestMethod.GET)
    public String viewFindUser(){
        return "findUser";
    }

    @RequestMapping(value = "findEvent", method = RequestMethod.GET)
    public String viewFindEvent(){
        return "findEvent";
    }

    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public String doFindUser(@RequestParam String findBy, @RequestParam String searchField){
        return  "redirect:/users/"+findBy+"/"+searchField;
    }

}
