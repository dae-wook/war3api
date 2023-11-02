package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Build;
import com.daesoo.war3api.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build, Long> {
    Page<Build> findAllByCharacterName(String character, Pageable pageable);

    Page<Build> findAllByUser(User user, Pageable pageable);
    Page<Build> findAllByCharacterNameAndUser(String character, User user, Pageable pageable);
}
