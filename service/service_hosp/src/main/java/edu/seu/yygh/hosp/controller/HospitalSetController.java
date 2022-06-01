package edu.seu.yygh.hosp.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.model.hosp.HospitalSet;
import edu.seu.utils.MD5;
import edu.seu.vo.hosp.HospitalSetQueryVo;
import edu.seu.yygh.common.result.Result;
import edu.seu.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author xuyitjuseu
 * @since 2022-05-31
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    private final HospitalSetService hospitalSetService;

    public HospitalSetController(HospitalSetService hospitalSetService) {
        this.hospitalSetService = hospitalSetService;
    }

    @GetMapping("")
    @ApiOperation(value = "获取所有医院设置信息")
    public Result<List<HospitalSet>> findAll() {
        return Result.ok(hospitalSetService.list());
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除医院设置")
    public Result<Object> deleteById(@PathVariable("id")Long id) {
        Result<Object> result;
        if (hospitalSetService.removeById(id)) {
            result = Result.ok();
            result.message("删除成功");
        } else {
            result = Result.fail();
            result.message("删除失败");
        }
        return result;
    }

    @PostMapping("")
    @ApiOperation(value = "添加医院设置")
    public Result<Object> add(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        // 生成signKey
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        Result<Object> result;
        if (hospitalSetService.save(hospitalSet)) {
            result = Result.ok();
            result.message("添加成功");
        } else {
            result = Result.fail();
            result.message("添加失败");
        }
        return result;
    }

    @PutMapping("")
    @ApiOperation(value = "更新医院设置")
    public Result<Object> update(@RequestBody HospitalSet hospitalSet) {
        Result<Object> result;
        if (hospitalSetService.updateById(hospitalSet)) {
            result = Result.ok();
            result.message("更新成功");
        } else {
            result = Result.fail();
            result.message("更新失败");
        }
        return result;
    }

    @PostMapping("/{current}/{limit}")
    @ApiOperation(value = "分页条件查询")
    public Result<Page<HospitalSet>> findPageHospitalSet(@PathVariable Long current,
                                      @PathVariable Long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        // 构造条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosName = hospitalSetQueryVo.getHosname();
        String hosCode = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hosName)) {
            wrapper.like("hosname", hosName);
        }
        if (!StringUtils.isEmpty(hosCode)) {
            wrapper.eq("hoscode", hosCode);
        }
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        return Result.ok(pageHospitalSet);
    }

    @GetMapping("{id}")
    @ApiOperation("根据id获取医院设置")
    public Result<HospitalSet> getById(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        if (hospitalSet != null) {
            return Result.ok(hospitalSet);
        } else {
            Result<HospitalSet> result = Result.fail();
            result.message("获取失败");
            return result;
        }
    }

    @DeleteMapping("")
    @ApiOperation("批量删除医院设置")
    public Result<Object> deleteBatchByIds(@RequestBody List<Long> ids) {
        Result<Object> result;
        if (hospitalSetService.removeByIds(ids)) {
            result = Result.ok();
            result.message("批量删除成功");
        } else {
            result = Result.fail();
            result.message("批量删除失败");
        }
        return result;
    }

    @PutMapping("/lock/{id}/{status}")
    @ApiOperation("医院设置锁定和解锁")
    public Result<Object> lockUnlock(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        Result<Object> result;
        if (hospitalSetService.updateById(hospitalSet)) {
            result = Result.ok();
            result.message("更新成功");
        } else {
            result = Result.fail();
            result.message("更新失败");
        }
        return result;
    }

    @GetMapping("/signKey/{id}")
    @ApiOperation("发送签名")
    public Result<Object> getSignKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hosCode = hospitalSet.getHoscode();
        // TODO 发送短信
        return Result.ok();
    }
}