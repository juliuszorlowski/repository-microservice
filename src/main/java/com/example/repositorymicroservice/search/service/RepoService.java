package com.example.repositorymicroservice.search.service;

import com.example.repositorymicroservice.search.model.Branch;
import com.example.repositorymicroservice.search.model.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RepoService {
  private final RestTemplate restTemplate;

  public RepoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<String> getRepos(String username) throws HttpClientErrorException{
    String url = "https://api.github.com/users/" + username + "/repos";
    try {
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
      if (response.getStatusCode() == HttpStatus.OK) {
        return new ResponseEntity<>(getReposList(response.getBody()), HttpStatus.OK);
      } else {
        return response;
      }
    } catch (HttpClientErrorException e) {
      JSONObject json = new JSONObject();
      json.put("status", "404 Not Found");
      json.put("message", "Username not found");
      return new ResponseEntity<>(json.toJSONString(), HttpStatus.NOT_FOUND);
    }
  }

  public String getReposList(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonArray = objectMapper.readTree(json);
      List<Repo> repoList = mapRepos(jsonArray);

      return new Gson().toJson(repoList);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private ResponseEntity<String> getBranches(String branchesUrl) {
    return restTemplate.getForEntity(branchesUrl, String.class);
  }

  private List<Repo> mapRepos(JsonNode jsonArray) {
    List<Repo> repoList = new ArrayList<>();
    for (JsonNode element : jsonArray) {
      if (!element.at("/fork").booleanValue()) {
        Repo repo = new Repo();
        repo.setRepositoryName((element.at("/name").asText()));
        repo.setOwnerLogin((element.at("/owner/login").asText()));
        String branchesUrl = (element.at("/branches_url").asText());
        ResponseEntity<String> branchesResponse = getBranches(cleanUpUrl(branchesUrl));
        if (branchesResponse.getStatusCode() == HttpStatus.OK) {
          repo.setBranchList(mapBranches(branchesResponse.getBody()));
        }
        repoList.add(repo);
      }
    }
    return repoList;
  }

  private List<Branch> mapBranches(String json) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode jsonArray = mapper.readTree(json);
      List<Branch> branchList = new ArrayList<>();
      for (JsonNode element : jsonArray) {
        Branch branch = new Branch();
        branch.setBranchName(element.at("/name").asText());
        branch.setLastCommitSha(element.at("/commit/sha").asText());
        branchList.add(branch);
      }
      return branchList;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private String cleanUpUrl(String url) {
    return url.substring(0, url.indexOf('{'));
  }
}
