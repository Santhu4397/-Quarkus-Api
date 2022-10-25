package org.tns.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtUser {
    public String username;
    public String password;
    public Set<Role> roles;

    public static JwtUser findByUsername(String username){
        Map<String, JwtUser> data = new HashMap<>();
        data.put("user", new JwtUser("user", "1234", Collections.singleton(Role.USER)));
        data.put("admin", new JwtUser("admin", "4567", Collections.singleton(Role.ADMIN)));
        if (data.containsKey(username)) {
            return data.get(username);
        } else {
            return null;
        }
    }
}
