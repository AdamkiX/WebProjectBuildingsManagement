package com.tab.buildings.controller;

import com.tab.buildings.entity.User;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for managing users.
 */
@RestController
public class UserController {

    private int statusCode;

    // Annotation
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    /**
     * User's ability to get his own credentials
     * @return
     */
    @GetMapping("/auth/loggedUser")
    public ResponseEntity<Object> getLoggedUser() {
        ReqRes reqRes = authService.getUserData();
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }
    /**
     * Saves a new user.
     * @param user The user information to be saved.
     * @return ResponseEntity with the status and response object.
     */
    // Save operation
    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(
            @Valid @RequestBody User user)
    {
        return ResponseEntity.ok(userService.saveUser(user));
    }


    /**
     * Updates an existing user's information.
     * @param user The updated user information.
     * @param userId The ID of the user to be updated.
     * @return ResponseEntity with the status and response object.
     * @throws Exception if an error occurs during the process.
     */
    // Update operation
    @PutMapping("/users/{id}")
    public ResponseEntity<Object>
    updateUser(@RequestBody User user, @PathVariable("id") Integer userId) throws Exception {

        return ResponseEntity.ok(userService.updateUser(user, userId));
    }

    /**
     * Deletes a user by their ID.
     * @param userId The ID of the user to be deleted.
     * @return ResponseEntity with a success message.
     */
    // Delete operation
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Integer userId)
    {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
