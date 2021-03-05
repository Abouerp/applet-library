package com.abouerp.library.applet.config;

import com.abouerp.library.applet.utils.UserUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Abouerp
 */
public class UserAuditorConfig implements AuditorAware<String> {


//    public Optional<Integer> getCurrentAuditor() {
//        return UserUtils.getCurrentAuditor();
//    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return UserUtils.getCurrentAuditorUsername();
    }

}
