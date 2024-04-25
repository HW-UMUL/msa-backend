package com.ssg.kms.post;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.alarm.post.PostAlarmService;
import com.ssg.kms.alarm.reply.ReplyAlarmService;
import com.ssg.kms.like.post.PostLikeRepository;
import com.ssg.kms.like.reply.ReplyLikeRepository;
import com.ssg.kms.reply.ReplyRepository;
import com.ssg.kms.star.post.PostStarRepository;
import com.ssg.kms.tag.Tag;
import com.ssg.kms.tag.TagPost;
import com.ssg.kms.tag.TagPostRepository;
import com.ssg.kms.tag.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostStarRepository postStarRepository;
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    
    private final PostAlarmService postAlarmService;
    private final ReplyAlarmService replyAlarmService;
    
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;
    
    private WebClient webClient = WebClient
            .builder()                
            .baseUrl("http://52.78.171.178:8088")
            .defaultHeaders(httpHeaders -> {
                httpHeaders.addAll(createHeaders());
            })
            .build();
    
    public HttpHeaders createHeaders() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//    	headers.add(HttpHeaders.AUTHORIZATION, "Bearer 11%s".formatted(authentication.getCredentials()));
    	
    	return headers;
    }
    
    public Object getToken() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return authentication.getCredentials();
    }
    
    public Object getUsername() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return authentication.getName();
    }
    // 메인
    @Transactional
    public Post createPost(PostDTO postDto, Long userId) {
    	
    	if(!postDto.getUsername().equals(getUsername())) return null;
    	Post post = Post.builder()
    			.postTitle(postDto.getPostTitle())
    			.postContent(postDto.getPostContent())
    			.postCreateDt(new Date())
    			.userId(userId)
    			.username(postDto.getUsername())
    			.tableId(1L)
    			.isPublic(true)
    			.build();
    	
    	createTags(postDto, post);
    	
		return post;
    }
    
    // 나중에
    // 테이블
    @Transactional
    public Post createPost(Long tableId, PostTableUserIdDTO postTableUserIdDto, Long userId) throws Exception {    	    	

    	if(!postTableUserIdDto.getUsername().equals(getUsername())) return null;
    	String hasCreateAuthority = webClient.get()
                .uri("/api/tableuser/hasCreateAuthority/%s/%s".formatted(tableId + "", userId + ""))
                .accept(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(getToken()))
                .retrieve()
                .bodyToMono(String.class) // 응답 본문을 Mono<String>으로 변환합니다.
                .block(); // Mono에서 결과를 블록킹하고 응답 본문을 가져옵니다.    	
    	
    	if(hasCreateAuthority.equals("false")) return null;
    	
    	    	
    	String responseBody = webClient.get()
                .uri("/api/table/tableInfo/%s/%s".formatted(tableId + "", userId + ""))
                .accept(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(getToken()))
                .retrieve()
                .bodyToMono(String.class) // 응답 본문을 Mono<String>으로 변환합니다.
                .block(); // Mono에서 결과를 블록킹하고 응답 본문을 가져옵니다.

    	ObjectMapper mapper = new ObjectMapper();
    	
    	Map<String, Object> map = mapper.readValue(responseBody, Map.class);
    	

    	
    	
    	Post post = Post.builder()
    			.postTitle(postTableUserIdDto.getPostTitle())
    			.postContent(postTableUserIdDto.getPostContent())
    			.postCreateDt(new Date())
    			.userId(userId)
    			.username(postTableUserIdDto.getUsername())
    			.tableId(tableId)
    			.isPublic(map.get("isPublic").equals(true))
    			.build();
    	    	
    	createTags2(postTableUserIdDto, post);
    	
/// 알람 /////////////   	
    	List<Long> tableUserIds = postTableUserIdDto.getUserIds();
    	postAlarmService.createAlarm(post, tableUserIds, userId);
/////////////////////    	
		return post;
    }
    
    @Transactional(readOnly = true)
    public Post readPost(Long postId, Long userId) {
		return postRepository.findById(postId).get();
    }

    @Transactional(readOnly = true)
    public List<Post> readAllPost(Long userId) {
		return postRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Post> readPublicPost(Long tableId, Long userId) {
		return postRepository.findAllByTableId(tableId);
    }
    
    @Transactional(readOnly = true)
    public List<Post> readTablePost(Long tableId, Long userId) {    	
    	
    	List<Post> posts = postRepository.findAllByTableId(tableId);
    	List<Long> postIds = postRepository.findPostIdAllByTableId(tableId);
    	
    	postAlarmService.deleteAlarm(postIds, userId);
   
		return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> readAllTablePost(AllTablePostIdsDTO allTablePostIdsDTO, Long userId) {    	
    	
    	List<Post> posts = postRepository.findAllByTableIdIn(allTablePostIdsDTO.getTableIds());
   
		return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> readMyPost(Long userId) {
    	
    	List<Post> posts = postRepository.findAllByUserId(userId);
    	List<Long> postIds = postRepository.findIdAllByUserId(userId);
    	
    	replyAlarmService.deleteAlarm(postIds, userId);

		return posts;
    }
    
    // readTablePost 사용하는 걸로 ...
    @Transactional(readOnly = true)
    public List<Post> readAllTablePost(Long userId) throws Exception {
    	
    	String responseBody = webClient.get()
                .uri("/api/tableuser/getMyTableIds/%s".formatted(userId + ""))
                .accept(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(getToken()))
                .retrieve()
                .bodyToMono(String.class) // 응답 본문을 Mono<String>으로 변환합니다.
                .block(); // Mono에서 결과를 블록킹하고 응답 본문을 가져옵니다.
    	
    	if(!responseBody.equals("[]")) {
        	List<Long> tableIds = convertStringToLongList(responseBody);    		
        	return postRepository.findAllByTableIdIn(tableIds);
    	} else {
    		return null;
    	}    	
    }
   
    // 내가 작성한 공개 게시글.
    // 
    @Transactional(readOnly = true)
    public List<Post> readUserPublicPost(Long userId, Long tableId) {
    	
		return postRepository.findAllByTableIdAndUserId(tableId, userId);
    }



    @Transactional
    public Post updatePost(Long postId, PostDTO postDto, Long userId) {
    	Post post = postRepository.findById(postId).get();
    	
    	post.setPostTitle(postDto.getPostTitle());
    	post.setPostContent(postDto.getPostContent());
    	post.setPostCreateDt(new Date());
    	
    	// tag post 연결된거 지우기
    	tagPostRepository.deleteAllByPostPostId(postId);
    	
    	// 새로 생성
    	createTags(postDto, post);

    	return post;
    }

    @Transactional
    public Post deletePost(Long postId, Long userId) {
    	Post post = postRepository.findById(postId).get();

    	replyLikeRepository.deleteAllByReplyPostPostId(post.getPostId());    	
    	replyAlarmService.deleteAlarmByPostId(postId);
    	replyRepository.deleteAllByPostPostId(post.getPostId());
    	
    	postLikeRepository.deleteAllByPostPostId(post.getPostId());
    	postStarRepository.deleteAllByPostPostId(post.getPostId());
    	tagPostRepository.deleteAllByPostPostId(post.getPostId());
    	
    	postAlarmService.deleteAlarmByPostId(postId);
    	
    	postRepository.deleteById(postId);
    	

    	return post;
    }

    @Transactional
    public List<Post> searchPost(String searchKeyword, Long userId){
        return postRepository.findAllByPostTitleContaining(searchKeyword);
    }    
    
    @Transactional
	public List<String> searchAllPost(Long userId) {
		return postRepository.findAllPostTitles();
	}

    // 태그 분리
    public String[] splitTag(String tags){
    	String sepStr = "MQcc44ZY687GhWB2rzVc";
    	tags = tags.replaceAll(",", " ").replaceAll("\\s+|,", sepStr);    	
    	String[] tagArr = tags.split(sepStr);
    	return tagArr;
    }
    
    // 태그 생성
    public void createTags(PostDTO postDto, Post post){
    	
    	String[] strTags = splitTag(postDto.getTag());
    	HashSet<String> strTagsSet = new HashSet<>(Arrays.asList(strTags));
    	List<Tag> tags = new ArrayList<>();
    	Set<TagPost> tagPosts = new HashSet<>();
    	
    	for(String str : strTagsSet) {
    		    		
    		Optional<Tag> foundTag = tagRepository.findByName(str);
    		Tag tag;
    		if(foundTag.isEmpty()) {
        		tag = Tag.builder()
        				.name(str)
        				.build();    			
        		tags.add(tag);
    		} else {
    			tag = foundTag.get();
    		}
    		
    		
    		TagPost tagPost = TagPost.builder()
    				.post(post)
    				.tag(tag)
    				.build();

    		tagPosts.add(tagPost);
    	}
    	
    	postRepository.save(post);
    	tagRepository.saveAll(tags);
    	tagPostRepository.saveAll(tagPosts);
    	    	
    }
    
    // 태그 생성
    public void createTags2(PostTableUserIdDTO postDto, Post post){
    	
    	String[] strTags = splitTag(postDto.getTag());
    	HashSet<String> strTagsSet = new HashSet<>(Arrays.asList(strTags));
    	List<Tag> tags = new ArrayList<>();
    	Set<TagPost> tagPosts = new HashSet<>();
    	
    	for(String str : strTagsSet) {
    		    		
    		Optional<Tag> foundTag = tagRepository.findByName(str);
    		Tag tag;
    		if(foundTag.isEmpty()) {
        		tag = Tag.builder()
        				.name(str)
        				.build();    			
        		tags.add(tag);
    		} else {
    			tag = foundTag.get();
    		}
    		
    		
    		TagPost tagPost = TagPost.builder()
    				.post(post)
    				.tag(tag)
    				.build();

    		tagPosts.add(tagPost);
    	}
    	
    	postRepository.save(post);
    	tagRepository.saveAll(tags);
    	tagPostRepository.saveAll(tagPosts);
    	    	
    }
    
    
    
    
    public List<Long> convertStringToLongList(String str) {
        // 대괄호와 공백을 제거하고 쉼표로 구분된 문자열 배열로 분할합니다.
        String[] tokens = str.replaceAll("\\[|\\]|\\s", "").split(",");
        
        // 문자열 배열을 Long 리스트로 변환합니다.
        List<Long> longList = new ArrayList<>();
        for (String token : tokens) {
            // Long으로 변환하여 리스트에 추가합니다.
            longList.add(Long.parseLong(token.trim()));
        }
        
        return longList;
    }
    
    
    
    
    
    // page
    @Transactional(readOnly = true)
    public Page<Post> readTablePostPage(Long tableId, Pageable pageable) {
		return postRepository.findAllByTableId(tableId, pageable);
    }
    
	// 추천
    @Transactional(readOnly = true)
    public List<Post> findTop5PostsByLikes(Long userId) {
        List<Post> topPostsLikes = postRepository.findByDistinctPostOrderByPostDesc();
        if (topPostsLikes.size() < 5) {
            return topPostsLikes;
        } else {
            return topPostsLikes.subList(0, 5);
        }
    }
	
	// 즐찾
    @Transactional(readOnly = true)
    public List<Post> findTop5PostsByStars(Long userId) {
        List<Post> topPostsStars = postRepository.findByDistinctPostOrderByPostDesc2();
        if (topPostsStars.size() < 5) {
            return topPostsStars;
        } else {
            return topPostsStars.subList(0, 5);
        }
    }
	
	// 최신
	@Transactional (readOnly = true)
	public List<Post> findTop5PostsByDate(Long userId) {
		return postRepository.findTop5ByOrderByPostCreateDtDesc();
	}
	
	////
    // 臾댄븳�뒪�겕濡� (10媛쒖뵫 �굹�삤寃�)
    @Transactional(readOnly = true)
    public Page<Post> readPublicPost2(Long tableId, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postCreateDt").descending());
        return postRepository.findByTableId(tableId, pageable);
    }
    
    // 理쒖떊�닚
    public List<PostRecentDTO> findLatestPost() {
        return postRepository.findLatestPost();
    }
	
}
