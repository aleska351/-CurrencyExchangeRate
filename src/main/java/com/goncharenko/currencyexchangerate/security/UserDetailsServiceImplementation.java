package com.goncharenko.currencyexchangerate.security;

import com.goncharenko.currencyexchangerate.dao.RoleRepository;
import com.goncharenko.currencyexchangerate.dao.UserRepository;
import com.goncharenko.currencyexchangerate.domain.Role;
import com.goncharenko.currencyexchangerate.domain.User;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Role> roles = roleRepository.getRoles(user.getId());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
        return userDetails;
    }
}
