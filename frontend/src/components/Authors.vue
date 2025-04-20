<template>
    <div class="flex flex-wrap text-sm">

        <div v-for="author in props.authors" :key="author.accno">
            
            <span >{{author.attributes.find(a => a.name === 'Name')?.value}}</span> 
         
            <span ><sup>{{ getAffiliationIndex(author) }}</sup></span>
            
            <span v-if="author.attributes.find(a => a.name === 'ORCID')">
                <a :href="getORCIDUrl(author.attributes.find(a => a.name === 'ORCID')?.value)" target="_blank"
                    rel="noopener noreferrer"
                    class="text-xs font-medium text-gray-800 hover:bg-gray-100">
                    <span class="text-gray-500">🔗</span>
                    <span class="uppercase font-semibold text-gray-600">
                        ORCID
                    </span>
                </a>
            </span>
            
            <span class="ml-4"></span>

        </div>
    </div>
</template>

<script setup lang="ts">
import type { StudySection } from '@/types/study';

const props = defineProps<{
    authors: StudySection[],
    affiliationsIndexes: Map<string, number>
}>()

function getAffiliationIndex(author: StudySection): string {
    let affiliationIndex = ""
    const affiliationValue: string = author.attributes.find(a => a.name === 'affiliation')?.value || ''

    if (affiliationValue) {
        if (props.affiliationsIndexes.get(affiliationValue)) {
            affiliationIndex = String(props.affiliationsIndexes.get(affiliationValue))
        }
    }
    return affiliationIndex
}

function getORCIDUrl(id: string | undefined): string {
    return `https://doi.org/${id}`
}

</script>