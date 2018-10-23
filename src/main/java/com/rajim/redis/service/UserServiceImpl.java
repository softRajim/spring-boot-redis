package com.rajim.redis.service;

import com.rajim.redis.domain.User;
import com.rajim.redis.dto.UserDto;
import com.rajim.redis.exception.EntityNotFoundException;
import com.rajim.redis.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author rajim on 10/23/18
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findOne(Long userId) throws EntityNotFoundException {
        log.info("Getting user with ID {}.", userId);
        Optional<User> user =  userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(User.class);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, UserDto userDto) throws Exception {
        log.info("Updating user with ID {}.", userId);
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new EntityNotFoundException(User.class);
        }
        user.setName(userDto.getName());
        user.setFollowers(userDto.getFollowers());
        return userRepository.save(user);
    }

    @Override
    public User addNewUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setFollowers(userDto.getFollowers());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id {}.", id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public void clearCache() {
        log.info("All list maintained cache is being cleared");
    }
}
