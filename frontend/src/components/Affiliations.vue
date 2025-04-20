<template>
    <div class="flex flex-wrap text-xs text-gray-700">

        <div v-for="affiliation in props.affiliations" :key="affiliation.accno">
            <span><sup>{{ getAffiliationIndex(affiliation) }}</sup></span>
            <span>{{affiliation.attributes.find(a => a.name === 'Name')?.value}}</span>
            <span class="ml-4"></span>
        </div>
    </div>
</template>
<script setup lang="ts">
import type { StudySection } from '@/types/study';

const props = defineProps<{
    affiliations: StudySection[],
    affiliationsIndexes: Map<string, number>
}>()

function getAffiliationIndex(affiliation: StudySection): string {
    let affiliationIndex = ""
    const affiliationValue: string = affiliation.accno || ''

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