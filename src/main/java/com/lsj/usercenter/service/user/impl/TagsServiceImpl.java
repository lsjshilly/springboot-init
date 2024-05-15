package com.lsj.usercenter.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.mapper.TagsMapper;
import com.lsj.usercenter.model.entity.Tags;
import com.lsj.usercenter.service.user.TagsService;
import org.springframework.stereotype.Service;

/**
 * @author liushijie
 * @description 针对表【tb_tags】的数据库操作Service实现
 * @createDate 2024-05-15 22:46:02
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
        implements TagsService {

}




