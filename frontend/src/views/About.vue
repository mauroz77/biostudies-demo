<template>
    <div class="about-page">
      <h1 class="title">🧬 BioStudies Demo: A Simplified BioStudies-like System</h1>
      <p class="intro">
        BioStudies Demo is a prototype project designed to explore and replicate the core structure and functionality of studies hosted in the BioStudies platform.
      </p>
  
      <section class="section">
        <h2 class="section-title">🔧 Backend</h2>
        <p class="section-text">
          A REST API built with Spring Boot, integrated with PostgreSQL and Apache Lucene for indexing and search capabilities.
        </p>
        <ul class="list">
          <li>Fetches real study data from the BioStudies API.</li>
          <li>Stores the data in a PostgreSQL database.</li>
          <li>Indexes the data using Apache Lucene for fast text-based search.</li>
          <li>Exposes endpoints to query studies by text and retrieve study details.</li>
        </ul>
      </section>
  
      <section class="section">
        <h2 class="section-title">💻 Frontend</h2>
        <p class="section-text">
          A web interface built with Vue.js and styled using Tailwind CSS.
        </p>
        <ul class="list">
          <li>Search: A text-based search interface to find relevant studies.</li>
          <li>Results: Displays a list of matched studies.</li>
          <li>Study Details: Shows metadata and details of a selected study.</li>
          <li>About: A simple informational page about the project.</li>
        </ul>
      </section>
      
      <section class="section">
        <h2 class="section-title">📚 API Documentation</h2>
        <p class="section-text">
          Explore the full capabilities of our REST API with interactive documentation.
        </p>
        <div class="api-doc-link">
          <a href="/api/swagger-ui/index.html" target="_blank" class="doc-button">
            View API Documentation
          </a>
        </div>
      </section>
      
    </div>
  </template>
  
  <style scoped>
  .about-page {
    max-width: 800px;
    margin: 2rem auto;
    padding: 1rem;
    font-family: system-ui, sans-serif;
    color: #374151; /* Tailwind's gray-700 */
  }
  
  .title {
    font-size: 2rem;
    font-weight: bold;
    margin-bottom: 1rem;
  }
  
  .intro {
    font-size: 1.1rem;
    margin-bottom: 2rem;
  }
  
  .section {
    margin-bottom: 2.5rem;
  }
  
  .section-title {
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: 0.75rem;
  }
  
  .section-text {
    margin-bottom: 1rem;
  }
  
  .list {
    list-style-type: disc;
    padding-left: 1.5rem;
    color: #4b5563; /* Tailwind's gray-600 */
  }
  
  .list li {
    margin-bottom: 0.5rem;
  }
  </style>