package com.dq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dq.blog.mapper.CommentMapper;
import com.dq.blog.pojo.Article;
import com.dq.blog.pojo.Comment;
import com.dq.blog.pojo.SysUser;
import com.dq.blog.service.CommentService;
import com.dq.blog.service.SysUserService;
import com.dq.blog.util.UserThreadLocal;
import com.dq.blog.vo.CommentVo;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.UserVo;
import com.dq.blog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: CommentsServiceImpl
 * @author: dq
 * @creat: 2022/5/21 16:29
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result findCommentsByArticleId(Long id) {
        /**
         * 1. 根据文章id 查询评论列表 从comment中查
         * 2. 根据作者id 查询作者的信息
         * 3. 判断 如果level=1 要去查询是否有子评论
         * 4. 如果有 根据评论id进行查询（parent_id）
         */
        //查询所有一级评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id).eq(Comment::getLevel, 1);
        List<Comment> commentsList = commentMapper.selectList(queryWrapper);

        //期望得到List<CommentVo> commentVo
        List<CommentVo> commentVoList = copyList(commentsList);
        return Result.success(commentVoList);
    }

    @Override
    public Result creatComment(CommentParam commentParam) {
        System.err.println("-----------------"+commentParam);
        /**
         * 1. 把参数拼接在comment中  用户信息在UserThreadLocal中  评论信息在commentParam中
         * 2. 然后判断其中的parent选项 如果为0 则为一级评论  反之为2级
         * 3. 获取parentId
         */
        SysUser sysUser = UserThreadLocal.getLOCAL();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticle().getId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    /**
     * 集合拷贝参数
     *
     * @param commentsList
     * @return
     */
    private List<CommentVo> copyList(List<Comment> commentsList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentsList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    /**
     * 单一拷贝
     *
     * @param comment
     * @return
     */
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(String.valueOf(comment.getId()));
        BeanUtils.copyProperties(comment, commentVo);
        //作者信息
        Long articleId = comment.getArticleId();
        UserVo userVo = sysUserService.findUserByCommentArticleId(articleId);
        commentVo.setAuthor(userVo);
        //子List<CommentVo> childrens 父context 评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findParentCommentsByCommentId(id);
            commentVo.setChildrens(commentVoList);
        }


        //如果大于1 设置toUser给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserByCommentToUserId(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    /**
     * 将子类中的信息同时也查询出来
     * 1. 判断父级是否为一级评论
     * 2. 是 （根据父类中id获取
     *
     * @param id
     * @return
     */
    private List<CommentVo> findParentCommentsByCommentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //select * from comment where parent_id = #{id} and level = 2
        queryWrapper
                .eq(Comment::getParentId, id)
                .eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
