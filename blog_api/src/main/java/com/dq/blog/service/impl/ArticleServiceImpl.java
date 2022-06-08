package com.dq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dq.blog.mapper.ArticleBodyMapper;
import com.dq.blog.mapper.ArticleMapper;
import com.dq.blog.mapper.ArticleTagMapper;
import com.dq.blog.pojo.Article;
import com.dq.blog.pojo.ArticleBody;
import com.dq.blog.pojo.ArticleTag;
import com.dq.blog.pojo.SysUser;
import com.dq.blog.pojo.dos.Archives;
import com.dq.blog.service.*;
import com.dq.blog.util.UserThreadLocal;
import com.dq.blog.vo.ArticleBodyVo;
import com.dq.blog.vo.ArticleVo;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.TagVo;
import com.dq.blog.vo.params.ArticleParam;
import com.dq.blog.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

/**
 * @project: blog_parent
 * @ClassName: ArticleServiceImpl
 * @author: dq
 * @creat: 2022/5/16 13:53
 */
@Service
@Slf4j
@EnableTransactionManagement
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        //获取作者姓名 和标签
        return Result.success(copyList(records, true, true));
    }
    /*@Override
    public Result listArticle(PageParams pageParams) {
        */

    /**
     * 分页查询 article数据库表
     *//*
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //分类下的文章列表显示
        if (pageParams.getCategoryId()!=null){
            //and category_id =#{categoryId}
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }

        ArrayList<Long> articleIdList = new ArrayList<>();
        //分类下文章的标签显示
        if (pageParams.getTagId()!=null){
            //加入标签 条件查询
            //article中没有此字段 关联表 article_tag
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size()>0){
                //and id in(1,2,3)
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //倒叙    时间 是否置顶 排列
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //不能直接返回 封装成Vo对象
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }*/
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        /**
         * 语法
         * select id , title from article order by view_counts desc limit 5
         */
        queryWrapper
                .orderByDesc(Article::getViewCounts)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        //只返回article中的 id 和 标题

        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result newsArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        /**
         * 语法
         * select id , title from article order by creat_time desc limit 5
         */
        queryWrapper
                .orderByDesc(Article::getCreateDate)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result listArchives(int limit) {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    //注入线程池 将数据查询和修改隔离
    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long id) {
        /**
         * 1. 根据id查询文章详情信息
         * 2. 转换封装为VO对象
         */
        Article article = articleMapper.selectById(id);

        ArticleVo articleVo = copy(article, true, true, true, true);
        /**
         * 1. 查看了文章接口之后  然后进行阅读写入 费时
         * 2. 并且在写入的时候阻塞其他读的操作 性能下降
         * 3. 一但跟新出问题 不能影响其他文章的操作
         * 4. 使用线程池解决   可以把跟新操作和主线程分开
         */
        threadService.updateArticleViewCount(articleMapper, article);

        return Result.success(articleVo);
    }

    @Override
    public Result publishArticle(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.getLOCAL();
        /**
         * 1. 发布文章  目的是构建article对象
         * 2. 作者ID 当前登录用户UserThreadLocal.get方法  （此接口要加入拦截器 才有）
         * 3. 标签 要将标签插入到关联表
         * 4. 内容(body)  关联表的填充
         */

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        this.articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                this.articleTagMapper.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    /**
     * 集合遍历 调用下面的方法
     *
     * @param records
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    /**
     * 重载方法 传入参数
     *
     * @param records
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }


    /**
     * 1. 遍历单个 封装到ArticleVo
     * 2. 进行相应功能的封装
     *
     * @param article
     * @return
     */
    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        //类名映射
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));

        //并不是所有的接口都需要标签和作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
            log.info("查询到的id为=={}  名字为=====》{}", authorId, sysUserService.findUserById(authorId).getNickname());
        }

        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }

        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }


    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    /**
     * 查询获取   articleBody的专用方法
     *
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContext(articleBody.getContent());
        return articleBodyVo;
    }
}
