package com.macaron.corpresent.domain.corporation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.corporation.model.entity.CorporationInfo;
import com.macaron.corpresent.domain.corporation.service.CorporationInfoService;
import com.macaron.corpresent.domain.corporation.model.dao.mapper.CorporationInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【corporation_info(公司信息表)】的数据库操作Service实现
* @createDate 2024-11-16 02:16:36
*/
@Service
public class CorporationInfoServiceImpl extends ServiceImpl<CorporationInfoMapper, CorporationInfo>
    implements CorporationInfoService {

}




