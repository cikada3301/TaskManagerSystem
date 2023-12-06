package com.example.taskmanagersystemapi.service.impl;

import com.example.taskmanagersystemapi.dto.UserRegistrationDto;
import com.example.taskmanagersystemapi.exception.UserExistException;
import com.example.taskmanagersystemapi.mapper.UserMapper;
import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.repository.UserRepository;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return new JwtUser(user);
    }

    @Override
    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) {

        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            throw new UserExistException("User with this email already exist");
        }

        userRegistrationDto.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDto.getPassword()));
        userRepository.save(UserMapper.INSTANCE.userRegistrationDtoToUser(userRegistrationDto));
    }
}
