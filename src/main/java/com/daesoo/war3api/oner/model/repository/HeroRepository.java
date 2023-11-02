package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, String> {


}
