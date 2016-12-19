package com.joyue.tech.gankio.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.joyue.tech.gankio.domain.DayResult;

/**
 * @author JiangYH
 */
public class ContentSection extends SectionEntity<DayResult> {

    public ContentSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ContentSection(DayResult t) {
        super(t);
    }
}
