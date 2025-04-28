<script setup lang="ts">

</script>

<template>
  <header class="header">
    <div class="wrapper">
      <nav class="nav">
        <RouterLink to="/studies" class="nav-link">Browse</RouterLink>
        <RouterLink to="/about" class="nav-link">About</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>

.header {
  background-color: #ffffff;
  border-bottom: 1px solid #e5e7eb; /* Light gray line */
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.wrapper {
  max-width: 1200px;
  margin: 0 auto;
}

.nav {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  text-decoration: none;
  color: #374151; /* Dark gray */
  font-weight: 600;
  transition: color 0.3s;
}

.nav-link:hover {
  color: #3b82f6; /* Nice blue hover */
}
</style>
