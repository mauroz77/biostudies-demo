package org.biostudies_demo.api.service;

import lombok.extern.slf4j.Slf4j;
import org.biostudies_demo.api.dto.StudyDTO;
import org.biostudies_demo.api.mappers.StudyMapper;
import org.biostudies_demo.api.model.Study;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
@Slf4j
public class BioStudiesService {

    private StudyMapper studyMapper;

    @Value("${biostudies.api.baseUrl:https://www.ebi.ac.uk/biostudies/api}")
    private String apiBaseUrl;

    @Value("${biostudies.ftp.baseUrl:https://ftp.ebi.ac.uk/pub/databases/biostudies}")
    private String ftpBaseUrl;

    private final RestTemplate restTemplate;

    public BioStudiesService(StudyMapper studyMapper, RestTemplate restTemplate) {
        this.studyMapper = studyMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches a study from the BioStudies API by accession number
     *
     * @param accession The study accession number (e.g., S-EPMC11663195)
     * @return The study object
     */
    public Study getStudyByAccession(String accession) {
        String url = apiBaseUrl + "/v1/studies/" + accession;
        log.info("Fetching study from API: {}", url);
        Study study;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String ftpLink = (String) response.getBody().get("ftpHttp_link");
                StudyDTO studyDTO = fetchFromJsonFileUrl(jsonFileUrl(ftpLink, accession));
                study = studyMapper.studyDTOToStudy(studyDTO);
                return study;
            }
        } catch (RestClientException e) {
            log.warn("Exception fetching from API: {}", e.getMessage());
            return fetchFromJsonFile(accession);
        }
        return null;
    }

    private String jsonFileUrl(String ftpLink, String accId) {
        return ftpLink + accId + ".json";
    }

    /**
     * Fetches a study directly from the JSON file URL
     *
     * @param accession The study accession number (e.g., S-EPMC11663195)
     * @return The study object or null if not found
     */
    public Study fetchFromJsonFile(String accession) {
        // Extract the numeric part after S-EPMC
        String prefix = extractPrefix(accession);
        String number = extractNumber(accession);

        if (number == null) {
            log.error("Invalid accession format: {}", accession);
            return null;
        }

        // Create directory number by taking first few digits
        // Format: For S-EPMC10268417, directory is 102 (first 3 digits)
        String dirNumber = number.length() > 3 ? number.substring(0, 3) : number;

        String jsonUrl = String.format("%s/%s/%s/%s/%s.json",
                ftpBaseUrl, prefix, dirNumber, accession, accession);

        log.info("Fetching study from JSON file: {}", jsonUrl);

        try {
            // Validate URL
            new URL(jsonUrl);

            return restTemplate.getForObject(jsonUrl, Study.class);
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", jsonUrl, e);
            return null;
        } catch (RestClientException e) {
            log.error("Failed to fetch JSON file: {}", jsonUrl, e);
            return null;
        }
    }

    /**
     * Fetches a study directly from a full JSON file URL
     *
     * @param jsonFileUrl The complete URL to the JSON file
     * @return The study object or null if not found
     */
    public StudyDTO fetchFromJsonFileUrl(String jsonFileUrl) {
        log.info("Fetching study from direct URL: {}", jsonFileUrl);

        try {
            // Validate URL
            new URL(jsonFileUrl);

            return restTemplate.getForObject(jsonFileUrl, StudyDTO.class);
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", jsonFileUrl, e);
            return null;
        } catch (RestClientException e) {
            log.error("Failed to fetch JSON file: {}", jsonFileUrl, e);
            return null;
        }
    }

    private String extractPrefix(String accession) {
        if (accession != null && accession.contains("-")) {
            return accession.split("-")[1];
        }
        return "";
    }

    private String extractNumber(String accession) {
        if (accession != null && accession.startsWith("S-EPMC")) {
            return accession.substring(6);
        }
        return null;
    }
}
