package com.oliverlockwood.example;

/*
 * Copyright © 2020 Dalet - All Rights Reserved
 *
 * This file is part of Ooyala Flex.
 *
 * Unauthorized copying and/or distribution of this file or any other part of Ooyala Flex, via any medium,
 * is strictly prohibited.  Proprietary and confidential.
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class DeserializerTest {

    private static final UUID TEST_UUID = UUID.fromString("730145fe-324d-4fb1-b12f-60b89a045730");

    // In Spring-Boot 2.6.x the serialized form is simply prepended and appended with a double-quote, as per JSON standard
    private static final byte[] TEST_UUID_BYTES_SPRING_BOOT_2_6_x = ("\"" + TEST_UUID + "\"").getBytes(StandardCharsets.UTF_8);

    // In Spring-Boot 2.7.x the serialized form is an array like: ["<fully-qualified classname>","the-actual-uuid"]
    private static final byte[] TEST_UUID_BYTES_SPRING_BOOT_2_7_x = ("[\"java.util.UUID\",\"" + TEST_UUID + "\"]").getBytes(StandardCharsets.UTF_8);


    private final GenericJackson2JsonRedisSerializer target = new GenericJackson2JsonRedisSerializer();;


    @Test
    void verifySerializeIntoBytes() {
        byte[] serializedUuid = target.serialize(TEST_UUID);
        System.out.println(new String(serializedUuid, StandardCharsets.UTF_8));

        assertThat(serializedUuid).isEqualTo(TEST_UUID_BYTES_SPRING_BOOT_2_6_x);
//        assertThat(serializedUuid).isEqualTo(TEST_UUID_BYTES_SPRING_BOOT_2_7_x);
    }


    @Test
    void verifyDeserializeFromBytes() {
        UUID deserializedUuid = target.deserialize(TEST_UUID_BYTES_SPRING_BOOT_2_6_x, UUID.class);
//        UUID deserializedUuid = target.deserialize(TEST_UUID_BYTES_SPRING_BOOT_2_7_x, UUID.class);

        assertThat(deserializedUuid).isEqualTo(TEST_UUID);
    }

}
