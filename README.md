# Producer-Consumer Task

This project implements a simple producer-consumer task using Java and the Producer-Consumer pattern. It demonstrates how multiple producers generate data blocks and add them to a shared queue, while multiple consumers consume these data blocks from the queue and perform some processing.

## Features

- The project utilizes multithreading to achieve concurrent execution of producers and consumers.
- It employs a blocking queue to manage the synchronization between producers and consumers, ensuring thread safety.
- Producers generate random data blocks of varying sizes and add them to the queue.
- Consumers consume data blocks from the queue, perform processing on them, and write the results to an output file.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Getting Started

1. Clone the repository:

   ```shell
   git clone https://github.com/har894/vmtask.git


## Build the Project using Maven

To build the project, follow these steps:

1. Open a terminal or command prompt.
2. Navigate to the root directory of the project.
3. Run the following command: ```mvn clean install```
4. After a successful build, you can run the project using the following command:
   ```shell
   java -jar target/your-project.jar 
   ```
   Replace your-project.jar with the actual name of your project's JAR file.
