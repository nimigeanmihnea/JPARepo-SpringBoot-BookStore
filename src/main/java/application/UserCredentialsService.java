package application;

import application.entity.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Service
public class UserCredentialsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public UserCredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = this.userRepository.findByUsername(username);
            user.isEnabled();
            return new User(user.getUsername(), user.getPassword(), user.getRole());
        }catch (UsernameNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
