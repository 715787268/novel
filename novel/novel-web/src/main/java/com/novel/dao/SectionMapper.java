package com.novel.dao;

import com.novel.beans.Section;

public interface SectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKeyWithBLOBs(Section record);

    int updateByPrimaryKey(Section record);
}