package edu.seu.yygh.hosp.service;

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
}
