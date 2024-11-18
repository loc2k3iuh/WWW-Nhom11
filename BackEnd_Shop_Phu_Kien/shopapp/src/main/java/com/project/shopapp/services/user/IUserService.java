package com.project.shopapp.services.user;

import com.project.shopapp.dtos.requests.UserDTO;
import com.project.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;

}
