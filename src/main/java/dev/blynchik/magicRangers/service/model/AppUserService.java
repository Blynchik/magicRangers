package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.repo.AppUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.APPUSER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final AppCharacterService characterService;

    @Autowired
    public AppUserService(AppUserRepo appUserRepo,
                          AppCharacterService characterService) {
        this.appUserRepo = appUserRepo;
        this.characterService = characterService;
    }

    /**
     * Метод проверяет, существует ли пользователь по его id
     */
    public boolean existsById(Long userId) {
        log.info("Check existing user id: {}", userId);
        return appUserRepo.existsById(userId);
    }

    /**
     * Метод удаляет пользователя по id
     */
    @Transactional
    public void delete(Long userId) {
        log.info("Delete user id: {}", userId);
        characterService.getOptByAppUserId(userId)
                .ifPresent(c -> characterService.delete(c.getId()));
        appUserRepo.deleteById(userId);
    }

    /**
     * Ищем пользователя по уникальному email
     * и выбрасываем исключение, если не находим
     */
    public AppUser getByEmail(String email) {
        return this.findOptByEmail(email)
                .orElseThrow(() -> new AppException(APPUSER_NOT_FOUND.formatted(email), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем пользователя по уникальному email.
     * Если такого нет, то сохраняем в БД
     */
    @Transactional
    public AppUser saveAppUserIfNotExist(String oauth2Provider, String oauth2Sub, String email) {
        log.info("Save appUser if not exist email: {}", email);
        return this.findOptByEmail(email).orElseGet(
                () -> this.save(new AppUser(oauth2Provider, oauth2Sub, email)));
    }

    /**
     * Ищем пользователя по уникальному email
     */
    private Optional<AppUser> findOptByEmail(String email) {
        Optional<AppUser> mbAppUser = appUserRepo.findByEmailIgnoreCase(email);
        log.info("AppUser by email: {} is found: {}", email, mbAppUser.isPresent());
        return mbAppUser;
    }

    /**
     * Ищем пользователя по провайдеру OAuth2 и его уникальному идентификатору в среде провайдера
     */
    private Optional<AppUser> findOptByOauth2ProviderAndOauth2Sub(String oauth2Provider, String oauth2Sub) {
        Optional<AppUser> mbAppUser = appUserRepo.findByOauth2ProviderAndOauth2Sub(oauth2Provider, oauth2Sub);
        log.info("OAuth2 profile by provider: {}, sub: {} is found: {}", oauth2Provider, oauth2Sub, mbAppUser.isPresent());
        return mbAppUser;
    }

    /**
     * Транзакционно сохраняем нового пользователя в БД
     */
    @Transactional
    private AppUser save(AppUser appUser) {
        appUser.setUpdatedAt(LocalDateTime.now());
        log.info("Save appUser: {}", appUser);
        return appUserRepo.save(appUser);
    }
}
