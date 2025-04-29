package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.repo.AppUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AppUserService {

    private final AppUserRepo appUserRepo;

    @Autowired
    public AppUserService(AppUserRepo appUserRespo) {
        this.appUserRepo = appUserRespo;
    }

    @Transactional
    public AppUser saveAppUserIfNotExist(String oauth2Provider, String oauth2Sub, String email) {
        log.info("Save appUser if not exist email: {}", email);
        return this.findByEmail(email).orElseGet(
                () -> this.save(new AppUser(oauth2Provider, oauth2Sub, email, LocalDateTime.now())));
    }

    private Optional<AppUser> findByEmail(String email) {
        Optional<AppUser> mbAppUser = appUserRepo.findByEmailIgnoreCase(email);
        log.info("AppUser by email: {} is found: {}", email, mbAppUser.isPresent());
        return mbAppUser;
    }

    private Optional<AppUser> findByOauth2ProviderAndOauth2Sub(String oauth2Provider, String oauth2Sub) {
        Optional<AppUser> mbAppUser = appUserRepo.findByOauth2ProviderAndOauth2Sub(oauth2Provider, oauth2Sub);
        log.info("OAuth2 profile by provider: {}, sub: {} is found: {}", oauth2Provider, oauth2Sub, mbAppUser.isPresent());
        return mbAppUser;
    }

    @Transactional
    private AppUser save(AppUser appUser) {
        log.info("Save appUser: {}", appUser);
        return appUserRepo.save(appUser);
    }
}
