package collegeproject.demo.service;

import collegeproject.demo.entity.Comment;
import collegeproject.demo.entity.Post;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.PostRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityService {

    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommunityService(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public List<Post> getAllPosts() {
        return postRepo.findAllByOrderByCreatedAtDesc();
    }

    public Post createPost(Long userId, String title, String text, String skill, String avatar) {
        User user = userRepo.findById(userId).orElseThrow();

        Post post = Post.builder()
                .author(user)
                .authorName(user.getUsername())
                .avatar(avatar == null ? "👤" : avatar)
                .title(title == null ? "My Skill Buddy Update" : title)
                .text(text)
                .skill(skill == null
                        ? (user.getLearningSkills().isEmpty() ? "General"
                            : user.getLearningSkills().iterator().next())
                        : skill)
                .build();

        return postRepo.save(post);
    }

    @Transactional
    public Post addComment(Long postId, String username, String text) {
        Post post = postRepo.findById(postId).orElseThrow();

        Comment comment = Comment.builder()
                .post(post)
                .user(username)
                .text(text)
                .build();

        post.getComments().add(comment);
        return postRepo.save(post);
    }

    @Transactional
    public Post likePost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        post.setLikes(post.getLikes() + 1);
        return postRepo.save(post);
    }
}