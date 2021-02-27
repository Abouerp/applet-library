package com.abouerp.library.applet.mapper;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.domain.Authority;
import com.abouerp.library.applet.domain.Role;
import com.abouerp.library.applet.dto.AdministratorDTO;
import com.abouerp.library.applet.security.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministratorMapper {

    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    AdministratorDTO toDTO(Administrator administrator);

    default UserPrincipal toUserPrincipal(Administrator administrator) {
        List<SimpleGrantedAuthority> authorities = administrator.getRoles()
                .parallelStream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(Authority::springAuthority)
                .collect(Collectors.toList());
        return new UserPrincipal(
                administrator.getId(),
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getEnabled(),
                administrator.getAccountNonExpired(),
                administrator.getCredentialsNonExpired(),
                administrator.getAccountNonLocked(),
                authorities
        );
    }
}
