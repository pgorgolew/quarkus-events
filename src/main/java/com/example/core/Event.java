package com.example.core;

import com.example.core.consumer.EventConsumer;

import java.util.Map;

public record Event(String payload, Map<String, String> metadata) {
}
