import axios from 'axios'
import type { StudyHit, StudyDetail } from '@/types/study'

const useMock = false

// Default to localhost:8080 for development, but allow override via env variables
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// Create an axios instance with the base URL
const apiClient = axios.create({
  baseURL: API_BASE_URL
})

export async function searchStudies(query: string): Promise<StudyHit[]> {
  if (useMock) {
    const res = await fetch('/mock/search-results.json')
    const data = await res.json()
    return data.hits
  } else {
    console.log('will call url', `/api/search?q=${encodeURIComponent(query)}`);
    
    const { data } = await apiClient.get(`/api/search?q=${encodeURIComponent(query)}`)
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
    console.log('Get details for', accno);
    
    const { data } = await apiClient.get(`/api/study/${accno}`)
    console.log("DATA::::", data);
    
    return data
  }
}