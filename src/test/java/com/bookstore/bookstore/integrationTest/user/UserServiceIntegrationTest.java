package com.bookstore.bookstore.integrationTest.user;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.integrationTest.steps.UserTestSteps;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.transfer.user.CreateUserRequest;
import com.bookstore.bookstore.transfer.user.UpdateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void createUser_whenValidRequest_thenReturnSavedUser() {
        userTestSteps.createUser();
    }

    @Test
    public void createUser_whenMissingFirstName_thenThrowException() {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName(null);
        request.setLastName("Pescaru");

        Exception exception = null;

        try {
            User user = userService.createUser(request);
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, notNullValue());
        assertThat("Unexpected exception type.", exception instanceof TransactionSystemException);
    }


    @Test
    public void getUser_whenExistingUser_thanReturnUser() {
        User createdUser = userTestSteps.createUser();

        User userResponse = userService.getUser(createdUser.getId());

        assertThat(userResponse, notNullValue());
        assertThat(userResponse.getId(), is(createdUser.getId()));
        assertThat(userResponse.getFirstName(), is(createdUser.getFirstName()));
        assertThat(userResponse.getLastName(), is(createdUser.getLastName()));

    }

    @Test
    public void getUser_whenNonExistingUser_thanThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(10000));

    }

    @Test
    public void updateUser_whenExistingUser_thanReturnUpdatedUser() {
        User user = userTestSteps.createUser();

        UpdateUserRequest request = new UpdateUserRequest();
        request.setFirstName(user.getFirstName() + "Updated");
        request.setLastName(user.getLastName() + "Updated");

        User updatedUser = userService.updateUser(user.getId(), request);

        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getFirstName(), is(request.getFirstName()));
        assertThat(updatedUser.getLastName(), is(request.getLastName()));
    }

    @Test
    public void updateUser_whenNonExistingUser_thanThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(10000, null));
    }

    @Test
    public void deleteUser_whenExistingUser_thanTheUserIsDeleting() {
        User createdUser = userTestSteps.createUser();

        userService.deleteUser(createdUser.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(createdUser.getId()));
    }



}
