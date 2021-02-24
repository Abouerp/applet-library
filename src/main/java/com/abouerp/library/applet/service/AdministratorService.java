package com.abouerp.library.applet.service;

import com.abouerp.library.applet.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }
}
