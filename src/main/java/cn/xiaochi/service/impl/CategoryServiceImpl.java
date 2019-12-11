package cn.xiaochi.service.impl;

import cn.xiaochi.common.Response;
import cn.xiaochi.dao.CategoryDao;
import cn.xiaochi.pojo.Category;
import cn.xiaochi.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 新增分类
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public Response<String> createCategory(String categoryName, Integer parentId) {
        if (parentId == null && StringUtils.isBlank(categoryName)){
            return Response.error("参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        int rowCount =categoryDao.insert(category);
        if (rowCount > 0){
            return Response.success("添加品类成功");
        }
        return Response.error("添加品类失败");
    }

    /**
     * 更新分类
     * @param categoryName
     * @param categoryId
     * @return
     */
    @Override
    public Response updateCategory(String categoryName, Integer categoryId) {
        if (categoryId == null && StringUtils.isBlank(categoryName)){
            return Response.error("参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(categoryId);
        category.setStatus(true);//这个分类是可用的

        int rowCount =categoryDao.updateByPrimaryKeySelective(category);
        if (rowCount > 0){
            return Response.success("更新品类成功");
        }
        return Response.error("更新品类失败");
    }

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     * @param categoryId
     * @return
     */
    @Override
    public Response<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return Response.success(categoryList);
    }

    /**
     * 递归查询当前节点的id及子节点的id
     * @param categoryId
     * @return
     */
    @Override
    public Response selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        List<Category> categoryList1 = categoryDao.selectAll();
        findChildCategory(categorySet,categoryList1,categoryId);

        List<Integer> categoryList = Lists.newArrayList();
        if (categoryId != null){
            for (Category category : categorySet){
                categoryList.add(category.getId());
            }
        }
        return Response.success(categoryList);
    }
    // 递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryDao.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

    /**
     * @param categorySet  空set集合
     * @param categoryList 分类所有数据
     * @param categoryId 当前分类id
     * @return
     */
    // 递归算法,算出子节点 改造
    private Set<Category> findChildCategory(Set<Category> categorySet, List<Category> categoryList,Integer categoryId){
        for (Category category : categoryList){
            if (category != null){
                // 当前自己也添加进去
                if (category.getId().intValue() == categoryId){
                    categorySet.add(category);
                }
                if (category.getParentId().intValue() == categoryId){
                    categorySet.add(category);
                    findChildCategory(categorySet,categoryList,category.getId());
                }
            }
        }
        return categorySet;
    }
}
