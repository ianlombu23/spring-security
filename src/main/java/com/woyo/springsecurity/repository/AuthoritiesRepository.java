package com.woyo.springsecurity.repository;

import com.woyo.springsecurity.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {
}
