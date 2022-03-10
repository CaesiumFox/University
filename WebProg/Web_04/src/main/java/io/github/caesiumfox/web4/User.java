package io.github.caesiumfox.web4;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User", schema = "public")
public class User implements Serializable {
    @Id
    private String username;
    private String passHashStr;
    private String passSaltStr;

    public String getUsername() {
        return username;
    }
    public String getPassHashStr() {
        return passHashStr;
    }
    public String getPassSaltStr() {
        return passSaltStr;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassHashStr(String passHash) {
        this.passHashStr = passHash;
    }
    public void setPassSaltStr(String passSaltStr) {
        this.passSaltStr = passSaltStr;
    }
}
