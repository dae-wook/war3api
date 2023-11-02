package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
