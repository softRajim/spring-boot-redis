package com.rajim.redis.service;

import com.rajim.redis.domain.User;
import com.rajim.redis.dto.UserDto;
import com.rajim.redis.exception.EntityNotFoundException;

import java.util.Optional;

/**
 * @author rajim on 10/23/18
 */

public interface UserService {
    Optional<User> findOne(Long userId) throws EntityNotFoundException;

    User updateUser(Long userId, UserDto userDto) throws Exception;

    User addNewUser(UserDto userDto);
}
