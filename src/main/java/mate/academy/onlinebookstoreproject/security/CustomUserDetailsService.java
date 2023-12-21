package mate.academy.onlinebookstoreproject.security;

import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String CANNOT_FIND_USER_BY_EMAIL_MSG = "Cannot find user by email: ";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(CANNOT_FIND_USER_BY_EMAIL_MSG + email));
    }
}
