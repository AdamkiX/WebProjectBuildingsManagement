
// Below is the code for the UserServiceImpl.java file.
package com.tab.buildings.service;

import com.tab.buildings.entity.User;
import com.tab.buildings.rep.ManagerRepository;
import com.tab.buildings.rep.TenantRepository;
import com.tab.buildings.rep.UserRepository;
import com.tab.buildings.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TenantRepository tenantRepository;

    // save operation
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    // update operation
    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        User depDB = userRepository.findById(userId).get();

        if (Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())) {
            depDB.setUsername(user.getUsername());
        }

        if (Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
            depDB.setPassword(user.getPassword());
        }

        if (Objects.nonNull(user.getUsertype()) && !"".equalsIgnoreCase(user.getUsertype())) {
            depDB.setUsertype(user.getUsertype());
        }


        return userRepository.save(depDB);
    }

    // delete operation
    @Override
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Deprecated
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
