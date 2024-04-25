package com.ssg.kms.mapping;

import java.util.Objects;

import com.ssg.kms.post.Post;

public class GetPostMappingImpl implements GetPostMapping {
    private Post post;

    public GetPostMappingImpl(Post post) {
        this.post = post;
    }

    @Override
    public Post getPost() {
        return post;
    }

    // postId를 기준으로 equals와 hashCode 메서드 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPostMappingImpl that = (GetPostMappingImpl) o;
        return Objects.equals(post.getPostId(), that.post.getPostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(post.getPostId());
    }
}
