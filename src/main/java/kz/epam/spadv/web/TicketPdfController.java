package kz.epam.spadv.web;

import kz.epam.spadv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Oleg_Motorin on 2/24/2016.
 */
@Controller
public class TicketPdfController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/tickets", params = "pdf")
    public ModelAndView handle(@RequestParam long userId){
        ModelAndView mav = new ModelAndView("pdfView");
        mav.addObject("tickets", userService.getBookedTicketsByUserId(userId));
        return mav;
    }
}
