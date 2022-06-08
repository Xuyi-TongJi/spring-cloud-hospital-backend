package edu.seu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fasterxml.jackson.databind.util.BeanUtil;
import edu.seu.model.cmn.Dict;
import edu.seu.vo.cmn.DictEeVo;
import edu.seu.yygh.cmn.mapper.DictMapper;
import org.springframework.beans.BeanUtils;

/**
 * @author xuyitjuseu
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private final DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
