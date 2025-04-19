<template>
  <div class="text-sm text-gray-700">
    <div class="mb-1 text-blue-600 font-medium">Publication</div>
    <p class="mb-2 italic">
      {{ getAttribute('Journal') }}.
      {{ getAttribute('Publication date') }};
      Volume {{ getAttribute('Volume') }}.
      Page: {{ getAttribute('Pages') }}.
    </p>
    <div class="flex flex-wrap gap-3">
      <template v-for="(group, groupIdx) in data.links" :key="groupIdx">
        <template v-for="(link, idx) in group" :key="idx">
          <a :href="resolveLinkUrl(link)" target="_blank" rel="noopener noreferrer"
            class="inline-flex items-center px-2 py-1 rounded text-xs font-medium text-gray-800 hover:bg-gray-100">
            <span class="mr-1 text-gray-500">🔗</span>
            <span class="uppercase font-semibold text-gray-600">
              {{ getLinkType(link) }}
            </span>
            <span class="ml-1 text-blue-600 underline">
              {{ link.url }}
            </span>
          </a>
        </template>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { StudyLink, StudySection } from '@/types/study';

const p: StudySection = {
  "type": "Publication",
  "attributes": [
    {
      "name": "Journal",
      "value": "Diabetologia"
    },
    {
      "name": "Volume",
      "value": "68(1)"
    },
    {
      "name": "Pages",
      "value": "231-242"
    },
    {
      "name": "Publication date",
      "value": "2025 Jan"
    }
  ],
  "links": [
    [
      {
        "url": "10.1007/s00125-024-06288-0",
        "attributes": [
          {
            "name": "Type",
            "value": "DOI"
          }
        ]
      },
      {
        "url": "PMC11663195",
        "attributes": [
          {
            "name": "Type",
            "value": "PMC"
          }
        ]
      },
      {
        "url": "39422717",
        "attributes": [
          {
            "name": "Type",
            "value": "PMID"
          }
        ]
      }
    ]
  ],
  "files": []
}

function getAttribute(name: string): string {
  const attr = props.data.attributes?.find(a => a.name === name);
  return attr?.value || '';
}

function getLinkType(link: StudyLink): string {
  return link.attributes?.find(attr => attr.name === 'Type')?.value || 'Link';
}

function resolveLinkUrl(link: StudyLink): string {
  const value = link.url;
  const type = getLinkType(link).toLowerCase();
  if (type === 'doi') return `https://doi.org/${value}`;
  if (type === 'pmc') return `https://www.ncbi.nlm.nih.gov/pmc/articles/${value}`;
  if (type === 'pmid') return `https://pubmed.ncbi.nlm.nih.gov/${value}`;
  return value.startsWith('http') ? value : `https://${value}`;
}

const props = defineProps<{
  data: StudySection
}>();
</script>