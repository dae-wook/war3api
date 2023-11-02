package com.daesoo.war3api.mrpg.repository;

import com.daesoo.war3api.mrpg.model.entity.ItemSetting;
import com.daesoo.war3api.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSettingRepository extends JpaRepository<ItemSetting, Long> {

    List<ItemSetting> findAllByUserOrderByRegDtDesc(User user);

    Page<ItemSetting> findAllByChecksIsNotNullOrChecksIsNotContainingIgnoreCase(String checks, Pageable pageable);

    Page<ItemSetting> findAllByCharacterNameAndChecksIsNotNullAndChecksIsNotContainingIgnoreCase(String character, String checks, Pageable pageable);

    Long countByCharacterNameAndChecksIsNotNullAndChecksIsNotContainingIgnoreCase(String character, String checks);
}
