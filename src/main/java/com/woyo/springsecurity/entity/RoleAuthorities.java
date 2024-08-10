package com.woyo.springsecurity.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_authorities")
public class RoleAuthorities {
    @Id
    @UuidGenerator
    @Column(name = "role_authorities_id")
    private String roleAuthoritiesId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "authorities_id")
    private String authoritiesId;
}
