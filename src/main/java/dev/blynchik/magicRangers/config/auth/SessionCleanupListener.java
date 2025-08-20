package dev.blynchik.magicRangers.config.auth;

import dev.blynchik.magicRangers.model.auth.GuestUser;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupListener implements ApplicationListener<SessionDestroyedEvent> {

    private final AppCharacterService characterService;
    private final AppUserService appUserService;

    @Autowired
    public SessionCleanupListener(AppCharacterService characterService, AppUserService appUserService) {
        this.characterService = characterService;
        this.appUserService = appUserService;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        event.getSecurityContexts().forEach(ctx -> {
            var auth = ctx.getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof GuestUser guestUser) {
                Long guestId = guestUser.getId();
                characterService.deleteByAppUserId(guestId);
                appUserService.delete(guestId);
            }
        });
    }
}
