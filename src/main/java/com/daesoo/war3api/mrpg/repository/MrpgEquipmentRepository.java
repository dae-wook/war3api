package com.daesoo.war3api.mrpg.repository;

import com.daesoo.war3api.mrpg.model.entity.MrpgEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MrpgEquipmentRepository extends JpaRepository<MrpgEquipment, Long> {
}
