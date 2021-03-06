package com.abouerp.library.applet.security;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.exception.UnauthorizedException;
import com.abouerp.library.applet.mapper.AdministratorMapper;
import com.abouerp.library.applet.repository.AdministratorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;


    public UserDetailsServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        Administrator administrator = administratorRepository.findByMobile(s).orElse(null);
        if (administrator == null) {
            throw new UnauthorizedException();
        }
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
