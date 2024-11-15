package com.macaron.corpresent.domain.corporation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.corporation.model.entity.Language;
import com.macaron.corpresent.domain.corporation.service.LanguageService;
import com.macaron.corpresent.domain.corporation.model.dao.mapper.LanguageMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【language(语言表)】的数据库操作Service实现
* @createDate 2024-11-16 02:16:36
*/
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language>
    implements LanguageService {

}




