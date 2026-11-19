package collegeproject.demo.controller;

import collegeproject.demo.dto.PostResponse;
import collegeproject.demo.entity.Post;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> all() {
        return ResponseEntity.ok(
                communityService.getAllPosts()
                        .stream()
                        .map(PostResponse::from)
                        .toList()
        );
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> create(@AuthenticationPrincipal UserPrincipal principal,
                                               @RequestBody Map<String, String> body) {
        Post post = communityService.createPost(
                principal.getId(),
                body.get("title"),
                body.get("text"),
                body.get("skill"),
                body.get("avatar")
        );
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<PostResponse> like(@PathVariable Long id) {
        return ResponseEntity.ok(PostResponse.from(communityService.likePost(id)));
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<PostResponse> comment(@AuthenticationPrincipal UserPrincipal principal,
                                                @PathVariable Long id,
                                                @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                PostResponse.from(
                        communityService.addComment(id, principal.getUsername(), body.get("text"))
                )
        );
    }
}