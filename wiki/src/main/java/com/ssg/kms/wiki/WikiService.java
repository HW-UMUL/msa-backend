package com.ssg.kms.wiki;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssg.kms.alarm.wiki.WikiAlarmService;
import com.ssg.kms.category.Category;
import com.ssg.kms.category.CategoryRepository;
import com.ssg.kms.like.wiki.WikiLikeRepository;
import com.ssg.kms.log.WikiLogRepository;
import com.ssg.kms.log.WikiLogService;
import com.ssg.kms.star.wiki.WikiStarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiService {
	
    private final WikiRepository wikiRepository;
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiStarRepository wikiStarRepository;
    private final WikiLogRepository wikiLogRepository;
    private final CategoryRepository categoryRepository;
    
    private final WikiLogService wikiLogService;
    private final WikiAlarmService wikiAlarmService;
    
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
    @Transactional
    public Wiki createWiki(WikiDTO wikiDto, Long userId) {
    	
    	Category category = createCategory(wikiDto.getCategory());
    	
    	Wiki wiki = Wiki.builder()
    			.wikiTitle(wikiDto.getTitle())
    			.wikiContent(wikiDto.getContent())
    			.wikiCreateDt(new Date())
    			.userId(userId)
    			.category(category)
    			.tableId(1L)
    			.build();
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, userId);
    	    	
		return wiki;
    }
    
    @Transactional
    public Wiki createTableWiki(Long tableId, WikiTableUserIdDTO wikiTableUserIdDto, Long userId) {
    	
    	Category category = createCategory(wikiTableUserIdDto.getCategory());
    	
    	Wiki wiki = Wiki.builder()
    			.wikiTitle(wikiTableUserIdDto.getTitle())
    			.wikiContent(wikiTableUserIdDto.getContent())
    			.wikiCreateDt(new Date())
    			.userId(userId)
    			.category(category)
    			.tableId(tableId)
    			.build();
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, userId);
    	   
/// �븣�엺 /////////////   	
    	List<Long> tableUserIds = wikiTableUserIdDto.getUserIds();
    	wikiAlarmService.createAlarm(wiki, tableUserIds, userId);
/////////////////////    	
		return wiki;
    }

    
    @Transactional(readOnly = true)
    public Wiki readWiki(Long wikiId, Long userId) {
		return wikiRepository.findById(wikiId).get();
    }
    
    @Transactional(readOnly = true)
    public List<Wiki> readAllWiki(Long userId) {
		return wikiRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Wiki> readMyWiki(Long userId) {
		return wikiRepository.findAllByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<Wiki> readPublicWiki(Long tableId, Long userId) {
		return wikiRepository.findAllByTableId(tableId);
    }
    
    
    @Transactional(readOnly = true)
    public Page<WikiResponseDTO> readPublicWiki2(Long tableId, Long userId, Pageable pageable) {
		return wikiRepository.findByTableId(tableId, pageable).map(WikiResponseDTO::from);
    } 
    
//    @Transactional(readOnly = true)
//    public List<Wiki> readPublicWiki(Long tableId, Long userId) {
//		return wikiRepository.findAllByTableId(tableId);
//    }
    
    @Transactional(readOnly = true)
    public List<Wiki> readTableWiki(Long tableId, Long userId) {
    	
    	List<Wiki> wikis = wikiRepository.findAllByTableId(tableId);
    	List<Long> wikiIds = wikiRepository.findWikiIdAllByTableId(tableId);
    	
    	wikiAlarmService.deleteAlarm(wikiIds, userId);
   
		return wikiRepository.findAllByTableId(tableId);
    }
    
//    @Transactional(readOnly = true)
//    public List<Wiki> readAllTableWiki(Optional<User> user) {
//    	List<Long> tableIds = tableUserRepository.findTableIdAllByUserId(user.get().getId());
//    	return wikiRepository.findAllByTableIdIn(tableIds);
//    }
    
    @Transactional(readOnly = true)
    public List<Wiki> readUserPublicWiki(Long userId, Long tableId) {
    	
		return wikiRepository.findAllByTableIdAndUserId(tableId, userId);
    }

    
    @Transactional
    public Wiki updateWiki(Long wikiId, WikiDTO wikiDto, Long userId) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	Category category = createCategory(wikiDto.getCategory());
    	
    	wiki.setWikiTitle(wikiDto.getTitle());
    	wiki.setWikiContent(wikiDto.getContent());
    	wiki.setWikiCreateDt(new Date());
    	wiki.setCategory(category);
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, userId);
    	
    	
		return wiki;
    }

    @Transactional
    public Wiki deleteWiki(Long wikiId, Long userId) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	wikiLikeRepository.deleteAllByWikiWikiId(wikiId);
    	wikiStarRepository.deleteAllByWikiWikiId(wikiId);
    	wikiLogRepository.deleteAllByWikiWikiId(wikiId);

    	wikiRepository.deleteById(wikiId);
    	return wiki;
    }
    
    @Transactional
    public List<Wiki> searchWiki(String searchKeyword, Long userId){
        return wikiRepository.findAllByWikiTitleContaining(searchKeyword);
    }   
    
    @Transactional
	public List<String> searchAllWiki(Long userId) {
		return wikiRepository.findAllWikiTitles();
	}
    
 // 移댄뀒怨좊━ �깮�꽦
    public Category createCategory(String categoryName){

    	Optional<Category> foundCategory = categoryRepository.findByName(categoryName);
    	Category category;
    	
    	if(foundCategory.isEmpty()) {
    		category = Category.builder()
    				.name(categoryName)
    				.build();    				
    	} else {
    		category = foundCategory.get();
    	}
    	
    	return categoryRepository.save(category);
    }
    
    

    // readTablePost 사용하는 걸로 ...
    @Transactional(readOnly = true)
    public Page<WikiResponseDTO> readAllTableWiki(Long userId, Pageable pageable) throws Exception {
    	
    	String responseBody = webClient.get()
                .uri("/api/tableuser/getMyTableIds/%s".formatted(userId + ""))
                .accept(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(getToken()))
                .retrieve()
                .bodyToMono(String.class) // 응답 본문을 Mono<String>으로 변환합니다.
                .block(); // Mono에서 결과를 블록킹하고 응답 본문을 가져옵니다.
    	
    	if(!responseBody.equals("[]")) {
        	List<Long> tableIds = convertStringToLongList(responseBody);    		
        	return wikiRepository.findAllByTableIdIn(tableIds, pageable).map(WikiResponseDTO::from);
    	} else {
    		return null;
    	}    	
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
    
    
}
