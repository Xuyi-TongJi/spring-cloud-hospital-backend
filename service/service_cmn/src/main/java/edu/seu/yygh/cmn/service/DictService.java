package edu.seu.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netflix.client.http.HttpResponse;
import edu.seu.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 导出excel
     * @param httpResponse 将excel文件封装在response响应体中并设置相应的响应参数
     */
    void exportExcel(HttpServletResponse httpResponse);

    /**
     * 将excel导入数据库
     * @param file Excel文件对象
     */
    void importExcel(MultipartFile file);
}
