package com.abouerp.library.applet.utils;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Abouerp
 */
public abstract class WebClientUtils {

    public static final WebClient DEFAULT_CLIENT = WebClient.builder().build();


}
