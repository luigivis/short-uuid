# Short UUID

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A lightweight Java utility library for generating and decoding short UUID strings using customizable alphabets.

This library makes it easy to generate compact, URL-friendly UUID representations, with support for both instance-based and static operations.

## Repository

GitHub: [github.com/luigivis/short-uuid](https://github.com/luigivis/short-uuid)

---

## Features

- **Short UUIDs**: Generate compact, URL-friendly UUIDs.
- **Customizable alphabets**: Define your own alphabet for encoding.
- **Deterministic decoding**: Decode short UUIDs back into their original UUIDs.
- **Flexible usage**: Static and instance-based APIs.

---

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.luigivismara</groupId>
    <artifactId>short-uuid</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Usage

### Generate a Random Short UUID (Default Alphabet)

```java
ShortUuid shortUuid = ShortUuid.random();
System.out.println("Short UUID: " + shortUuid);

// Decode it back to the original UUID
UUID originalUuid = shortUuid.decode();
System.out.println("Original UUID: " + originalUuid);
```

### Generate a Short UUID with a Custom Alphabet

```java
char[] customAlphabet = "abcdef1234567890".toCharArray();
ShortUuid customShortUuid = ShortUuid.random(customAlphabet);
System.out.println("Short UUID with custom alphabet: " + customShortUuid);

// Decode it back to the original UUID
UUID decodedCustomUuid = customShortUuid.decode();
System.out.println("Decoded custom UUID: " + decodedCustomUuid);
```

### Encode and Decode a Specific UUID

```java
UUID uuid = UUID.randomUUID();
ShortUuid shortUuid = ShortUuid.encode(uuid);
System.out.println("Encoded Short UUID: " + shortUuid);

// Decode the Short UUID back to its original UUID
UUID decodedUuid = shortUuid.decode();
System.out.println("Decoded UUID: " + decodedUuid);
```

### Encode with a Custom Alphabet and Length

```java
UUID uuid = UUID.randomUUID();
char[] customAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
ShortUuid shortUuid = ShortUuid.encode(uuid, customAlphabet, 12);
System.out.println("Short UUID with custom alphabet and length: " + shortUuid);
```

---

## API Documentation

### `ShortUuid.random()`

Generates a short UUID using a random UUID and the default alphabet.

### `ShortUuid.random(char[] alphabet)`

Generates a short UUID using a random UUID and the provided custom alphabet.

### `ShortUuid.encode(UUID uuid)`

Encodes the given UUID into a short UUID using the default alphabet.

### `ShortUuid.encode(UUID uuid, char[] alphabet, int length)`

Encodes the given UUID into a short UUID using the provided custom alphabet and desired length.

### `decode()`

Decodes the short UUID back into its original UUID.

---

## Why Use Short UUIDs?

- **Compact**: Ideal for URLs, filenames, or any use case requiring short, unique identifiers.
- **Customizable**: Tailor the encoding to your needs with custom alphabets.
- **Deterministic**: Decode short UUIDs back to their original UUIDs.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.

---

## Author

**Luigi Vismara**  
GitHub: [github.com/luigivis](https://github.com/luigivis)

---

### Example Output

```bash
Short UUID: 9wY7p6X2zHbq
Original UUID: 123e4567-e89b-12d3-a456-426614174000

Short UUID with custom alphabet: a7c2f3e4d5b6
Decoded custom UUID: 123e4567-e89b-12d3-a456-426614174000
```