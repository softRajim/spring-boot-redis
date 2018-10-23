package com.rajim.redis.repo;

import com.rajim.redis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author rajim on 10/23/18
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.id=:userId")
    User findByUserId(@Param("userId") Long userId);
}
