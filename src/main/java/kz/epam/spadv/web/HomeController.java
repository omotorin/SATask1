package kz.epam.spadv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Олег on 20.02.2016.
 */
@Controller
public class HomeController {

    @RequestMapping({"/","/index.htm"})
    public String index(){
        return "index";
    }

    @RequestMapping("login")
    public String login(){
        return "index";
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
