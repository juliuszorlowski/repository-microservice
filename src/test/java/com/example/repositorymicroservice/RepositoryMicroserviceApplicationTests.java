package com.example.repositorymicroservice;

import com.example.repositorymicroservice.search.controller.RepoController;
import com.example.repositorymicroservice.search.service.RepoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RepositoryMicroserviceApplicationTests {

  @Autowired
  private RepoController repoController;

  @Autowired
  private RepoService repoService;

  @Test
  void contextLoads() {
    assertThat(repoController).isNotNull();
    assertThat(repoService).isNotNull();
  }
}
