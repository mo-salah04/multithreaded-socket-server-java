# Network Lab 2 - Multi-Client TCP Server

This Java project demonstrates a multi-client TCP server using threads. The server listens on port `4999`, accepts multiple clients, processes commands, and writes activity to `server_log.txt`.

## Features

Supported commands:

- `UPPER:text` converts text to uppercase.
- `LOWER:text` converts text to lowercase.
- `REVERSE:text` reverses text.
- `COUNT:text` counts the number of characters.
- `VOWELS:text` counts vowels.
- `DATE` returns the current date.
- `TIME` returns the current time.
- `QUIT` disconnects the client.

## Code Review Fixes

- Added safer socket/resource closing using `try-with-resources`.
- Replaced the non-thread-safe client counter with `AtomicInteger`.
- Used the actual client IP address instead of hardcoding `127.0.0.1`.
- Added response handling for empty commands.
- Used `StringBuilder` for reversing text.
- Removed generated build files and private NetBeans files from the GitHub version.

## Run

Compile:

```bash
javac -d out src/netlab2/*.java
```

Run the server:

```bash
java -cp out netlab2.Server
```

Run clients in separate terminals:

```bash
java -cp out netlab2.Client
```

## Documentation

Screenshots and the lab report are included in `docs/`.
