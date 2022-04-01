package com.cryptolisting.springreactjs.util;

import com.cryptolisting.springreactjs.models.AuthorizationResponse;
import com.cryptolisting.springreactjs.models.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorizationUtil {

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    public AuthorizationResponse authorize(HttpServletRequest httprequest) {
        String authorizationHeader = httprequest.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new AuthorizationResponse(ResponseCodes.ERROR, "Authorization header was not found.");
        }

        String email = null;
        String jwt;

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new AuthorizationResponse(ResponseCodes.ERROR, "Bad JWT.");
        }

        List<Object> payload = new ArrayList<>();
        payload.add(email);

        return new AuthorizationResponse(ResponseCodes.OK, "JWT Processing success.", payload);
    }
}
