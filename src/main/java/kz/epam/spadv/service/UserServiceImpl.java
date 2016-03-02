package kz.epam.spadv.service;

import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.UserRepository;
import kz.epam.spadv.repository.WinsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger log = Logger.getLogger(UserService.class);

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WinsRepository winsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {

    }

    @Override
    public User create(String name, String email, LocalDate birthDay) {
        return new User(name, email, birthDay);
    }

    @Override
    public User register(User user) {
        log.info("User <" + user.getName() + "> is registered");
        user = userRepository.save(user);
        return user;
    }

    @Override
    public void remove(User user) {
        Optional.ofNullable(user).ifPresent(u->{
            userRepository.delete(u.getId());
        });
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Collection<Ticket> getBookedTickets() {
        return ticketRepository.getBookedTickets();
    }

    @Override
    public Collection<Ticket> getBookedTicketsByUserId(long userId) {
        return ticketRepository.getBookedTicketsByUserId(userId);
    }

    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName);
        if(user==null){
            throw new UsernameNotFoundException("No user found with username: " + userName);
        }
        return (UserDetails) user;
    }
}
