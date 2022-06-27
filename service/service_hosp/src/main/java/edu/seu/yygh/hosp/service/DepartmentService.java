package edu.seu.yygh.hosp.service;

import edu.seu.model.hosp.Department;
import edu.seu.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author xuyitjuseu
 */
public interface DepartmentService {

    /**
     * 保存科室
     * @param objectMap objectMap
     */
    void saveDepartment(Map<String, Object> objectMap);

    /**
     * 根据条件分页查询
     * @param page page 分页参数
     * @param limit limit 分页参数
     * @param departmentVo 查询实体类
     * @return Page对象
     */
    Page<Department> findDepartmentPage(Integer page, Integer limit, DepartmentQueryVo departmentVo);

    /**
     * 根据id删除Department
     * @param objectMap objectMap
     */
    void removeDepartmentById(Map<String, Object> objectMap);
}
