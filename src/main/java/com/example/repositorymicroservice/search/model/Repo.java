package com.example.repositorymicroservice.search.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Repo {
  private String repositoryName;
  private String ownerLogin;
  private List<Branch> branchList;
}
