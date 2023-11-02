package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, String> {

    Optional<Status> findByType(String type);

}
