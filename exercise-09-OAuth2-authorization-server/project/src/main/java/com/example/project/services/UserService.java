package com.example.project.services;

import com.example.project.model.SecurityUser;
import com.example.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         var user=userRepository.findByUsername(username);
        return user.map(SecurityUser::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
