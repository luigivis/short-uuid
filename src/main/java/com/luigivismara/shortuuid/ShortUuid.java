package com.luigivismara.shortuuid;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

/**
 * A utility class for generating and decoding short UUID strings using a customizable alphabet.
 * This class supports both instance-based and static operations.
 * <p>
 * Example usage:
 * <pre>
 * // Generate a random ShortUuid with the default alphabet
 * ShortUuid shortUuid = ShortUuid.random();
 * System.out.println("Short UUID: " + shortUuid);
 *
 * // Decode it back to the original UUID
 * UUID originalUuid = shortUuid.decode();
 * System.out.println("Original UUID: " + originalUuid);
 *
 * // Generate a ShortUuid with a custom alphabet
 * char[] customAlphabet = "abcdef1234567890".toCharArray();
 * ShortUuid customShortUuid = ShortUuid.random(customAlphabet);
 * System.out.println("Short UUID with custom alphabet: " + customShortUuid);
 *
 * // Decode the custom ShortUuid
 * UUID decodedCustomUuid = customShortUuid.decode();
 * System.out.println("Decoded custom UUID: " + decodedCustomUuid);
 * </pre>
 *
 * @author Luigi Vismara
 */
public class ShortUuid {
    private static final char[] DEFAULT_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private final String uuid;
    private final char[] alphabet;

    /**
     * Constructs a ShortUuid instance with a custom UUID string and the default alphabet.
     * <p>
     * Example usage:
     * <pre>
     * ShortUuid shortUuid = new ShortUuid("abc123");
     * System.out.println("Short UUID: " + shortUuid);
     * </pre>
     *
     * @param uuid the short UUID string.
     */
    public ShortUuid(String uuid) {
        this(uuid, DEFAULT_ALPHABET);
    }

    /**
     * Constructs a ShortUuid instance with a custom UUID string and a custom alphabet.
     * <p>
     * Example usage:
     * <pre>
     * char[] customAlphabet = "abcdef1234567890".toCharArray();
     * ShortUuid shortUuid = new ShortUuid("abc123", customAlphabet);
     * System.out.println("Short UUID with custom alphabet: " + shortUuid);
     * </pre>
     *
     * @param uuid     the short UUID string.
     * @param alphabet the custom alphabet to use for encoding/decoding.
     */
    public ShortUuid(String uuid, char[] alphabet) {
        this.uuid = uuid;
        this.alphabet = Arrays.copyOf(alphabet, alphabet.length);
    }

    /**
     * Generates a ShortUuid using a random UUID and the default alphabet.
     * <p>
     * Example usage:
     * <pre>
     * ShortUuid shortUuid = ShortUuid.random();
     * System.out.println("Short UUID: " + shortUuid);
     * </pre>
     *
     * @return a ShortUuid instance.
     */
    public static ShortUuid random() {
        return random(DEFAULT_ALPHABET);
    }

    /**
     * Generates a ShortUuid using a random UUID and a custom alphabet.
     * <p>
     * Example usage:
     * <pre>
     * char[] customAlphabet = "abcdef1234567890".toCharArray();
     * ShortUuid shortUuid = ShortUuid.random(customAlphabet);
     * System.out.println("Short UUID with custom alphabet: " + shortUuid);
     * </pre>
     *
     * @param alphabet the custom alphabet to use.
     * @return a ShortUuid instance.
     */
    public static ShortUuid random(char[] alphabet) {
        return encode(UUID.randomUUID(), alphabet, calculateLength(alphabet.length));
    }

    /**
     * Encodes a given UUID into a ShortUuid with the default alphabet.
     *
     * Example usage:
     * <pre>
     * UUID uuid = UUID.randomUUID();
     * ShortUuid shortUuid = ShortUuid.encode(uuid);
     * System.out.println("Encoded Short UUID: " + shortUuid);
     * </pre>
     *
     * @param uuid the UUID to encode.
     * @return a ShortUuid instance.
     */
    public static ShortUuid encode(UUID uuid) {
        return encode(uuid, DEFAULT_ALPHABET, calculateLength(DEFAULT_ALPHABET.length));
    }

    /**
     * Encodes a given UUID into a ShortUuid with a custom alphabet.
     * Example usage:
     * <pre>
     * UUID uuid = UUID.randomUUID();
     * char[] customAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
     * ShortUuid shortUuid = ShortUuid.encode(uuid, customAlphabet, 12);
     * System.out.println("Encoded Short UUID with custom alphabet: " + shortUuid);
     * </pre>
     *
     * @param uuid     the UUID to encode.
     * @param alphabet the custom alphabet to use.
     * @param length   the desired length of the ShortUuid.
     * @return a ShortUuid instance.
     */
    public static ShortUuid encode(UUID uuid, char[] alphabet, int length) {
        var uuidStr = uuid.toString().replaceAll("-", "");
        var number = new BigInteger(uuidStr, 16);
        var encoded = encode(number, alphabet, length);
        return new ShortUuid(encoded, alphabet);
    }

    /**
     * Decodes a short UUID string back into its original UUID.
     * Example usage:
     * <pre>
     * ShortUuid shortUuid = ShortUuid.random();
     * UUID decodedUuid = shortUuid.decode();
     * System.out.println("Decoded UUID: " + decodedUuid);
     * </pre>
     *
     * @return the original UUID.
     */
    public UUID decode() {
        return decode(this.uuid, this.alphabet);
    }

    /**
     * Decodes a short UUID string back into its original UUID using the default alphabet.
     * Example usage:
     * <pre>
     * String shortUuidString = "abc123def456";
     * UUID decodedUuid = ShortUuid.decode(shortUuidString);
     * System.out.println("Decoded UUID: " + decodedUuid);
     * </pre>
     *
     * @param shortUuid the short UUID string to decode.
     * @return the original UUID.
     */
    public static UUID decode(String shortUuid) {
        return decode(shortUuid, DEFAULT_ALPHABET);
    }

    private static UUID decode(String shortUuid, char[] alphabet) {
        var sum = BigInteger.ZERO;
        var alphaSize = BigInteger.valueOf(alphabet.length);

        var chars = shortUuid.toCharArray();
        for (var i = 0; i < chars.length; i++) {
            sum = sum.add(
                    alphaSize.pow(i).multiply(BigInteger.valueOf(Arrays.binarySearch(alphabet, chars[i])))
            );
        }

        var hexString = sum.toString(16);
        hexString = String.format("%032x", new BigInteger(hexString, 16)); // Pad to 32 chars
        return UUID.fromString(
                hexString.substring(0, 8) + "-" +
                        hexString.substring(8, 12) + "-" +
                        hexString.substring(12, 16) + "-" +
                        hexString.substring(16, 20) + "-" +
                        hexString.substring(20, 32)
        );
    }

    private static String encode(BigInteger number, char[] alphabet, int length) {
        var alphaSize = BigInteger.valueOf(alphabet.length);
        var encoded = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0) {
            var fracAndRemainder = number.divideAndRemainder(alphaSize);
            encoded.append(alphabet[fracAndRemainder[1].intValue()]);
            number = fracAndRemainder[0];
        }

        while (encoded.length() < length) {
            encoded.append(alphabet[0]);
        }

        if (encoded.length() > length) {
            encoded.setLength(length);
        }

        return encoded.toString();
    }

    private static int calculateLength(int alphabetSize) {
        var factor = Math.log(256) / Math.log(alphabetSize);
        return (int) Math.ceil(factor * 16);
    }

    /**
     * Returns the short UUID as a string.
     * Example usage:
     * <pre>
     * ShortUuid shortUuid = ShortUuid.random();
     * System.out.println("Short UUID: " + shortUuid.toString());
     * </pre>
     *
     * @return the string representation of the short UUID.
     */
    @Override
    public String toString() {
        return uuid;
    }

    /**
     * Computes the hash code for this short UUID.
     *
     * @return the hash code of the short UUID.
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    /**
     * Compares this short UUID with another object for equality.
     *
     * @param o the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortUuid other)) return false;
        return other.toString().equals(uuid);
    }
}
