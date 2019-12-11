package cn.xiaochi.service;

import cn.xiaochi.common.Response;
import cn.xiaochi.pojo.Category;

import java.util.List;

public interface ICategoryService {
    /**
     * 新增分类
     * @param categoryName
     * @param parentId
     * @return
     */
    Response createCategory(String categoryName, Integer parentId);

    /**
     * 更新分类
     * @param categoryName
     * @param categoryId
     * @return
     */
    Response updateCategory(String categoryName, Integer categoryId);

    /**
     * 查询子节点的category信息,并且不递归,（平级）
     * @param categoryId
     * @return
     */
    Response<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询当前节点的id及子节点的id
     * @param categoryId
     * @return
     */
    Response selectCategoryAndChildrenById(Integer categoryId);
}
