package edu.seu.yygh.cmn.controller;

import edu.seu.model.cmn.Dict;
import edu.seu.yygh.cmn.service.DictService;
import edu.seu.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyitjuseu
 */
@Api("数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {
    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @ApiOperation("获取给定id数据的子数据")
    @GetMapping("/getByParentId/{id}")
    public Result<List<Dict>> getByParentId(@PathVariable Long id) {
        return Result.ok(dictService.getByParentId(id));
    }

    //@ApiOperation("导出(下载)数据接口到excel")
    //public Result<List<Dict>>
}
