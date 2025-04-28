package com.robson.desafiopicpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.Role;
import com.robson.desafiopicpay.entities.enums.RoleName;


public interface RoleRepository extends JpaRepository<Role,Long>{
    Optional<Role> findByAuthority(RoleName authority);
}
