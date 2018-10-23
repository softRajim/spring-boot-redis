package com.rajim.redis.controller;

import com.rajim.redis.domain.User;
import com.rajim.redis.dto.UserDto;
import com.rajim.redis.exception.EntityNotFoundException;
import com.rajim.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author rajim on 10/23/18
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Cacheable(value = "users")
    @GetMapping(value = "")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable Long userId) throws EntityNotFoundException {
        return userService.findOne(userId);
    }


    @CachePut(value = "users", key = "#user.id")
    @PutMapping("/{userId}/update")
    public User updatePersonByID(@PathVariable Long userId,
                                 @RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userId, userDto);
    }

    @PostMapping("")
    public User addUser(@RequestBody UserDto userDto) {
        return userService.addNewUser(userDto);
    }

    @CacheEvict(value = "users", allEntries=true)
    @DeleteMapping("/{id}")
    public void deleteUserByID(@PathVariable Long id) {
        userService.delete(id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @PutMapping("/clear-cash")
    public void clearCache() {
        userService.clearCache();
    }

}
