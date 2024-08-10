package com.woyo.springsecurity.repository;

import com.woyo.springsecurity.entity.UserRole;
import com.woyo.springsecurity.entity.projection.UserAuthoritiesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    @Query(value =
            "SELECT ur.userId AS userId, a.authoritiesName AS authoritiesName " +
                    "FROM UserRole ur " +
                    "INNER JOIN RoleAuthorities ra ON ur.roleId = ra.roleId " +
                    "INNER JOIN A" +
                    "uthorities a ON ra.authoritiesId = a.authoritiesId " +
                    "WHERE ur.userId = :userId"
    )
    List<UserAuthoritiesProjection> findUserAuthorities(String userId);
}
