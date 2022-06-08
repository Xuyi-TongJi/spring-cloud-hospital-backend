package edu.seu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.model.cmn.Dict;
import edu.seu.vo.cmn.DictEeVo;
import edu.seu.yygh.cmn.listener.DictListener;
import edu.seu.yygh.cmn.mapper.DictMapper;
import edu.seu.yygh.cmn.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyitjuseu
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> getByParentId(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        List<Dict> result = baseMapper.selectList(wrapper);
        for (Dict dict : result) {
            dict.setHasChildren(hasChildren(dict.getId()));
        }
        return result;
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "hosp-dict";
        // attachment 以附件形式下载 inline 直接在页面显示
        response.setHeader("Content-disposition", "attachment;");
        List<Dict> results = baseMapper.selectList(new QueryWrapper<>());
        List<DictEeVo> ans = new ArrayList<>();
        for (Dict result : results) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(result, dictEeVo);
            ans.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("sheet1").doWrite(ans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importExcel(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        return baseMapper.selectCount(wrapper) > 0;
    }
}
