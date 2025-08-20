package dev.blynchik.magicRangers.model.storage;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    // лучше на каждый сервис сделать свою роль
    // например, при регистрации USER, при создании персонажа PLAYER и т.д.
    // менять при необходимости, например, при удалении персонажа удалять PLAYER
    // позволит избежать лишних проверок и костылей
    USER,
    GUEST;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
