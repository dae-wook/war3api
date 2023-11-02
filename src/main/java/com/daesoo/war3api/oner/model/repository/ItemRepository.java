package com.daesoo.war3api.oner.model.repository;

import com.daesoo.war3api.oner.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {



}
