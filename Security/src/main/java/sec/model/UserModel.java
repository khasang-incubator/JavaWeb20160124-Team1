package sec.model;

/**
 * Created by MainW8 on 28.02.2016.
 */
public class UserModel {
    String username;
    String password;
    int valid;

    public UserModel(String username,String password, int valid) {
        this.password = password;
        this.valid = valid;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getValid() {
        return valid;
    }
}
