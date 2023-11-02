package com.daesoo.war3api.mrpg.repository;

import com.daesoo.war3api.mrpg.model.dto.MrpgMaterialMapping;
import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import com.daesoo.war3api.mrpg.model.entity.MrpgMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSettingShareRepository extends JpaRepository<ItemSettingShare, Long> {
    Page<ItemSettingShare> findAllByCharacterName(String characterName, Pageable pageable);
//    Page<ItemSettingShare> findAll(Pageable pageable);

    Long countByCharacterName(String characterName);
}
