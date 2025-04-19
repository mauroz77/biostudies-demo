<template>
    <div
      @click="$emit('click')"
      class="cursor-pointer border rounded-xl p-4 shadow-sm hover:shadow-md transition"
    >
      <h2 class="text-lg font-semibold mb-1">
        {{ study.title }}
      </h2>
      <p class="text-sm text-gray-600 mb-2">
        {{ study.author }}
      </p>
      <p class="text-xs text-gray-400">
        {{ study.release_date }} • {{ study.files }} files • {{ study.links }} links
      </p>
    </div>
  </template>
  
  <script setup lang="ts">
  import type { StudyHit } from '@/types/study'
  
  defineProps<{
    study: StudyHit
  }>()
  
  defineEmits(['click'])
  </script>