package cn.xiaochi.controller.bankend;

import cn.xiaochi.common.Const;
import cn.xiaochi.common.Response;
import cn.xiaochi.pojo.User;
import cn.xiaochi.service.ICategoryService;
import cn.xiaochi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IUserService iUserService;

    /**
     * 新增分类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public Response<String> createCategory(HttpSession session, String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录");
        }
        // 验证是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            iCategoryService.createCategory(categoryName,parentId);
            return Response.success();
        }else{
            return Response.error("无权限操作,需要管理员权限");
        }
    }

    /**
     * 新增分类
     * @param session
     * @param categoryName
     * @param categoryId
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public Response<String> updateCategory(HttpSession session, String categoryName,int categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录");
        }
        // 验证是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            iCategoryService.updateCategory(categoryName,categoryId);
            return Response.success();
        }else{
            return Response.error("无权限操作,需要管理员权限");
        }
    }

    /**
     * 获取当前分类下的所有子类平级分类（平级）
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public Response getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录");
        }
        // 验证是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return Response.error("无权限操作,需要管理员权限");
        }
    }

    /**
     * (递归)获取当前分类下的所有子类平级信息
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public Response getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录");
        }
        // 验证是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            // 查询当前节点的id和递归子节点的id
            // 0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return Response.error("无权限操作,需要管理员权限");
        }
    }
}
