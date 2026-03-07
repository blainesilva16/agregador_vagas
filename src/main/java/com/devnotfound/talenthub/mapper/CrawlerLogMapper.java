package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.CrawlerLogResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;

public class CrawlerLogMapper {

    public static CrawlerLogResponseDTO toResponseDTO(CrawlerLog crawlerLog) {

        return new CrawlerLogResponseDTO(
                crawlerLog.getId(),
                crawlerLog.getTitle(),
                /*crawlerLog.getTech(),
                crawlerLog.getPosition(),*/
                crawlerLog.getCompanyName(),
                crawlerLog.getCityName(),
                crawlerLog.getUfAbrev(),
                crawlerLog.getWorkMode(),
                crawlerLog.getHiringType(),
                crawlerLog.getTechLevel(),
                crawlerLog.getSalaryRange(),
                crawlerLog.getPlatform(),
                crawlerLog.getCreationDate()
        );
    }
}
