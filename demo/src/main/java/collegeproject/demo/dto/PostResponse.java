package collegeproject.demo.dto;

import collegeproject.demo.entity.Comment;
import collegeproject.demo.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String authorName;
    private String avatar;
    private String title;
    private String text;
    private String skill;
    private Integer likes;
    private List<CommentItem> comments;
    private LocalDateTime createdAt;

    public static PostResponse from(Post p) {
        if (p == null) return null;
        PostResponse r = new PostResponse();
        r.setId(p.getId());
        r.setAuthorName(p.getAuthorName());
        r.setAvatar(p.getAvatar());
        r.setTitle(p.getTitle());
        r.setText(p.getText());
        r.setSkill(p.getSkill());
        r.setLikes(p.getLikes());
        r.setCreatedAt(p.getCreatedAt());
        r.setComments(
                p.getComments() == null ? List.of() :
                        p.getComments().stream().map(CommentItem::from).toList()
        );
        return r;
    }

    @Data
    public static class CommentItem {
        private String user;
        private String text;
        private LocalDateTime createdAt;

        public static CommentItem from(Comment c) {
            CommentItem i = new CommentItem();
            i.setUser(c.getUser());
            i.setText(c.getText());
            i.setCreatedAt(c.getCreatedAt());
            return i;
        }
    }
}