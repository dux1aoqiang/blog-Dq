package com.dq.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dq.blog.pojo.Article;
import com.dq.blog.pojo.dos.Archives;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: ArticleMapper
 * @author: dq
 * @creat: 2022/5/16 13:35
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
