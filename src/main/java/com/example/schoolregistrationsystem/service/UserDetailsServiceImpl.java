package com.example.schoolregistrationsystem.service;

import com.example.schoolregistrationsystem.exception.ApplicationException;
import com.example.schoolregistrationsystem.model.dto.ExceptionDto;
import com.example.schoolregistrationsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username).orElseThrow(() -> new ApplicationException(ExceptionDto.USERNAME_NOT_FOUND));
    }
}
