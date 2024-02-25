package com.example.repositorymicroservice.search.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Branch {
  private String branchName;
  private String lastCommitSha;
}
