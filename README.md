# LRU Cache Implementation in Java

This repository contains a **from-scratch implementation of an LRU (Least Recently Used) cache** in Java, along with **comprehensive unit tests** and **service-level integration tests**.  
The goal of this project is to deeply understand cache internals, eviction strategies, and how caching is used in real-world systems.

---

## 🚀 What is Implemented

### 1. In-Memory LRU Cache
- Custom **LRU cache implementation**
- Uses:
    - `ConcurrentHashMap` for O(1) lookups
    - Doubly Linked List for maintaining access order
- Supports:
    - `getCustomer(key)`
    - `putCustomer(key, value)`
- Automatically evicts the **least recently used entry** when capacity is exceeded

---

### 2. Cache Design Details

- **Head** → Most Recently Used (MRU)
- **Tail** → Least Recently Used (LRU)
- Any **read or write** moves the entry to the head
- Eviction always removes the tail

**Time Complexity**
| Operation | Complexity |
|---------|------------|
| Get | O(1) |
| Put | O(1) |
| Eviction | O(1) |

---

### 3. Thread Safety
- `ConcurrentHashMap` is used for concurrent access
- Critical linked-list operations are synchronized
- Safe for multi-threaded access scenarios

---

## 🧪 Testing Strategy

### 1. Cache Unit Tests
`CustomerLruCacheTest`
- Cache miss handling
- Cache hit behavior
- LRU eviction order
- Access-based recency updates
- Updating existing entries
- Edge case: cache capacity = 1

### 2. Service-Level Tests
`CustomerServiceTest`
- Read-through cache behavior
- Repository call on cache miss
- No repository call on cache hit
- Repository call after eviction

All tests are written using **JUnit 5** and **Mockito**.

---

## 🧠 Why This Project Matters

This project demonstrates :
- Understanding of **cache eviction policies**
- Strong grasp of **data structures (HashMap + Doubly Linked List)**
- Ability to reason about **time and space complexity**
- Proper **unit and integration testing practices**
- Clean separation of concerns (Cache vs Service vs Repository)

This is a common **system design and low-level design interview problem**, and this implementation closely matches real-world expectations.

---


