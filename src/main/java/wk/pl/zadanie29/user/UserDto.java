package wk.pl.zadanie29.user;

public class UserDto {

    private Long id;
    private String username;
    private String fullname;
    private boolean admin;

    public UserDto(Long id, String username, String fullname, boolean admin) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
