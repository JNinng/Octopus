package top.ninng.octopus.entry;

import java.io.Serializable;

/**
 * @Author OhmLaw
 * @Version 1.0
 */
public class Login implements Serializable {

    private String name;
    private String password;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
