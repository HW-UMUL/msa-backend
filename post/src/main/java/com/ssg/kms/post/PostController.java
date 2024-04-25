package com.ssg.kms.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;
	
	/////////////////////////////////////////////////
	// Own
	/////////////////////////////////////////////////
	@PostMapping("/create/{userId}")
	public ResponseEntity createPost(@Valid @RequestBody PostDTO PostDto, @PathVariable Long userId) {
		postService.createPost(PostDto, userId);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/read/{postId}/{userId}")
    public ResponseEntity<Post> readPost(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.readPost(postId, userId));
    }
	
	@GetMapping("/read/{userId}")
    public ResponseEntity<List<Post>> readAllPost(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.readAllPost(userId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<Post>> readMyPost(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.readMyPost(userId));
    }

	@GetMapping("/read/public/{tableId}/{userId}")
    public ResponseEntity<List<Post>> readPublicPost(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.readPublicPost(tableId, userId));
    }
	
	@PutMapping("/update/{postId}/{userId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDto, @PathVariable Long userId) {
		postService.updatePost(postId, postDto, userId);
        return ResponseEntity.ok(null);
    }
	
	@DeleteMapping("/delete/{postId}/{userId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
		postService.deletePost(postId, userId);
        return ResponseEntity.ok(null);
    }
	
	@GetMapping("/search/{searchKeyword}/{userId}")
    public ResponseEntity<List<Post>> searchPost(@PathVariable String searchKeyword, @PathVariable Long userId) {        
        return ResponseEntity.ok(postService.searchPost(searchKeyword, userId));
    }
	
	@GetMapping("/search/all/{userId}")
    public ResponseEntity<List<String>> searchAllPost(@PathVariable Long userId) {        
        return ResponseEntity.ok(postService.searchAllPost(userId));
    }
	/////////////////////////////////////////////////
	// Table
	/////////////////////////////////////////////////
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@PostMapping("/create/{tableId}/{userId}")
	public ResponseEntity<Post> createTablePost(@PathVariable Long tableId, @Valid @RequestBody PostTableUserIdDTO postTableUserIdDto, @PathVariable Long userId) throws Exception {
		postService.createPost(tableId, postTableUserIdDto, userId);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/read/table/{tableId}/{userId}")
    public ResponseEntity<List<Post>> readTablePost(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.readTablePost(tableId, userId));
    }
		
	@PostMapping("/read/table/{userId}")
    public ResponseEntity<List<Post>> readAllTablePost(@RequestBody AllTablePostIdsDTO allTablePostIdsDto, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.readAllTablePost(allTablePostIdsDto, userId));
    }


	@GetMapping("/read/table/all/{userId}")
	public ResponseEntity<List<Post>> readAllTablePost(@PathVariable Long userId) throws Exception{
		return ResponseEntity.ok(postService.readAllTablePost(userId));
	}
	
	/////////////////////////////////////////////////
	// Other
	/////////////////////////////////////////////////

	@GetMapping("/read/user/{otherUserId}/{tableId}")
    public ResponseEntity<List<Post>> readUserPublicPost(@PathVariable Long otherUserId, @PathVariable Long tableId) {
        return ResponseEntity.ok(postService.readUserPublicPost(otherUserId, tableId));
    }
	
	/////////////// 추가
	// page
	@GetMapping("/read/page/{tableId}")
	public ResponseEntity<Page<Post>> readTablePostPage(@PathVariable Long tableId, Pageable pageable) {
		return ResponseEntity.ok(postService.readTablePostPage(tableId, pageable));
	}
	
	// 추천
	@GetMapping("read/top5Posts/likes/{userId}")
	public ResponseEntity<List<Post>> findtop5PostsLikes(@PathVariable Long userId) {
		return ResponseEntity.ok(postService.findTop5PostsByLikes(userId));
	}
	
	// 즐찾
	@GetMapping("read/top5Posts/stars/{userId}")
	public ResponseEntity<List<Post>> findtop5PostsStars(@PathVariable Long userId) {
		return ResponseEntity.ok(postService.findTop5PostsByStars(userId));
	}
	
	// 최신
	@GetMapping("read/top5Posts/Date/{userId}")
	public ResponseEntity<List<Post>> findtop5PostsDate(@PathVariable Long userId) {
		return ResponseEntity.ok(postService.findTop5PostsByDate(userId));
	}
	
	
	////
	
	// 臾댄븳�뒪�겕濡� (10媛쒖뵫)
	@GetMapping("/read/publics/{tableId}/{userId}")
	public ResponseEntity<Page<Post>> readPublicPost(@PathVariable Long tableId, @PathVariable Long userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Page<Post> posts = postService.readPublicPost2(tableId, userId, page, size);
		return ResponseEntity.ok(posts);
	}
	
	// 理쒖떊�닚
	@GetMapping("/recent")
	public List<PostRecentDTO> getLatestLogsPerWiki() {
		return postService.findLatestPost();
	}
	


}
