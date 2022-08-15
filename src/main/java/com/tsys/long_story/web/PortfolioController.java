package com.tsys.long_story.web;

import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.service.local.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
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

  @GetMapping("/portfolio/networth")
  public ResponseEntity networth() {
    LOG.info(() -> String.format("==> Getting Portfolio Networth"));
    return ResponseEntity.ok(service.networth());
  }

  @GetMapping(value = "/portfolio/track/{id}", produces = "application/json")
  public ResponseEntity<Portfolio> track(@PathVariable String id) {
    LOG.info(() -> String.format("==> Getting Updated Portfolio with id = %s", id));
    var startTime = System.currentTimeMillis();
    Optional<Portfolio> updated = service.track(id);
    var timeTaken = System.currentTimeMillis() - startTime;
    LOG.info(() -> String.format("==> Getting Updated Portfolio took %d (ms)", timeTaken));
    return ResponseEntity.of(updated);
  }

//  @GetMapping("/portfolio/{id}")
//  public ResponseEntity<Portfolio> portfolio(@PathVariable String id) {
//    LOG.info(() -> String.format("==> Getting Portfolio with id = %s", id));
//    return ResponseEntity.of(service.findById(id));
//  }

//  @GetMapping("/portfolio/networth/{id}")
//  public ResponseEntity<Double> networth(@PathVariable String id) {
//    LOG.info(() -> String.format("==> Getting Portfolio Networth for id = %s", id));
//    return ResponseEntity.of(service.findById(id).map(portfolio -> portfolio.networth()));
//  }

//  @PatchMapping(value = "/portfolio/{id}/addHolding", produces = "application/json", consumes = "application/json")
//  public ResponseEntity<Portfolio> addHolding(@PathVariable String id, @RequestBody Holding holding) {
//    LOG.info(() -> String.format("Adding holding %s to Portfolio with id = %s", holding, id));
//    return ResponseEntity.of(service.addHolding(id, holding));
//  }

//  @DeleteMapping("/portfolio/{id}")
//  public ResponseEntity<Boolean> deleteById(@PathVariable String id) {
//    LOG.info(() -> String.format("Deleting Portfolio with id = %s", id));
//    return ResponseEntity.ok(service.deleteById(id));
//  }
//
//  @PostMapping(value = "/portfolio", consumes = "application/json")
//  public ResponseEntity<Portfolio> add(@RequestBody Portfolio portfolio) {
//    if (enableEndpoint == true)
//      throw new UnsupportedOperationException("Not Implemented!");
//
//    LOG.info(() -> String.format("Creating Portfolio  %s", portfolio));
//    return ResponseEntity.ok(service.add(portfolio));
//  }
}