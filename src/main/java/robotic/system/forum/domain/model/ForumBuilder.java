package robotic.system.forum.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.user.domain.model.Users;

public class ForumBuilder {
    private UUID id;
    private String title;
    private String description;
    private String codeSnippet;
    private ForumStatus status;
    private Users user;
    private Date creationDate;
    private Date editDate;
    private int voteCount;
    private List<ForumComment> comments;
    private List<Tag> tags;

    public ForumBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ForumBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ForumBuilder withCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
        return this;
    }

    public ForumBuilder withStatus(ForumStatus status) {
        this.status = status;
        return this;
    }

    public ForumBuilder withUser(Users user) {
        this.user = user;
        return this;
    }

    public ForumBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public ForumBuilder withEditDate(Date editDate) {
        this.editDate = editDate;
        return this;
    }

    public ForumBuilder withVoteCount(int voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public ForumBuilder withComments(List<ForumComment> comments) {
        this.comments = comments;
        return this;
    }

    public ForumBuilder withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Forum build() {
        Forum forum = new Forum();
        forum.setId(UUID.randomUUID());
        forum.setTitle(title);
        forum.setDescription(description);
        forum.setCodeSnippet(codeSnippet);
        forum.setStatus(status);
        forum.setUser(user);
        forum.setCreationDate(creationDate);
        forum.setEditDate(editDate);
        forum.setVoteCount(voteCount);
        forum.setComments(comments);
        forum.setTags(tags);
        return forum;
    }
}
