package com.qci.onsite.pojo;

/**
 * Created by Ankit on 22-11-2018.
 */

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String firstName;
    private String lastname;
    private String userId;
    private String email;
    private String roleName;
    private String roleId;
    private Boolean isActive;
    private String message;

    public LoginAssessorResponsePojo getAssessor() {
        return assessor;
    }

    public void setAssessor(LoginAssessorResponsePojo assessor) {
        this.assessor = assessor;
    }

    private LoginAssessorResponsePojo assessor;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
