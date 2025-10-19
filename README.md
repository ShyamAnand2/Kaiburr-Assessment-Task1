# Kaiburr Assessment - Task 1: Java REST API

**Author:** Shyam Anand  
**Date:** October 19, 2025

## Overview

This project implements a Java-based REST API for managing task objects that represent shell commands. The application is built using Spring Boot 3.5.6, MongoDB for data persistence, and provides endpoints for creating, searching, updating, deleting, and executing shell commands.

## Technology Stack

- **Java:** 17
- **Framework:** Spring Boot 3.5.6
- **Database:** MongoDB (Local instance)
- **Build Tool:** Maven
- **Testing:** Postman

## Project Structure

task-manager/
├── src/
│ └── main/
│ └── java/
│ └── com/
│ └── platinum/
│ └── task_manager/
│ ├── PlatinumTaskManagerApplication.java
│ ├── Task.java
│ ├── TaskExecution.java
│ ├── TaskRepository.java
│ └── TaskController.java
├── screenshots/
├── pom.xml
└── README.md

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven
- MongoDB (running locally on port 27017)

### Steps to Run

1. **Clone the repository:**
git clone https://github.com/ShyamAnand2/Kaiburr-Assessment-Task1.git
cd Kaiburr-Assessment-Task1

2. **Ensure MongoDB is running:**
mongod --dbpath=C:\data\db

3. **Build and run the application:**
mvnw.cmd spring-boot:run

4. **The application will start on:**
http://localhost:8080

## Data Model

### Task Object
{
"id": "string",
"name": "string",
"owner": "string",
"command": "string",
"taskExecutions": [
{
"startTime": "date",
"endTime": "date",
"output": "string"
}
]
}

text

## API Endpoints

### 1. Create a Task
**Endpoint:** `POST /tasks`  
**Description:** Creates a new task with the specified name, owner, and command.

**Request Body:**
{
"name": "platinum-test",
"owner": "Shyam",
"command": "echo platinum"
}

text

**Screenshot:**
<img width="1918" height="1078" alt="Task-1-POST" src="https://github.com/user-attachments/assets/18df4b3f-424b-43fb-a8a6-04dd73347142" />

---

### 2. Get All Tasks
**Endpoint:** `GET /tasks`  
**Description:** Returns a list of all tasks stored in the database.

**Screenshot:**
<img width="1918" height="1078" alt="Task-1-GET" src="https://github.com/user-attachments/assets/7c2faa31-d0cb-4d51-82b8-8dce90259523" />

---

### 3. Search Tasks by Name
**Endpoint:** `GET /tasks/search?name={searchString}`  
**Description:** Searches for tasks where the name contains the specified string.

**Example:** `GET /tasks/search?name=platinum`

**Screenshot:**
<img width="1918" height="1078" alt="Task-1-GET-2" src="https://github.com/user-attachments/assets/f63a7ec0-a02e-49f7-a864-485dab17645c" />

---

### 5. Update a Task
**Endpoint:** `PUT /tasks/{id}`  
**Description:** Updates an existing task with new data.

**Request Body:**
{
"name": "platinum-updated",
"owner": "Shyam",
"command": "echo updated"
}

text

**Screenshot:**

Before: When we specifically create a platinum-original task to showcase the update functionality
<img width="1918" height="1078" alt="Task-1-UPDATE-1" src="https://github.com/user-attachments/assets/a9cbbdcf-e474-4d96-b4be-9dfba4ba5f41" />

After: The GET with all the tasks to showcase that PUT was used to update platinum-original to platinum-updated.
<img width="1918" height="1067" alt="Task-1-UPDATE-2" src="https://github.com/user-attachments/assets/093b1910-63ce-430d-9fe6-17e8bfe11d6d" />

---

### 6. Delete a Task
**Endpoint:** `DELETE /tasks/{id}`  
**Description:** Deletes a task by its ID.

**Example:** `DELETE /tasks/671234abcd5678ef`

**Screenshot:**

Before: With all the tasks present.
<img width="1918" height="1078" alt="Task-1-DELETE" src="https://github.com/user-attachments/assets/f17a448d-1263-47d7-9aa6-51e842e0e237" />

After: One of them missing, run with the DELETE command.
<img width="1918" height="1078" alt="Task-1-DELETE-2" src="https://github.com/user-attachments/assets/de167ac7-e894-48dd-b082-1ef6305342e5" />

---

### 7. Execute Task Command
**Endpoint:** `PUT /tasks/{id}/execute`  
**Description:** Executes the shell command associated with the task and stores the execution result (startTime, endTime, output) in the taskExecutions array.

**Example:** `PUT /tasks/671234abcd5678ef/execute`

**Note:** No request body required. Only commands starting with "echo " are allowed for security reasons.

**Screenshot - After Execution:**
<img width="1918" height="1078" alt="Task-1-EXECUTE" src="https://github.com/user-attachments/assets/2f735986-bf66-4935-b7e9-5ac01b5ed1c8" />
The time stamp in the taskExecutions bar shows the time when the code has been executed, and the table in the Preview part shows how many times the task has been executed.

---

## Security Considerations

- Command validation is implemented to allow only safe `echo` commands
- Commands must start with "echo " to be executed
- This prevents execution of potentially malicious or unsafe shell commands

## MongoDB Data Verification

The following screenshot shows the tasks stored in MongoDB Compass:
<img width="1918" height="1078" alt="Task-1-MONGO" src="https://github.com/user-attachments/assets/f0a75968-382a-412e-b4b7-fbaf0f43bcfa" />

---

## Testing with Postman

All endpoints were tested using Postman. The screenshots above demonstrate:
- Request URL and HTTP method
- Request body (where applicable)
- Response with status code
- Timestamp and author name visible

## Sample Request/Response

### Create Task Example

**Request:**
POST http://localhost:8080/tasks
Content-Type: application/json

{
"name": "platinum-two",
"owner": "Shyam",
"command": "echo platinum 2"
}

text

**Response:**
{
"id": "68f4e3925c39df428267296f",
"name": "platinum-two",
"owner": "Shyam",
"command": "echo platinum 2",
"taskExecutions": []
}

text

### Execute Task Example

**Request:**
PUT http://localhost:8080/tasks/68f4e3925c39df428267296f/execute

text

**Response:**
{
"id": "68f4e3925c39df428267296f",
"name": "platinum-two",
"owner": "Shyam",
"command": "echo platinum 2",
"taskExecutions": [
{
"startTime": "2025-10-19T13:30:15.123+00:00",
"endTime": "2025-10-19T13:30:15.456+00:00",
"output": "platinum 2\n"
}
]
}

text

## Known Limitations

- Only `echo` commands are supported for execution
- Commands are executed locally on Windows using `cmd.exe /c`
- No authentication/authorization implemented (as per task requirements)

## Future Enhancements

- Support for additional safe shell commands
- Kubernetes pod execution (Task 2 requirement)
- Input validation and error handling improvements
- Rate limiting and request throttling

## Author Notes

All screenshots in this README include:
- My name (Shyam Anand) visible in the environment
- System timestamp visible in the taskbar
- Complete request/response information

This ensures authenticity and demonstrates that the work was completed by me.

---

**Repository:** https://github.com/ShyamAnand2/Kaiburr-Assessment-Task1  
**Submission Date:** October 20, 2025
