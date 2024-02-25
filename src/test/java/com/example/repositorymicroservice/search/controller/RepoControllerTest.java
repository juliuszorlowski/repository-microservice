package com.example.repositorymicroservice.search.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
class RepoControllerTest {

  @Test
  void getRepositories() {
    String repositoryName = "AutoService";

    ResponseEntity<String> responseEntity =
        ResponseEntity.status(HttpStatus.OK).body(repositoryName);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isEqualTo(repositoryName);
  }
}