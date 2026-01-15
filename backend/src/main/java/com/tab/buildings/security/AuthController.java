package com.tab.buildings.security;


import com.tab.buildings.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login endpoint
     */
    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest,
                                         HttpServletResponse response) {

        ReqRes res = authService.signIn(signInRequest);

        if (res.getStatusCode() == 200) {
            // Tworzenie ciasteczek z tokenami
            Cookie token = createCookie("token", res.getToken(), 86400);
            Cookie refreshToken = createCookie("refreshToken", res.getRefreshToken(), 86400);

            response.addCookie(token);
            response.addCookie(refreshToken);
        }

        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    /**
     * Refresh token endpoint
     */
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshTokenFromCookies(HttpServletRequest request,
                                                          HttpServletResponse response) {

        ReqRes res = authService.refreshTokenFromCookies(request, response);

        if (res.getStatusCode() == 200) {
            // Odświeżony token – ustaw ciasteczko
            Cookie token = createCookie("token", res.getToken(), 86400);
            response.addCookie(token);
        }

        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    /**
     * Logout endpoint
     */
    @PostMapping("/logout")
    public ResponseEntity<ReqRes> logout(HttpServletResponse response) {

        Cookie token = createCookie("token", "", 0);
        Cookie refreshToken = createCookie("refreshToken", "", 0);

        response.addCookie(token);
        response.addCookie(refreshToken);

        ReqRes res = new ReqRes();
        res.setStatusCode(204);
        res.setMessage("Logged out");

        return ResponseEntity.ok(res);
    }

    /**
     * Helper method do tworzenia ciasteczek z poprawnymi parametrami dla CORS
     */
    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true jeśli HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "Lax"); // "Lax" jest bezpieczne dla localhost
        return cookie;
    }
}
