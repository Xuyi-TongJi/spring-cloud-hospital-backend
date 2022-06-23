package edu.seu.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.seu.model.hosp.Hospital;
import edu.seu.model.hosp.HospitalSet;
import edu.seu.yygh.hosp.mapper.HospitalSetMapper;
import edu.seu.yygh.hosp.service.HospitalSetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 医院设置表 服务实现类
 * </p>
 *
 * @author xuyitjuseu
 * @since 2022-05-31
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public String querySignKeyByHoscode(String hoscode) {
        HospitalSet hospitalSet = baseMapper.selectOne(new QueryWrapper<HospitalSet>().eq("hoscode", hoscode));
        return hospitalSet.getSignKey();
    }
}
