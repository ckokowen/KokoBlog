package com.koko.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.domain.entity.ArticleTag;
import com.koko.mapper.ArticleTagMapper;
import com.koko.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-10 15:44:06
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
