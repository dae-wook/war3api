package com.daesoo.war3api.mrpg.repository;

import com.daesoo.war3api.mrpg.model.dto.MrpgMaterialMapping;
import com.daesoo.war3api.mrpg.model.entity.MrpgMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MrpgMaterialRepository extends JpaRepository<MrpgMaterial, Long> {
    List<MrpgMaterialMapping> findAllBy();
}
