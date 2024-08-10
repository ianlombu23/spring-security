package com.woyo.springsecurity.repository;

import com.woyo.springsecurity.entity.RoleAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAuthoritiesRepository extends JpaRepository<RoleAuthorities, String> {
}
