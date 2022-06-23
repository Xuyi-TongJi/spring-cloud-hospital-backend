package edu.seu.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.model.hosp.HospitalSet;

/**
 * <p>
 * 医院设置表 服务类
 * </p>
 *
 * @author xuyitjuseu
 * @since 2022-05-31
 */
public interface HospitalSetService extends IService<HospitalSet> {

    /**
     * 根据hoscode查询signKey（密钥）
     * @param hoscode hoscode
     * @return signKey String
     */
    String querySignKeyByHoscode(String hoscode);
}
