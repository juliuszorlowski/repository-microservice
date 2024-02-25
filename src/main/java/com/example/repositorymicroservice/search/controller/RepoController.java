package com.example.repositorymicroservice.search.controller;

import com.example.repositorymicroservice.search.service.RepoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class RepoController {
  private final RepoService repoService;

  public RepoController(RepoService repoService) {
    this.repoService = repoService;
  }

  @GetMapping(value = "/{username}", consumes="application/json")
  public ResponseEntity<String> getRepositories(@PathVariable String username) {
    return repoService.getRepos(username);
  }
}
