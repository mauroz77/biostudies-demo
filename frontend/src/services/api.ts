import axios from 'axios'
import type { StudyHit, StudyDetail } from '@/types/study'

const useMock = true

export async function searchStudies(query: string): Promise<StudyHit[]> {
  if (useMock) {
    const res = await fetch('/mock/search-results.json')
    const data = await res.json()
    return data.hits
  } else {
    const { data } = await axios.get(`/api/search?q=${encodeURIComponent(query)}`)
    return data.hits
  }
}

export async function getStudyDetail(accno: string): Promise<StudyDetail> {
  if (useMock) {
    // const res = await fetch(`/mock/detail-${accno}.json`)
    const res = await fetch(`/mock/detail.json`)
    const data = await res.json()
    return data
  } else {
    const { data } = await axios.get(`/api/study/${accno}`)
    console.log("DATA::::", data);
    
    return data
  }
}