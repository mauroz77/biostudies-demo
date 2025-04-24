# BioStudies Demo: A Simplified BioStudies-like System

**BioStudies Demo** is a prototype project designed to explore and replicate the core structure and functionality of studies hosted in the [BioStudies](https://www.ebi.ac.uk/biostudies/) platform.

This demo is composed of two main components:

## 🔧 Backend
A REST API built with **Spring Boot**, integrated with **PostgreSQL** and **Apache Lucene** for indexing and search capabilities.

Key features:
- Fetches real study data from the BioStudies API.
- Stores the data in a PostgreSQL database.
- Indexes the data using Apache Lucene for fast text-based search.
- Exposes endpoints to query studies by text and retrieve study details.

## 💻 Frontend
A web interface built with **Vue.js** and styled using **Tailwind CSS**.

Available pages:
- **Search**: A text-based search interface to find relevant studies.
- **Results**: Displays a list of matched studies.
- **Study Details**: Shows metadata and details of a selected study.
- **About**: A simple informational page about the project.

## 🧩 Architecture Overview
You can include a diagram here to illustrate the high-level system design or the database schema. Visuals are excellent for interviews—they show you're thinking about design and data flow. Something like:

![Database Schema](./docs/db-schema.png)

## 🚀 Getting Started
Instructions to run the project locally:

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
npm run dev
```

## 📦 Tech Stack
Backend: Java, Spring Boot, PostgreSQL, Apache Lucene

Frontend: Vue.js, Tailwind CSS, Axios

## 📝 License
This project is for educational and demo purposes.