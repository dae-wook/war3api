package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, String> {
}
