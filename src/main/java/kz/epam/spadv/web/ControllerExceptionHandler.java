package kz.epam.spadv.web;

import kz.epam.spadv.service.exception.EventNotFoundException;
import kz.epam.spadv.service.exception.UserNotRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Oleg_Motorin on 2/26/2016.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotRegisteredException.class)
    public ModelAndView onUserNotRegisteredException(Exception e){
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        mav.addObject("msg", e.getMessage());
        return mav;
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ModelAndView onEventNotFoundException(EventNotFoundException e){
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        mav.addObject("msg", e.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView onServerException(Exception e){
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        mav.addObject("msg", e.getMessage());
        return mav;
    }
}
