package com.learning.api.services;

import com.learning.api.dtos.UserDto;
import com.learning.api.exceptions.ObjectNotFoundException;
import com.learning.api.models.User;
import com.learning.api.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public User findById(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User create(UserDto userDto){
        return userRepository.save(mapper.map(userDto, User.class));
    }
}