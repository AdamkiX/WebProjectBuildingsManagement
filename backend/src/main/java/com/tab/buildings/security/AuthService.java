package com.tab.buildings.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import com.tab.buildings.entity.Building;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.Tenant;
import com.tab.buildings.entity.User;
import com.tab.buildings.rep.ManagerRepository;
import com.tab.buildings.rep.TenantRepository;
import com.tab.buildings.rep.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private TenantRepository tenantRepository;


    /**
     * Sign in a user
     *
     * @param signinRequest - Request body containing username and password
     * @return ReqRes object containing the user data and status code
     */
    public ReqRes signIn(ReqRes signinRequest) {
        Date date = new Date();
        ReqRes response = new ReqRes();
        Optional<Manager> managerEntity;
        Optional<Tenant> tenantEntity;
        Optional<List<Building>> buildingsEntity;
        try {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
            var user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow();
            var manager = managerRepository;
            var tenant = tenantRepository;
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            if (user.getUsertype().equals("manager")) {
                managerEntity = manager.findById(user.getManagerUserId());
                response.setManager(managerEntity.orElse(null));
            }
            if (user.getUsertype().equals("tenant")) {
                tenantEntity = tenant.findById(user.getTenantUserId());
                response.setTenant(tenantEntity.orElse(null));
            }
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            date.setTime(System.currentTimeMillis() + 86400000);
            response.setExpirationTime(date.toString());
            response.setMessage("Successfully Signed In");
        } catch (NoSuchElementException e) {
            response.setStatusCode(418);
            response.setMessage("User not found");
        } catch (AuthenticationException e) {
            response.setStatusCode(401);
            response.setMessage("Invalid Credentials");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Refresh the token
     *
     * @param refreshTokenRequest - Request body containing the refresh token
     * @return ReqRes object containing the new token and status code
     */
    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        try {
            String username = jwtUtils.extractUsername(refreshTokenRequest.getRefreshToken());
            User user = userRepository.findByUsername(username).orElseThrow();

            if (jwtUtils.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getRefreshToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            } else {
                response.setStatusCode(403);
            }
        } catch (JwtException e) {
            response.setStatusCode(403);
            return response;
        }
        return response;
    }

    /**
     * Get the user data
     *
     * @return ReqRes object containing the user data and status code
     */
    public ReqRes getUserData() {
        ReqRes response = new ReqRes();
        Optional<Manager> managerEntity;
        Optional<Tenant> tenantEntity;

        try {
            User user = getUser().orElseThrow();
            if (user.getUsertype().equals("manager")) {
                managerEntity = managerRepository.findById(user.getManagerUserId());
                response.setManager(managerEntity.orElse(null));
                response.setUserType("manager");
            } else if (user.getUsertype().equals("tenant")) {
                tenantEntity = tenantRepository.findById(user.getTenantUserId());
                response.setTenant(tenantEntity.orElse(null));
                response.setUserType("tenant");
            }
            response.setStatusCode(200);
        } catch (NoSuchElementException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
//        System.out.println(response);
        return response;
    }

    /**
     * Get the user from the security context
     *
     * @return User
     */
    public Optional<User> getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return userRepository.findByUsername(username);
    }

    /**
     * Get the user from the security context
     *
     * @return User
     */
    @Deprecated
    public Integer getLoggedUser() {
        User user = getUser().orElse(null);
        if(user.getManagerUserId() != null) {
            return user.getManagerUserId();
        }
        return user.getTenantUserId();
    }

    /**
     * Get the logged user object
     *
     * @return Object, either a Manager or Tenant
     * @throws NoSuchElementException if user is not found
     */
    public Object getLoggedUserType() {
        User user = getUser().orElseThrow();
        if (user.getUsertype().equals("manager")) {
            return managerRepository.findById(user.getManagerUserId()).orElse(null);
        } else if (user.getUsertype().equals("tenant")) {
            return tenantRepository.findById(user.getTenantUserId()).orElse(null);
        }
        throw new NoSuchElementException("User not found");
    }

    /**
     * Get the logged user object
     *
     * @return Object, either a Manager or Tenant
     * @throws NoSuchElementException if user is not found
     */
    @Deprecated
    public String loggedUserType(){
        User user = getUser().orElse(null);

        return user.getUsertype();
    }

    public ReqRes refreshTokenFromCookies(HttpServletRequest request, HttpServletResponse response) {
        ReqRes res = new ReqRes();

        Cookie refreshCookie = Arrays.stream(
                        Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])
                )
                .filter(c -> c.getName().equals("refreshToken"))
                .findFirst()
                .orElse(null);

        if (refreshCookie == null) {
            res.setStatusCode(401);
            res.setMessage("Refresh token missing");
            return res;
        }

        try {
            String username = jwtUtils.extractUsername(refreshCookie.getValue());
            User user = userRepository.findByUsername(username).orElseThrow();

            if (!jwtUtils.isTokenValid(refreshCookie.getValue(), user)) {
                res.setStatusCode(403);
                res.setMessage("Refresh token invalid");
                return res;
            }

            String newToken = jwtUtils.generateToken(user);

            Cookie token = new Cookie("token", newToken);
            token.setHttpOnly(true);
            token.setPath("/");
            token.setMaxAge(86400);
            token.setAttribute("SameSite", "Lax");

            response.addCookie(token);

            res.setStatusCode(200);
            res.setToken(newToken);
            res.setRefreshToken(refreshCookie.getValue());
            res.setMessage("Token refreshed");

        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }

        return res;
    }

}
