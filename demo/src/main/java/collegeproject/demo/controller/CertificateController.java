package collegeproject.demo.controller;

import collegeproject.demo.dto.CertificateRequest;
import collegeproject.demo.dto.CertificateResponse;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/generate")
    public ResponseEntity<CertificateResponse> generate(@AuthenticationPrincipal UserPrincipal principal,
                                                        @RequestBody CertificateRequest req) {
        return ResponseEntity.ok(
                CertificateResponse.from(certificateService.generate(principal.getId(), req))
        );
    }

    @GetMapping
    public ResponseEntity<List<CertificateResponse>> all(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(
                certificateService.getAll(principal.getId())
                        .stream()
                        .map(CertificateResponse::from)
                        .toList()
        );
    }
}