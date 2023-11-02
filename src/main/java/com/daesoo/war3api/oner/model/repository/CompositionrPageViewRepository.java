package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.CompositionPageView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompositionrPageViewRepository extends JpaRepository<CompositionPageView, String> {

    Optional<CompositionPageView> findByNameAndUpdateDt(String name, LocalDate updateDt);
    // startDate <= compareWithStartDate
    // endDate >= compareWithEndDate
    List<CompositionPageView> findAllByUpdateDtBetween(LocalDate startDate, LocalDate endDate);
    Optional<CompositionPageView> findByName(String name);

}
