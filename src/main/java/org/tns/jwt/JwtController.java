package org.tns.jwt;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.tns.util.ResponseBodySingle;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
public class JwtController {
    @ConfigProperty(name = "jwt.duration") public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;
    @PermitAll
    @POST
    @Path("/login") @Produces(MediaType.APPLICATION_JSON)
    public Response login(RequestBody reqBody) {
        Log.info("------------JWT Token Controller ----------");
        JwtUser u = JwtUser.findByUsername(reqBody.username);

        if (u != null && u.password.equals(reqBody.password)) {
            try {
                Log.info("------------JWT Token Generated----------");
                return Response.status(CREATED).entity(ResponseBodySingle.success("Bearer "+TokenGenerator.generateToken(u.username, u.roles, duration, issuer), "JWT Token Generated")).build();
            } catch (Exception e) {
                Log.error("------------JWT Token Not Generated----------");
                return Response.status(UNAUTHORIZED).entity(ResponseBodySingle.un_Authorized(null, "User UNAUTHORIZED")).build();
            }
        } else {
            Log.error("------------JWT Token Not Generated Because No User exist ----------");
            return Response.status(UNAUTHORIZED).entity(ResponseBodySingle.un_Authorized(null, "!!!User name and Password Don't match!!!")).build();
        }
    }
}
