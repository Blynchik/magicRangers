package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepo appUserRepo;

    @Autowired
    public AppUserService(AppUserRepo appUserRespo) {
        this.appUserRepo = appUserRespo;
    }

    @Transactional
    public AppUser saveAppUserIfNotExist(String oauth2Provider, String oauth2Sub, String email) {
        return appUserRepo.findByOauth2ProviderAndOauth2Sub(oauth2Provider, oauth2Sub)
                .orElse(appUserRepo.save(new AppUser(oauth2Provider, oauth2Sub, email, LocalDateTime.now())));
    }
}
