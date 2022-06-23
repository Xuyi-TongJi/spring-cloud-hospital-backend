package edu.seu.yygh.hosp.controller.api;

import edu.seu.helper.HttpRequestHelper;
import edu.seu.utils.MD5;
import edu.seu.yygh.common.exception.YyghException;
import edu.seu.yygh.common.result.Result;
import edu.seu.yygh.common.result.ResultCodeEnum;
import edu.seu.yygh.hosp.service.HospitalService;
import edu.seu.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    public ApiController(HospitalService hospitalService, HospitalSetService hospitalSetService) {
        this.hospitalService = hospitalService;
        this.hospitalSetService = hospitalSetService;
    }

    @ApiOperation(value = "保存医院到mongodb")
    @PostMapping("saveHospital")
    public Result<Object> saveHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> objectMap = HttpRequestHelper.switchMap(parameterMap);
        // 校验签名是否一致
        String signKey = (String)objectMap.get("signKey");
        String realSignKey = hospitalSetService.querySignKeyByHoscode((String)objectMap.get("hoscode"));
        realSignKey = MD5.encrypt(realSignKey);
        if (!realSignKey.equals(signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        // 处理图片
        String logoData = (String)objectMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        objectMap.put("logoData", logoData);
        hospitalService.saveHospital(objectMap);
        return Result.ok("保存成功");
    }
}
