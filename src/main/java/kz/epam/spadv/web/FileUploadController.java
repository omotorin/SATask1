package kz.epam.spadv.web;

import kz.epam.spadv.domain.Role;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.service.EventService;
import kz.epam.spadv.service.Rating;
import kz.epam.spadv.service.UserService;
import kz.epam.spadv.xml.EventType;
import kz.epam.spadv.xml.EventsType;
import kz.epam.spadv.xml.UserType;
import kz.epam.spadv.xml.UsersType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;

/**
 * Created by Олег on 21.02.2016.
 */
@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST, value = "/uploadUsers")
    public @ResponseBody
    Model uploadUsers(@RequestParam("file") MultipartFile file, Model model){
        String res;
        if(file.isEmpty()){
            res = "Failed to upload file "+file.getOriginalFilename()+", because the file is empty";
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(UsersType.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                UsersType usersType = (UsersType) unmarshaller.unmarshal(file.getInputStream());
                for (UserType userType: usersType.getUser()) {
                    User user = new User();
                    user.setName(userType.getName());
                    user.setEmail(userType.getEmail());
                    user.setBirthday(userType.getBirthday().toGregorianCalendar().toZonedDateTime().toLocalDate());
                    user.setPassword(userType.getPassword());
                    user.setRoles(new ArrayList<>());
                    for (String roleName : userType.getRoles().getRole()) {
                        Role role = new Role();
                        role.setName(roleName);
                        user.getRoles().add(role);
                    }
                    userService.register(user);
                }
                res = "File successfully upload";
            } catch (Exception e) {
                res = "Failed to upload file " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        }
        model.addAttribute("msg", res);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadEvents")
    public Model uploadEvents(@RequestParam("file") MultipartFile file, Model model){
        String res;
        if(file.isEmpty()){
            res = "Failed to upload file "+file.getOriginalFilename()+", because the file is empty";
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(EventsType.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                EventsType eventsType = (EventsType) unmarshaller.unmarshal(file.getInputStream());
                for (EventType eventType: eventsType.getEvent()) {
                    eventService.create(eventType.getName(), eventType.getDateTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
                            eventType.getTicketPrice(), Rating.valueOf(eventType.getRating()));
                }
                res = "File successfully upload";
            } catch (Exception e) {
                res = "Failed to upload file " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        }
        model.addAttribute("msg", res);
        return model;
    }
}
