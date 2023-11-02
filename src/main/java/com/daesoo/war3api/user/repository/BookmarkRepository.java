package com.daesoo.war3api.user.repository;

import com.daesoo.war3api.user.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findBySiteNickAndTargetAndCategory(String siteNick, String target, String category);
    List<Bookmark> findAllBySiteNickAndCategory(String siteNick, String category);
}
