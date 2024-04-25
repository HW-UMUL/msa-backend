package com.ssg.kms.like.wiki;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetWikiMapping;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiLikeService {
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiRepository wikiRepository;
    
    @Transactional
    public WikiLike checkwiki(Long wikiId, Long userId) {
    	
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	WikiLike wikiLike = wikiLikeRepository.findByWikiWikiIdAndUserId(wikiId, userId).orElse(null);
    	
    	if(wikiLike == null) {
    		WikiLike newWikiLike = WikiLike.builder()
        			.wiki(wiki)
        			.userId(userId)
        			.build();
    		
    		wikiLikeRepository.save(newWikiLike);
    		wikiLike = newWikiLike;
    	} else {
    		wikiLikeRepository.deleteById(wikiLike.getId());
    	}
    	    	
    	
    	
		return wikiLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long wikiId) {
    	return wikiLikeRepository.findAllByWikiWikiId(wikiId).size();
    }
    
    @Transactional(readOnly = true)
    public List<GetWikiMapping> readMyLike(Long userId) {
    	return wikiLikeRepository.findWikiAllByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Boolean isCheck(Long wikiId, Long userId) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();

    	if(wikiLikeRepository.findByWikiWikiIdAndUserId(wikiId, userId) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    ////////////////////////////
    
	@Transactional(readOnly = true)
    public WikiLike readLikePersonal(Long wikiId, Long userId) {
//		Wiki wiki = wikiRepository.findById(wikiId).get();
    	WikiLike wikiLike = wikiLikeRepository.findByWikiWikiIdAndUserId(wikiId, userId).orElse(null);
		
		if(wikiLike != null) {
    		return wikiLike;
    	}
    	    	
		return null;
    }
	
	
	// 醫뗭븘�슂 �닚�쐞 留ㅺ린�뒗 �븿�닔
	public List<WikiLikeDTO> findTop5WikiLikeCounts() {
        PageRequest pageable = PageRequest.of(0, 5); // 理쒖긽�쐞 5媛쒕�� 媛��졇�삤湲� �쐞�븳 �럹�씠吏� �슂泥�
        List<Object[]> results = wikiLikeRepository.findTop5WikiLikeCounts(pageable);
        return results.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private WikiLikeDTO convertToDTO(Object[] result) {
        Long wikiId = (Long) result[0];
        String title = (String) result[1];
        Long count = (Long) result[2];
        return new WikiLikeDTO(wikiId, title, count);
    }
}
