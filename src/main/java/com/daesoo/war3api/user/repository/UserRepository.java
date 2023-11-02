package com.daesoo.war3api.user.repository;

import com.daesoo.war3api.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findBySiteNick(String name);

    void deleteBySiteNick(String siteNick);
}
