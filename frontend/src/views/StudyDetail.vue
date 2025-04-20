<template>
  <div class="p-6 max-w-5xl mx-auto">
    <div v-if="loading">
      <Loader />
    </div>

    <div v-else-if="error">
      <Error :message="error" />
    </div>

    <div v-else-if="study">
      <h1 class="text-2xl font-bold mb-4">{{ getAttribute('Title') }}</h1>

      <div class="mb-6 text-gray-700">
        <strong>Released:</strong> {{ getAttribute('ReleaseDate') }}
      </div>

      <div class="mb-3">
        <Authors :authors="authors" :affiliationsIndexes="affiliationsIndexes" />
      </div>

      <div class="mb-6">
        <Affiliations :affiliations="affiliations" :affiliationsIndexes="affiliationsIndexes" />
      </div>

      <div class="mb-6 text-gray-700">
        <strong>Accession:</strong> {{ study.accno }}
      </div>

      <div class="text-gray-700">
        <strong>Abstract:</strong>
      </div>

      <div class="prose prose-sm max-w-none mb-6" v-html="getAbstract()"></div>

      <div v-if="study.section?.files?.length" class="mb-6">
        <h2 class="text-xl font-semibold mb-2">Files</h2>
        <ul class="list-disc list-inside">
          <li v-for="file in study.section.files" :key="file.path">
            {{ file.path }} ({{ formatSize(file.size) }})
          </li>
        </ul>
      </div>

      <div class="mb-6" v-if="publications.length">
        <h2 class="text-xl font-semibold mb-2">Publications</h2>
        <div class="space-y-4">
          <PublicationSection v-for="(pub, idx) in publications" :key="idx" :data="pub" />
        </div>
      </div>

      <div v-if="funding.length" class="mb-6">
        <h2 class="text-xl font-semibold mb-2">Funding</h2>
        <ul class="list-disc list-inside">
          <li v-for="(f, idx) in funding" :key="idx">
            {{f.attributes.find(a => a.name === 'Agency')?.value}}
            <span v-if="f.attributes.find(a => a.name === 'grant_id')">
              — Grant: {{f.attributes.find(a => a.name === 'grant_id')?.value}}
            </span>
          </li>
        </ul>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import type { StudyDetail, StudySection } from '@/types/study'
import { getStudyDetail } from '@/services/api'

import Loader from '@/components/Loader.vue'
import Error from '@/components/Error.vue'
import PublicationSection from '@/components/PublicationSection.vue'
import Authors from '@/components/Authors.vue'
import Affiliations from '@/components/Affiliations.vue'

const route = useRoute()
const study = ref<StudyDetail | null>(null)
const loading = ref(false)
const error = ref('')

const funding = ref<StudySection[]>([])
const publications = ref<StudySection[]>([])
const authors = ref<StudySection[]>([])
const affiliations = ref<StudySection[]>([])
const affiliationsIndexes = ref<Map<string, number>>(new Map())

onMounted(async () => {
  const accno = route.params.accno as string

  loading.value = true
  error.value = ''

  try {
    const data = await getStudyDetail(accno)

    study.value = data

    funding.value = data.section?.subsections?.filter(s => s.type === 'Funding') || []
    publications.value = data.section?.subsections?.filter(s => s.type === 'Publication') || []
    authors.value = data.section?.subsections?.filter(s => s.type === 'Author') || []
    affiliations.value = data.section?.subsections?.filter(s => s.type === 'Organization') || []

    let affiliationIdx = 1
    let affiliationsIndexesValues = new Map<string, number>([]);

    affiliations.value.forEach(a => {
      if (a.accno) {
        affiliationsIndexesValues.set(a.accno, affiliationIdx++)
      }
    })

    affiliationsIndexes.value = affiliationsIndexesValues

  } catch (err) {
    error.value = 'Failed to fetch study details.'
    console.error(err)
  } finally {
    loading.value = false
  }
})

function getAttribute(name: string): string {
  return study.value?.attributes?.find(attr => attr.name === name)?.value || ''
}

function getAbstract(): string {
  return (
    study.value?.section?.attributes?.find(attr => attr.name === 'Abstract')?.value || ''
  )
}

function formatSize(bytes: number): string {
  const kb = bytes / 1024
  if (kb < 1024) return `${kb.toFixed(1)} KB`
  return `${(kb / 1024).toFixed(1)} MB`
}
</script>
