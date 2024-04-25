package com.ssg.kms.mapping;

import com.ssg.kms.post.Post;

public interface GetPostMapping {
	Post getPost();	
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
