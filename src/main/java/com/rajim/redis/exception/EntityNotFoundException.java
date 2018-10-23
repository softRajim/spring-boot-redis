package com.rajim.redis.exception;

import org.springframework.util.StringUtils;

/**
 * @author rajim on 10/23/18
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Class clazz) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName()));
    }

    private static String generateMessage(String entity) {
        return StringUtils.capitalize(entity) +
                " does not found ";
    }
}

