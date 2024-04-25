package com.ssg.kms.wiki;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiResponseDTO {
    private Long wikiId;
    private String wikiTitle;
    private String wikiContent;
    private Date wikiCreateDt;
    private Long userId;
    private Long tableId;
    private Long categoryId;

    // �깮�꽦�옄, getter, setter �벑�쓽 �븘�슂�븳 硫붿꽌�뱶�뱾�� �뿬湲곗뿉 異붽��빀�땲�떎.

    public static WikiResponseDTO from(Wiki wiki) {
        WikiResponseDTO dto = new WikiResponseDTO();
        dto.setWikiId(wiki.getWikiId());
        dto.setWikiTitle(wiki.getWikiTitle());
        dto.setWikiContent(wiki.getWikiContent());
        dto.setWikiCreateDt(wiki.getWikiCreateDt());
        dto.setUserId(wiki.getUserId());
        dto.setTableId(wiki.getTableId());
        if (wiki.getCategory() != null) {
            dto.setCategoryId(wiki.getCategory().getId());
        }
        return dto;
    }
}
