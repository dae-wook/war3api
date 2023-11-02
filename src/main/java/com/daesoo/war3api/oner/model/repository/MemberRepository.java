package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User, String> {


}
