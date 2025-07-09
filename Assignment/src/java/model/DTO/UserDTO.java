package model.DTO;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class UserDTO {

    private String userId;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDate createdAt;

    public UserDTO() {
    }

    public UserDTO(String userId, String userName, String name, String email, String password, String role, LocalDate createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UserDTO(String userName, String name, String email, String password, String role, LocalDate createdAt) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UserDTO(String userName, String name, String email, String password, LocalDate createdAt) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

}
