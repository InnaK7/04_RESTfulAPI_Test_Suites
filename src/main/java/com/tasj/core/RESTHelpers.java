package com.tasj.core;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import java.util.Base64;

public class RESTHelpers {

    public static Invocation.Builder requestTo(String uri) {
        return ClientBuilder.newClient().target(uri).request();
    }

    public static Invocation.Builder authorized(String credentials, Invocation.Builder requestBuilder) {
        return requestBuilder.
                header("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes()));
    }

}
