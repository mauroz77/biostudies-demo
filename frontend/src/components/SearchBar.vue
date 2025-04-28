<template>
    <div class="flex gap-2">
      <input
        v-model="query"
        type="text"
        placeholder="Search studies..."
        @keydown.enter="emitSearch"
        class="flex-1 px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
      />
      <button
        @click="emitSearch"
        class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg"
      >
        Search
      </button>
    </div>
    <div class="italic text-xs text-gray-500">Example: gene</div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  
  const emit = defineEmits<{
    (e: 'search', query: string): void
  }>()
  
  const query = ref('')
  
  function emitSearch() {
    if (query.value.trim()) {
      emit('search', query.value.trim())
    }
  }
  </script>
  