Design Document

This project follows a producer-consumer architecture, which allows for concurrent execution of multiple producers and consumers.
The design aims to ensure thread safety, proper synchronization, and avoidance of race conditions.

Architecture:
- Producers: Multiple producer threads that generate data blocks and add them to a shared blocking queue.
- Consumers: Multiple consumer threads that consume data blocks from the queue and perform processing on them.
- Blocking Queue: A synchronized data structure that serves as the shared buffer between producers and consumers.
- Output File: The processed data blocks are written to an output file.

Synchronization:
To ensure proper synchronization and thread safety, the following synchronization mechanisms and techniques are employed:
- Synchronized Data Structures: The blocking queue is implemented using a synchronized data structure (e.g., BlockingQueue).
- Monitor Locks: Synchronization blocks (synchronized sections) are used to control access to critical sections of code.
- Wait and Notify: wait() and notifyAll() methods are used within synchronized blocks to implement the producer-consumer synchronization pattern.
- Thread Interruption: Thread interruption is properly handled using Thread.currentThread().isInterrupted() checks and Thread.currentThread().interrupt().

Race Condition Avoidance:
To avoid race conditions and ensure data integrity, the following strategies are employed:
- Synchronized Access: Access to shared resources is synchronized using monitor locks.
- Atomic Operations: Atomic operations are used to ensure multiple steps are executed atomically.
- Thread-Safe Data Structures: Thread-safe data structures, like BlockingQueue, are used to handle concurrent access and modifications.


