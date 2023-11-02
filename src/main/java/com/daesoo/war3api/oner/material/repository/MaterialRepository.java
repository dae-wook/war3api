package com.daesoo.war3api.oner.material.repository;

import com.daesoo.war3api.oner.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, String> {
}
