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

5 folders each with a different scope

## Scope

The examples use Java Cryptography Architecture (JCA/JCE) APIs and a small in-memory communicator to demonstrate how cryptographic operations fit into message flows. They are simplified teaching examples and omit production concerns such as protocol negotiation, robust key lifecycle management, and deployment hardening.
