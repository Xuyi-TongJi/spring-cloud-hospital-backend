package edu.seu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.seu.model.hosp.Department;
import edu.seu.vo.hosp.DepartmentQueryVo;
import edu.seu.yygh.hosp.repository.DepartmentRepository;
import edu.seu.yygh.hosp.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author xuyitjuseu
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void saveDepartment(Map<String, Object> objectMap) {
        String hoscode = (String) objectMap.get("hoscode");
        String depcode = (String) objectMap.get("depcode");
        // map -> entity
        String jsonObject = JSONObject.toJSONString(objectMap);
        Department department = JSONObject.parseObject(jsonObject, Department.class);
        Department exist = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (exist != null) {
            exist.setUpdateTime(new Date());
            exist.setIsDeleted(0);
            departmentRepository.save(exist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findDepartmentPage(Integer page, Integer limit, DepartmentQueryVo departmentVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 设置模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Department department = new Department();
        BeanUtils.copyProperties(departmentVo, department);
        department.setIsDeleted(0);
        Example<Department> example = Example.of(department, matcher);
        return departmentRepository.findAll(example, pageable);
    }

    @Override
    public void removeDepartmentById(Map<String, Object> objectMap) {
        String hoscode = (String)objectMap.get("hoscode");
        String depcode = (String)objectMap.get("depcode");
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }
}
