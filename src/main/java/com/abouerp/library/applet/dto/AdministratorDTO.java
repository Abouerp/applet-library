package com.abouerp.library.applet.dto;

import com.abouerp.library.applet.domain.Role;
import lombok.Data;

import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class AdministratorDTO {

    private Integer id;
    private String username;
    private String mobile;
    private String email;
    private String sex;
    private String md5;
    private Set<Role> roles;
    private String description;
    private String nickName;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
