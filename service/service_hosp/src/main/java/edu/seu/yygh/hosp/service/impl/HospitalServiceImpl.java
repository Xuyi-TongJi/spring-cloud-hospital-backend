package edu.seu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.seu.model.hosp.Hospital;
import edu.seu.yygh.hosp.repository.HospitalRepository;
import edu.seu.yygh.hosp.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author xuyitjuseu
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public void saveHospital(Map<String, Object> objectMap) {
        // 将objectMap映射为相应的实体类
        String objectString = JSONObject.toJSONString(objectMap);
        Hospital hospital = JSONObject.parseObject(objectString, Hospital.class);
        Hospital exist = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        if (exist != null) {
            hospital.setStatus(exist.getStatus());
            hospital.setCreateTime(exist.getCreateTime());
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        }
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital queryByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }


}
