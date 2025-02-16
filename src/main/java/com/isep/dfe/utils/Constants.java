package com.isep.dfe.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    @Value("${application.mode")
    private String mode;

    public static final String BASE_MAPPING = "api/v1";
    public static final String APP_MODE = "dev";
}
