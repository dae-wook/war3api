package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.CharacterPageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface CharacterPageViewRepository extends JpaRepository<CharacterPageView, Long> {

    Optional<CharacterPageView> findByNameAndUpdateDt(String name, LocalDate updateDt);
    // startDate <= compareWithStartDate
    // endDate >= compareWithEndDate
    List<CharacterPageView> findAllByUpdateDtBetween(LocalDate startDate, LocalDate endDate);
    //SELECT name, SUM(page_view) AS total_count
    //FROM character_page_view
    //GROUP BY name;
    @Query(value = "SELECT id, name, SUM(page_view) AS page_view, update_dt FROM character_page_view WHERE update_dt BETWEEN :startDate AND :endDate group by name order by page_view desc", nativeQuery = true)
    List<CharacterPageView> findAllByUpdateDtBetweenQuery(LocalDate startDate, LocalDate endDate);
    Optional<CharacterPageView> findByName(String name);

}
