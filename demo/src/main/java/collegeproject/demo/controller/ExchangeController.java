package collegeproject.demo.controller;

import collegeproject.demo.dto.ExchangeResponse;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/offer")
    public ResponseEntity<ExchangeResponse> offer(@AuthenticationPrincipal UserPrincipal principal,
                                                  @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                ExchangeResponse.from(exchangeService.save(
                        principal.getId(), "offer", body.get("skill"),
                        body.get("description"), null
                ))
        );
    }

    @PostMapping("/request")
    public ResponseEntity<ExchangeResponse> request(@AuthenticationPrincipal UserPrincipal principal,
                                                    @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                ExchangeResponse.from(exchangeService.save(
                        principal.getId(), "request", body.get("skill"),
                        body.get("description"), null
                ))
        );
    }

    @PostMapping("/connect")
    public ResponseEntity<ExchangeResponse> connect(@AuthenticationPrincipal UserPrincipal principal,
                                                    @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                ExchangeResponse.from(exchangeService.save(
                        principal.getId(), "connection", body.get("skill"),
                        null, body.get("partnerName")
                ))
        );
    }

    @GetMapping
    public ResponseEntity<List<ExchangeResponse>> mine(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(
                exchangeService.getMine(principal.getId())
                        .stream()
                        .map(ExchangeResponse::from)
                        .toList()
        );
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Map<String, Object>>> matches(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(exchangeService.getMatches(principal.getId()));
    }
}