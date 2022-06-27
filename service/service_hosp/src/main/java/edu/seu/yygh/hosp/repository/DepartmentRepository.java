package edu.seu.yygh.hosp.repository;

import edu.seu.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xuyitjuseu
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    /**
     * 根据hoscode和depcode查询Department
     * @param hoscode hoscode
     * @param depcode depcode
     * @return Department entity
     */
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
