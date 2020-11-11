package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.persistance.UserRepository;
import com.bookstore.bookstore.transfer.user.CreateUserRequest;
import com.bookstore.bookstore.transfer.user.GetUsersRequest;
import com.bookstore.bookstore.transfer.user.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {
        LOGGER.info("Creating user: {}", request);
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    public User getUser(long id) {
        LOGGER.info("Retrieving user: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not exist."));
    }

    public Page<User> getUsers(GetUsersRequest request, Pageable pageable) {
        LOGGER.info("Retrieving users: {}", request);

        return userRepository.findByOptionalCriteria(request.getPartialFirstName(), request.getPartialLastName(), pageable);
    }

    public User updateUser(long id, UpdateUserRequest request) {
        LOGGER.info("Updating user {}: {}", id, request);

        User existingUser = getUser(id);

        BeanUtils.copyProperties(request, existingUser);

        return userRepository.save(existingUser);
    }

    public void deleteUser(long id) {
        LOGGER.info("Deleting user {}", id);

        userRepository.deleteById(id);
    }
}
