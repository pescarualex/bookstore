package com.bookstore.bookstore.integrationTest.steps;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.transfer.user.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class UserTestSteps {

    @Autowired
    private UserService userService;

    public User createUser() {
        CreateUserRequest request = new CreateUserRequest();

        request.setFirstName("Alex");
        request.setLastName("Pescaru");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getLastName(), is(request.getLastName()));

        return user;
    }

}
