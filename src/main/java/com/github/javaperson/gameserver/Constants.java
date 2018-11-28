package com.github.javaperson.gameserver;

import io.vertx.core.eventbus.DeliveryOptions;

public interface Constants {
    String KRYO = "kryo";
    DeliveryOptions BASE_DELIVERY_OPTIONS = new DeliveryOptions().setCodecName(KRYO);
}
