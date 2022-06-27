package edu.seu.yygh.hosp.controller.api;

import com.aliyuncs.utils.StringUtils;
import edu.seu.helper.HttpRequestHelper;
import edu.seu.model.hosp.Department;
import edu.seu.model.hosp.Hospital;
import edu.seu.utils.MD5;
import edu.seu.vo.hosp.DepartmentQueryVo;
import edu.seu.yygh.common.exception.YyghException;
import edu.seu.yygh.common.result.Result;
import edu.seu.yygh.common.result.ResultCodeEnum;
import edu.seu.yygh.hosp.service.DepartmentService;
import edu.seu.yygh.hosp.service.HospitalService;
import edu.seu.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xuyitjuseu
 */
@Api("上传医院接口API")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    private final HospitalService hospitalService;
    private final HospitalSetService hospitalSetService;
    private final DepartmentService departmentService;

    public ApiController(HospitalService hospitalService,
                         HospitalSetService hospitalSetService,
                         DepartmentService departmentService) {
        this.hospitalService = hospitalService;
        this.hospitalSetService = hospitalSetService;
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "保存医院到mongodb")
    @PostMapping("saveHospital")
    public Result<Object> saveHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        if (checkSign(objectMap)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        // 处理图片
        String logoData = (String)objectMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        objectMap.put("logoData", logoData);
        hospitalService.saveHospital(objectMap);
        return Result.ok();
    }

    @ApiOperation("根据请求信息查询Hospital")
    @PostMapping("hospital/show")
    public Result<Hospital> showHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        if (checkSign(objectMap)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        Hospital hospital = hospitalService.queryByHoscode((String)objectMap.get("hoscode"));
        return Result.ok(hospital);
    }

    @ApiOperation("上传科室接口")
    @PostMapping("/saveDepartment")
    public Result<Object> saveDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        if (checkSign(objectMap)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.saveDepartment(objectMap);
        return Result.ok();
    }

    @ApiOperation("分页查询科室")
    @PostMapping("/department/list")
    public Result<Page<Department>> queryDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        if (checkSign(objectMap)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        Integer page = StringUtils.isEmpty((String)objectMap.get("page")) ?
                1 : Integer.parseInt((String)objectMap.get("page"));
        Integer limit = StringUtils.isEmpty((String)objectMap.get("limit")) ?
                1 : Integer.parseInt((String)objectMap.get("limit"));
        DepartmentQueryVo departmentVo = new DepartmentQueryVo();
        departmentVo.setHoscode((String) objectMap.get("hoscode"));
        Page<Department> results = departmentService.findDepartmentPage(page, limit, departmentVo);
        return Result.ok(results);
    }

    @ApiOperation("删除科室")
    @PostMapping("/department/remove")
    public Result<Object> removeDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        if (checkSign(objectMap)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.removeDepartmentById(objectMap);
        return Result.ok();
    }

    private boolean checkSign(Map<String, Object> objectMap) {
        String signKey = (String)objectMap.get("sign");
        String hoscode = (String)objectMap.get("hoscode");
        String realSignKey = hospitalSetService.querySignKeyByHoscode(hoscode);
        String realSignKeyMd5 = MD5.encrypt(realSignKey);
        return !realSignKeyMd5.equals(signKey);
    }
}