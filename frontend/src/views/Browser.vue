<template>
  <div class="p-6 max-w-6xl mx-auto">
    <SearchBar @search="handleSearch" />

    <div v-if="loading" class="mt-8">
      <Loader />
    </div>

    <div v-if="error" class="mt-8">
      <Error :message="error" />
    </div>

    <div v-if="studies.length > 0" class="mt-8 grid gap-4">
      <StudyCard
        v-for="study in studies"
        :key="study.accession"
        :study="study"
        @click="goToDetail(study.accession)"
      />
    </div>

    <div v-if="!loading && !error && studies.length === 0 && hasSearched" class="mt-8 text-center text-gray-500">
      No results found.
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import type { StudyHit } from '@/types/study'
import { searchStudies } from '@/services/api'

import SearchBar from '@/components/SearchBar.vue'
import StudyCard from '@/components/StudyCard.vue'
import Loader from '@/components/Loader.vue'
import Error from '@/components/Error.vue'

const router = useRouter()
const studies = ref<StudyHit[]>([])
const loading = ref(false)
const error = ref('')
const hasSearched = ref(false)

function handleSearch(query: string) {
  loading.value = true
  error.value = ''
  hasSearched.value = true

  searchStudies(query)
    .then(data => {
      studies.value = data
    })
    .catch(err => {
      error.value = 'Failed to load results.'
      console.error(err)
    })
    .finally(() => {
      loading.value = false
    })
}

function goToDetail(accno: string) {
  router.push({ name: 'study-detail', params: { accno } })
}
</script>