package kz.epam.spadv.ws;

import kz.epam.spadv.domain.User;
import kz.epam.spadv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by olegm on 16.03.2016.
 */
@Endpoint
public class UserEndPoint {
    private static final String NAMESPACE_URI = "satask";

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "user")
    @ResponsePayload
    public User getUserById(@RequestPayload Long userId){
        return userService.getById(userId);
    }
}
