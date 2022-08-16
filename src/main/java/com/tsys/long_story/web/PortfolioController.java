package com.tsys.long_story.web;

import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.service.local.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller("portfolio")
public class PortfolioController {

  private static final Logger LOG = Logger.getLogger(com.tsys.long_story.web.PortfolioController.class.getName());

  private final PortfolioService service;

  @Autowired
  public PortfolioController(PortfolioService service) {
    this.service = service;
  }

  @RequestMapping
  public String index() {
    LOG.info("==> Serving index.html");
    return "index.html";
  }

  @GetMapping("/portfolio")
  public ResponseEntity findAll() {
    LOG.info(() -> String.format("==> Getting All Portfolios"));
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/portfolio/{id}")
  public ResponseEntity<Portfolio> portfolio(@PathVariable String id) {
    LOG.info(() -> String.format("==> Getting Portfolio with id = %s", id));
    return ResponseEntity.of(service.findById(id));
  }

  @GetMapping("/portfolio/networth")
  public ResponseEntity totalNetWorth() {
    LOG.info(() -> String.format("==> Getting Portfolio Networth"));
    return ResponseEntity.ok(service.totalNetWorth());
  }
}