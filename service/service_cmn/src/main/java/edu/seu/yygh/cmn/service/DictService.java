package edu.seu.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.model.cmn.Dict;

import java.util.List;

/**
 * @author xuyitjuseu
 */
public interface DictService extends IService<Dict> {
    /**
     * 根据数据id获取其所有子数据
     * @param id 父数据id
     * @return 包含所有子数据的集合
     */
    List<Dict> getByParentId(Long id);
}
