package com.daesoo.war3api.mrpg.repository;

import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import com.daesoo.war3api.mrpg.model.entity.ShareComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareCommentRepository extends JpaRepository<ShareComment, Long> {
    List<ShareComment> findAllByItemSettingShareOrderByRegDtDesc(ItemSettingShare itemSettingShare);
}
