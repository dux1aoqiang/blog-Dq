package com.dq.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dq.blog.pojo.Tag;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: TagMapper
 * @author: dq
 * @creat: 2022/5/16 13:41
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签 前limit条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 查询tags 根据IDs
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
