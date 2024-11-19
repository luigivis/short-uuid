package com.luigivismara.shortuuid;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ShortUuidTest {

    @Test
    void testRandomShortUuid() {
        // Generate a random ShortUuid
        var shortUuid = ShortUuid.random();
        assertThat(shortUuid).isNotNull();
        assertThat(shortUuid.toString()).isNotEmpty();

        var decodedUuid = shortUuid.decode();
        assertThat(decodedUuid).isNotNull();
        assertThat(decodedUuid.toString()).matches("^[a-f0-9\\-]{36}$");
    }

    @Test
    void testRandomShortUuidWithCustomAlphabet() {
        var customAlphabet = "abcdef1234567890".toCharArray();

        var shortUuid = ShortUuid.random(customAlphabet);
        assertThat(shortUuid).isNotNull();
        assertThat(shortUuid.toString()).isNotEmpty();

        assertThat(shortUuid.toString()).matches("^[abcdef1234567890]+$");

        var decodedUuid = shortUuid.decode();
        assertThat(decodedUuid).isNotNull();
        assertThat(decodedUuid.toString()).matches("^[a-f0-9\\-]{36}$");
    }

    @Test
    void testEncodeAndDecode() {
        var uuid = UUID.randomUUID();

        var shortUuid = ShortUuid.encode(uuid);
        assertThat(shortUuid).isNotNull();
        assertThat(shortUuid.toString()).isNotEmpty();

        var decodedUuid = shortUuid.decode();
        assertThat(decodedUuid).isEqualTo(uuid);
    }

    @Test
    void testEncodeAndDecodeWithCustomAlphabet() {
        var uuid = UUID.randomUUID();
        var customAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        var shortUuid = ShortUuid.encode(uuid, customAlphabet, 12);
        assertThat(shortUuid).isNotNull();
        assertThat(shortUuid.toString()).hasSize(12);

        assertThat(shortUuid.toString()).matches("^[a-z]+$");
    }

    @Test
    void testEqualityAndHashCode() {
        var uuid = UUID.randomUUID();

        var shortUuid1 = ShortUuid.encode(uuid);
        var shortUuid2 = ShortUuid.encode(uuid);

        assertThat(shortUuid1).isEqualTo(shortUuid2);
        assertThat(shortUuid1.hashCode()).isEqualTo(shortUuid2.hashCode());
    }

    @Test
    void testToString() {
        var shortUuid = ShortUuid.random();
        assertThat(shortUuid.toString()).isNotNull().isNotEmpty();
    }

    @Test
    void testDecodeStaticMethod() {
        var uuid = UUID.randomUUID();

        var shortUuid = ShortUuid.encode(uuid);

        var decodedUuid = ShortUuid.decode(shortUuid.toString());
        assertThat(decodedUuid).isEqualTo(uuid);
    }
}
