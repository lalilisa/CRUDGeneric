package com.trimv.crudgeneric.repository;

import com.trimv.crudgeneric.domain.DynamicApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<DynamicApi, Long> {
}
