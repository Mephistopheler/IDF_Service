package org.example.idf_service.config; import org.springframework.boot.context.properties.ConfigurationProperties; import java.time.*;
@ConfigurationProperties(prefix="rates") public record RatesProperties(String baseUrl, Duration connectTimeout, Duration readTimeout, ZoneId monthZone) {}
