package io.github.caesiumfox.web4.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
public class User implements Serializable {
    @Id
    private String username;
    private String passHashStr;
    private String passSaltStr;

    public User() {}
    public User(String username,
                String passHashStr,
                String passSaltStr) {
        this.username = username;
        this.passHashStr = passHashStr;
        this.passSaltStr = passSaltStr;
    }

    public String getUsername() {
        return username;
    }

    public String getPassHashStr() {
        return passHashStr;
    }

    public String getPassSaltStr() {
        return passSaltStr;
    }
}
