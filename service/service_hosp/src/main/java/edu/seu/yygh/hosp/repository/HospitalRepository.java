package edu.seu.yygh.hosp.repository;

import edu.seu.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xuyitjuseu
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {


    /**
     * 根据hoscode查询
     * @param hoscode hoscode
     * @return Hospital实体类
     */
    Hospital getHospitalByHoscode(String hoscode);
}