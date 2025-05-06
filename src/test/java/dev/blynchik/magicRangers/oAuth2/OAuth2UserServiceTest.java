package dev.blynchik.magicRangers.oAuth2;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.service.auth.OAuth2UserService;
import dev.blynchik.magicRangers.service.model.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

import static dev.blynchik.magicRangers.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OAuth2UserServiceTest {

    @MockitoSpyBean
    private OAuth2UserService oAuth2UserService;
    @Autowired
    private AppUserService appUserService;

    @Test
    void loadUser_whenUserRequestFirstTimeAndSave_success() {
        // given
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(OAUTH2_PROVIDER_GOOGLE);
        OAuth2User mockOAuth2User = new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_GOOGLE_SUB, "email", OAUTH2_EMAIL),
                "sub"
        );
        doReturn(mockOAuth2User)
                .when(oAuth2UserService)
                .loadUserFromProvider(any());
        // when
        OAuth2User result = oAuth2UserService.loadUser(userRequest);
        // then
        assertNotNull(result);
        assertTrue(result instanceof AuthUser);
        AppUser saved = appUserService.getByEmail(OAUTH2_EMAIL);
        assertNotNull(saved.getId());
        assertEquals(OAUTH2_EMAIL, saved.getEmail());
        assertEquals(OAUTH2_PROVIDER_GOOGLE, saved.getOauth2Provider());
        assertEquals(OAUTH2_GOOGLE_SUB, saved.getOauth2Sub());
        assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    }

    @Test
    void loadUser_whenUserExists_returnsExistingUser() {
        // given
        appUserService.saveAppUserIfNotExist(OAUTH2_PROVIDER_GOOGLE, OAUTH2_GOOGLE_SUB, OAUTH2_EMAIL);
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(OAUTH2_PROVIDER_GOOGLE);
        OAuth2User mockOAuth2User = new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_GOOGLE_SUB, "email", OAUTH2_EMAIL),
                "sub"
        );
        doReturn(mockOAuth2User)
                .when(oAuth2UserService)
                .loadUserFromProvider(any());
        // when
        OAuth2User result = oAuth2UserService.loadUser(userRequest);
        // then
        assertNotNull(result);
        assertTrue(result instanceof AuthUser);
        AppUser found = appUserService.getByEmail(OAUTH2_EMAIL);
        assertNotNull(found.getId());
        assertEquals(OAUTH2_EMAIL, found.getEmail());
        assertEquals(OAUTH2_PROVIDER_GOOGLE, found.getOauth2Provider());
        assertEquals(OAUTH2_GOOGLE_SUB, found.getOauth2Sub());
        assertNotNull(found.getCreatedAt());
        assertNotNull(found.getUpdatedAt());
    }

    @Test
    void loadUser_whenProviderUnknown_throwsException() {
        // given
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(OAUTH2_PROVIDER_UNKNOWN);
        OAuth2User mockOAuth2User = new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_GOOGLE_SUB, "email", OAUTH2_EMAIL),
                "sub"
        );
        doReturn(mockOAuth2User).when(oAuth2UserService).loadUserFromProvider(any());
        // when-then
        assertThrows(OAuth2AuthenticationException.class, () ->
                oAuth2UserService.loadUser(userRequest)
        );
    }

    @Test
    void loadUser_whenSubMissing_throwsException() {
        // given
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(OAUTH2_PROVIDER_GOOGLE);
        OAuth2User mockOAuth2User = new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("email", OAUTH2_EMAIL),
                "email"
        );
        doReturn(mockOAuth2User).when(oAuth2UserService).loadUserFromProvider(any());
        // when-then
        assertThrows(OAuth2AuthenticationException.class, () ->
                oAuth2UserService.loadUser(userRequest)
        );
    }

    @Test
    void loadUser_whenEmailMissing_throwsException() {
        // given
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(OAUTH2_PROVIDER_GOOGLE);
        OAuth2User mockOAuth2User = new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_GOOGLE_SUB),
                "sub"
        );
        doReturn(mockOAuth2User).when(oAuth2UserService).loadUserFromProvider(any());
        // when-then
        assertThrows(OAuth2AuthenticationException.class, () ->
                oAuth2UserService.loadUser(userRequest)
        );
    }
}
