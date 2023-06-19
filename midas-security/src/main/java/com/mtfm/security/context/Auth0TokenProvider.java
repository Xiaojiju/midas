/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.security.context;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用auth0进行生成token，token中尽量包含一些简单的信息，切勿将敏感信息包装在里面
 * @author 一块小饼干
 * @since 1.0.0
 */
public class Auth0TokenProvider implements TokenProvider {

    private static final String SECRET = "X5V7QZ2Z4CB40A7T";
    private static final Algorithm DEFAULT_ALGORITHM = Algorithm.HMAC256(SECRET);
    protected static final String ISSUER = "一块小饼干";
    private final Algorithm algorithm;

    public Auth0TokenProvider() {
        this(DEFAULT_ALGORITHM);
    }

    public Auth0TokenProvider(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String make(Map<String, String> claims) {
        JWTCreator.Builder builder = JWT.create()
            .withIssuedAt(Instant.now())
            .withIssuer(ISSUER)
            .withPayload(claims);
        return builder.sign(this.algorithm);
    }

    @Override
    public Map<String, String> parse(String token) throws Exception {
        JWTVerifier verifier = JWT.require(this.algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String, Claim> map = decodedJWT.getClaims();
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        Map<String, String> payload = new HashMap<>();
        for (Map.Entry<String, Claim> entry : map.entrySet()) {
            payload.put(entry.getKey(), entry.getValue().asString());
        }
        return payload;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
