package io.github.caesiumfox.web4.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
public class AuthToken implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String token;
    
    @Column(updatable = false, nullable = false)
    private String username;

    public AuthToken() {}
    public AuthToken(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
