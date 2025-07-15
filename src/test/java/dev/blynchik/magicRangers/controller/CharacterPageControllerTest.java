package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.response.AppCharacterResponse;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.service.model.CharacterService;
import dev.blynchik.magicRangers.util.ValidationUIErrorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.CHARACTER;
import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.MY;
import static dev.blynchik.magicRangers.util.TestData.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CharacterPageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CharacterService characterService;
    @MockitoBean
    private ValidationUIErrorUtil validationUIErrorUtil;
    private AuthUser authUser;
    private AuthUser notAuthUser;

    @BeforeEach
    void setup() {
        authUser = new AuthUser(new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_GOOGLE_SUB, "email", OAUTH2_EMAIL), "sub"
        ),
                new AppUser(OAUTH2_PROVIDER_GOOGLE, OAUTH2_GOOGLE_SUB, OAUTH2_EMAIL, LocalDateTime.now()));
        notAuthUser = new AuthUser(new DefaultOAuth2User(
                Collections.emptySet(),
                Map.of("sub", OAUTH2_PROVIDER_UNKNOWN, "email", OAUTH2_EMAIL), "sub"
        ),
                new AppUser(OAUTH2_PROVIDER_UNKNOWN, OAUTH2_GOOGLE_SUB, OAUTH2_EMAIL, LocalDateTime.now()));
    }

    @Test
    void createCharacter_whenRequestToCreate_successWithRedirect() throws Exception {
        AppCharacterResponse response = new AppCharacterResponse("Test", 100, 100, 100, LocalDateTime.now());
        when(characterService.existsByAppUserId(1L)).thenReturn(false);
        when(characterService.createWithDto(anyLong(), any())).thenReturn(response);
        when(validationUIErrorUtil.hasValidationErrors(any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post(CHARACTER)
                        .param("name", response.getName())
                        .param("str", response.getStr().toString())
                        .param("intl", response.getIntl().toString())
                        .param("cha", response.getCha().toString())
                        .with(oauth2Login().oauth2User(authUser)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + CHARACTER + MY));
    }

    @Test
    void createCharacter_whenRequestToCreate_NotAuthUser() throws Exception {
        AppCharacterResponse response = new AppCharacterResponse("Test", 100, 100, 100, LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders.post(CHARACTER)
                        .param("name", response.getName())
                        .param("str", response.getStr().toString())
                        .param("intl", response.getIntl().toString())
                        .param("cha", response.getCha().toString())
                        .with(oauth2Login().oauth2User(notAuthUser)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + CHARACTER + MY));
    }

    @Test
    void createCharacter_getCharacterPage_shouldDisplayCharacterDetails() throws Exception {
        AppCharacterResponse response = new AppCharacterResponse("Test", 100, 100, 100, LocalDateTime.now());
        when(characterService.getDtoByAppUserId(anyLong())).thenReturn(response);
        authUser.getAppUser().setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.get(CHARACTER + MY)
                        .with(oauth2Login().oauth2User(authUser)))
                .andExpect(status().isOk())
                .andExpect(view().name("character/view"))
                .andExpect(model().attributeExists("character"))
                .andExpect(model().attribute("character", hasProperty("name", is(response.getName()))))
                .andExpect(model().attribute("character", hasProperty("str", is(response.getStr()))))
                .andExpect(model().attribute("character", hasProperty("intl", is(response.getIntl()))))
                .andExpect(model().attribute("character", hasProperty("cha", is(response.getCha()))));
    }
}
