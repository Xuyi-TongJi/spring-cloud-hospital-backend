package edu.seu.yygh.hosp.service;

import edu.seu.model.hosp.Hospital;

import java.util.Map;

/**
 * @author xuyitjuseu
 */
public interface HospitalService {
    /**
     * 保存医院信息到mongodb
     * @param objectMap 包含医院信息的参数Map
     */
    void saveHospital(Map<String, Object> objectMap);

    /**
     * 根据hoscode查询Hospital
     * @param hoscode hoscode
     * @return Hospital实体类
     */
    Hospital queryByHoscode(String hoscode);
}
