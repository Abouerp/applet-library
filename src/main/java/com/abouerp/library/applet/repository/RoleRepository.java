package com.abouerp.library.applet.repository;

import com.abouerp.library.applet.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findFirstByIsDefault(Boolean isDefault);
}
