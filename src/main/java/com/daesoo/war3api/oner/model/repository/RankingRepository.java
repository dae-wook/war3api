package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, String> {

    Optional<Ranking> findByNickNameAndName(String nickName, String name);

    Page<Ranking> findAllByHeroImageNameOrderByBountyDesc(String character, Pageable pageable);


    Page<Ranking> findAllByName(String name, Pageable pageable);

    Page<Ranking> findAll(Pageable pageable);

    Page<Ranking> findAllByOrderByBountyDesc(Pageable pageable);
}
