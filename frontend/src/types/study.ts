export interface StudyHit {
    accession: string
    type: string
    title: string
    author: string
    links: number
    files: number
    release_date: string
    views: number
    isPublic: boolean
  }
  
  export interface StudyAttribute {
    name: string
    value: string
    reference?: boolean
  }
  
  export interface StudyFile {
    path: string
    size: number
    type: string
  }
  
  export interface StudyLink {
    url: string
    attributes: StudyAttribute[]
  }
  
  export interface StudySection {
    accno?: string
    type: string
    attributes: StudyAttribute[]
    files?: StudyFile[]
    links?: StudyLink[][]
    subsections?: StudySection[]
  }
  
  export interface StudyDetail {
    accno: string
    attributes: StudyAttribute[]
    section?: StudySection
    links?: StudyLink[][]
    type: string
  }