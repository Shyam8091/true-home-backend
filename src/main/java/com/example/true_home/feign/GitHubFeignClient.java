package com.example.true_home.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GitHubFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/repos/{owner}/{repo}/contents/{path}")
    ResponseEntity<Map<String, Object>> uploadFile(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo,
            @PathVariable("path") String path,
            @RequestBody Map<String, Object> fileData,
            @RequestHeader("Authorization") String authorization
    );
}