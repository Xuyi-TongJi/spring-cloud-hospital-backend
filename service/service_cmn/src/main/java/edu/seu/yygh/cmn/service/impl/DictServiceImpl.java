package edu.seu.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.model.cmn.Dict;
import edu.seu.yygh.cmn.mapper.DictMapper;
import edu.seu.yygh.cmn.service.DictService;
import org.springframework.stereotype.Service;

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

    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        return baseMapper.selectCount(wrapper) > 0;
    }
}
