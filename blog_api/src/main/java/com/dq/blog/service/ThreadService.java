package com.dq.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dq.blog.mapper.ArticleMapper;
import com.dq.blog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @project: blog_parent
 * @ClassName: ThreadService
 * @author: dq
 * @creat: 2022/5/21 13:03
 */
@Component
public class ThreadService {

    /**
     * 希望此跟新操作在线程池中进行  不会影响主线程
     *
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int articleViewCounts = article.getViewCounts();
        //最小限度修改article中的参数
        Article copyArticle = new Article();
        copyArticle.setViewCounts(articleViewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        //CAS 类似乐观锁  如果不等于回滚
        updateWrapper.eq(Article::getId, article.getId()).eq(Article::getViewCounts, articleViewCounts);

        //开始修改值
        articleMapper.update(copyArticle, updateWrapper);


    }
}
