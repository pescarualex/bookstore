package com.bookstore.bookstore.web;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.transfer.user.CreateUserRequest;
import com.bookstore.bookstore.transfer.user.GetUsersRequest;
import com.bookstore.bookstore.transfer.user.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<User>> getUsers(GetUsersRequest request, Pageable pageable) {
        Page<User> users = userService.getUsers(request, pageable);

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody @Valid UpdateUserRequest request) {
        User user = userService.updateUser(id, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
