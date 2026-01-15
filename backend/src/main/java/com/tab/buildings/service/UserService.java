package com.tab.buildings.service;

import com.tab.buildings.entity.User;
import com.tab.buildings.security.ReqRes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    // save operation
    User saveUser(User user);

    // find by username
    Optional<User> findByUsername(String username);

    // update operation
    User updateUser(User user, Integer userId) throws Exception;

    // delete operation
    void deleteUserById(Integer userId);

}
