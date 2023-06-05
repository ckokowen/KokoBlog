package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.Comment;
import com.koko.domain.vo.CommentVo;
import com.koko.domain.vo.PageVo;
import com.koko.enums.AppHttpCodeEnum;
import com.koko.exception.SystemException;
import com.koko.mapper.CommentMapper;
import com.koko.service.CommentService;
import com.koko.service.UserService;
import com.koko.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-07 19:57:10
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断,只有是评论类型为0时才判断
        lambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        lambdaQueryWrapper.eq(Comment::getRootId,-1);
        lambdaQueryWrapper.orderByDesc(Comment::getCreateTime);

        //评论类型
        lambdaQueryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询对应的子评论集合，并赋值给对应的属性
        for(CommentVo commentVo : commentVoList){
            //查询每条评论对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //把子评论赋值到根评论中的属性
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        //根据id查询
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //查询评论的根id和目标id一样的评论，就是这个根评论的子评论
        queryWrapper.eq(Comment::getRootId,id);
        //按时间排序
        queryWrapper.orderByAsc(Comment::getCreateTime);
        //查询子评论集合
        List<Comment> children = list(queryWrapper);
        //将子评论转换为vo，准备赋值到根评论中的属性中
        List<CommentVo> childrenList = toCommentVoList(children);
        return childrenList;

    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo
        for(CommentVo commentVo : commentVos){
            //通过creatyBy查询用户的昵称并赋值nickName
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId() != -1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
