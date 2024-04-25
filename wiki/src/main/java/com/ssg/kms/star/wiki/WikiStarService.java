package com.ssg.kms.star.wiki;

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
public class WikiStarService {
    private final WikiStarRepository wikiStarRepository;
    private final WikiRepository wikiRepository;
    
    @Transactional
    public WikiStar checkWiki(Long wikiId, Long userId) {
    	
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	WikiStar wikiStar = wikiStarRepository.findByWikiWikiIdAndUserId(wikiId, userId).orElse(null);
    	
    	if(wikiStar == null) {
    		WikiStar newWikiStar = WikiStar.builder()
        			.wiki(wiki)
        			.userId(userId)
        			.build();
    		
    		wikiStarRepository.save(newWikiStar);
    		wikiStar = newWikiStar;
    	} else {
    		wikiStarRepository.deleteById(wikiStar.getId());
    	}
    	    	
    	
    	
		return wikiStar;
    }
    
    @Transactional(readOnly = true)
    public int readStar(Long wikiId) {
    	return wikiStarRepository.findAllByWikiWikiId(wikiId).size();
    }
    
    @Transactional(readOnly = true)
    public List<GetWikiMapping> readMyStar(Long userId) {
    	return wikiStarRepository.findWikiAllByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Boolean isCheck(Long wikiId, Long userId) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();

    	if(wikiStarRepository.findByWikiWikiIdAndUserId(wikiId, userId) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //////////////////////
    
	@Transactional(readOnly = true)
    public WikiStar readStarPersonal(Long wikiId, Long userId) {
		Wiki wiki = wikiRepository.findById(wikiId).get();
    	WikiStar wikiStar = wikiStarRepository.findByWikiWikiIdAndUserId(wikiId, userId).orElse(null);
		
		if(wikiStar != null) {
    		return wikiStar;
    	}
    	    	
		return null;
    }
	
	// 利먭꺼李얘린 媛쒖닔
	public List<WikiStarDTO> findTop5WikiStarCounts() {
        PageRequest pageable = PageRequest.of(0, 5); // 理쒖긽�쐞 5媛쒕�� 媛��졇�삤湲� �쐞�븳 �럹�씠吏� �슂泥�
        List<Object[]> results = wikiStarRepository.findTop5WikiStarCounts(pageable);
        return results.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private WikiStarDTO convertToDTO(Object[] result) {
        Long wikiId = (Long) result[0];
        String title = (String) result[1];
        Long count = (Long) result[2];
        return new WikiStarDTO(wikiId, title, count);
    }
}
