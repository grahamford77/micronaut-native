package com.rightmove.demo.client;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Deserializable
public record AgifyResponse(Long count, String name, long age) {
}
