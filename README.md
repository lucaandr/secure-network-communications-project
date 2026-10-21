# Telecom & Cybersecurity Labs

A collection of Java exercises exploring cryptographic building blocks used to protect network communications. The projects progress from symmetric encryption to authenticated key exchange and certificate-based signatures.

These are educational lab implementations based on university coursework. They are intended for learning and portfolio review, not production use.

## Labs

| Folder | Topic | Main concepts |
|---|---|---|
| `01-symmetric-crypto` | Symmetric encryption | AES, shared keys, IVs, encrypt/decrypt flow |
| `02-rsa-key-exchange` | RSA session-key exchange | RSA key pairs, public-key transport, symmetric session encryption |
| `03-authenticated-encryption` | Message authentication and EtM | HMAC, integrity checks, Encrypt-then-MAC |
| `04-x509-signatures` | Digital signatures and X.509 | RSA signatures, certificate validity, CA trust verification |
| `05-psk-key-exchange` | Authenticated key exchange | Nonces, pre-shared keys, MAC authentication, session-key derivation |

## Structure

Each lab is a standalone Eclipse Java project. Import a lab folder as an existing Java project. Source code is under `src/`; the `RunTest` class in each lab contains the sample entry point.

Labs 4 and 5 use local key stores and credentials. Private key stores and shared-secret key stores are intentionally excluded from version control. Create your own local test credentials before running those examples. Public certificate fixtures in Lab 4 are included for certificate-chain exercises.

## Scope

The examples use Java Cryptography Architecture (JCA/JCE) APIs and a small in-memory communicator to demonstrate how cryptographic operations fit into message flows. They are simplified teaching examples and omit production concerns such as protocol negotiation, robust key lifecycle management, and deployment hardening.