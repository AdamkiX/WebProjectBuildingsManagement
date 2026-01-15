package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tab.buildings.entity.Manager;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_login")
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id_user", nullable = false, length = 11)
    private int id_user;

    /**
     * Retrieves the user's ID.
     *
     * @return The user's ID.
     */
    public int getId() {
        return id_user;
    }

    /**
     * Sets the user's ID.
     *
     * @param id The user's ID to set.
     */
    public void setId(int id) {
        this.id_user = id;
    }

    /**
     * Username of the user.
     */
    @Basic
    @Column(name = "username", nullable = false, length = 64)
    private String username;

    /**
     * Retrieves the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Password of the user.
     */
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Retrieves the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Type of the user (e.g., "tenant" or "manager").
     */
    @Basic
    @Column(name = "user_type", nullable = false)
    String userType;

    /**
     * Retrieves the type of the user.
     *
     * @return The user type.
     */
    public String getUsertype() {
        return userType;
    }

    /**
     * Sets the type of the user.
     *
     * @param type The user type to set.
     */
    public void setUsertype(String type) {
        this.userType = type;
    }

    /**
     * Tenant user ID, applicable only if the user type is "tenant".
     */
    @Basic
    @Column(name = "tenant_user_id", nullable = true, length = 11)
    Integer tenantUserId;

    /**
     * Retrieves the tenant user ID.
     *
     * @return The tenant user ID.
     */
    public Integer getTenantUserId() {
        return tenantUserId;
    }

    /**
     * Sets the tenant user ID if the user type is "tenant".
     *
     * @param tenantUserId The tenant user ID to set.
     * @throws Exception If the user type is not "tenant".
     */
    public void setTenantUserId(Integer tenantUserId) throws Exception {
        if ("tenant".equals(userType)) {
            this.tenantUserId = tenantUserId;
        } else {
            throw new Exception("Cannot set tenant user ID for non-tenant user.");
        }
    }

    /**
     * Manager user ID, applicable only if the user type is "manager".
     */
    @Basic
    @Column(name = "manager_user_id", nullable = true, length = 11)
    Integer managerUserId;

    /**
     * Retrieves the manager user ID.
     *
     * @return The manager user ID.
     */
    public Integer getManagerUserId() {
        return managerUserId;
    }

    /**
     * Sets the manager user ID if the user type is "manager".
     *
     * @param managerUserId The manager user ID to set.
     * @throws Exception If the user type is not "manager".
     */
    public void setManagerUserId(Integer managerUserId) throws Exception {
        if ("manager".equals(userType)) {
            this.managerUserId = managerUserId;
        } else {
            throw new Exception("Cannot set manager user ID for non-manager user.");
        }
    }

    /**
     * Indicates whether the user's account is non-expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is non-locked.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are non-expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of authorities (user types).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(getUsertype()));
        return list;
    }
}