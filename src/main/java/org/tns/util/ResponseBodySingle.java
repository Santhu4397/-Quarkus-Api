package org.tns.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class ResponseBodySingle<T> {
    private String status;
    private String responseCode;
    private String responseDescription;
    private T data;
    @Contract("_, _ -> new")
    public static  <V> @NotNull ResponseBodySingle ok(V data, String description) {
        return new ResponseBodySingle("ok", "200", description, data);
    }
    public static  <V> @NotNull ResponseBodySingle success(V data, String description) {
        return new ResponseBodySingle("SUCCESS", "201", description, data);
    }
    public static <V> @NotNull  ResponseBodySingle notFound(V data, String description) {
        return new ResponseBodySingle("NOT_FOUND", "404", description,data);
    }

    public static <V> @NotNull ResponseBodySingle  internalServerError(V data, String description) {
        return new ResponseBodySingle("INTERNAL_SERVER_ERROR", "500", description,data);
    }
    public static<V> @NotNull ResponseBodySingle  Bad_Request(V data, String description) {
        return new ResponseBodySingle("BAD_REQUEST", "400", description,data);
    }
    public static<V> @NotNull ResponseBodySingle  un_Authorized(V data, String description) {
        return new ResponseBodySingle("UNAUTHORIZED", "401", description,data);
    }


}
