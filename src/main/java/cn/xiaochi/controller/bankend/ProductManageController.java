package cn.xiaochi.controller.bankend;

import cn.xiaochi.common.Const;
import cn.xiaochi.common.Response;
import cn.xiaochi.pojo.Product;
import cn.xiaochi.pojo.User;
import cn.xiaochi.service.IProductService;
import cn.xiaochi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IUserService iUserService;

    /**
     * 商品新增与修改共用一个接口
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public Response saveProduct(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return Response.error("无权限操作");
        }
    }

    /**
     * 更新商品销售状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("set_salt_status.do")
    @ResponseBody
    public Response saveProduct(HttpSession session, Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.setSaltStatus(productId,status);
        }else {
            return Response.error("无权限操作");
        }
    }

    /**
     * 获取商品详情
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public Response getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.getDetail(productId);
        }else {
            return Response.error("无权限操作");
        }
    }

    /**
     * 获取商品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("get_list.do")
    @ResponseBody
    public Response getList(HttpSession session,@RequestParam(value = "page",defaultValue = "1") int pageNum,@RequestParam(value = "limit",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.getList(pageNum,pageSize);
        }else {
            return Response.error("无权限操作");
        }
    }

    /**
     * 搜索商品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("get_search_list.do")
    @ResponseBody
    public Response productSearch(HttpSession session,String productName,@RequestParam(value = "page",defaultValue = "1") int pageNum,@RequestParam(value = "limit",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return Response.error("用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.productSearch(productName,pageNum,pageSize);
        }else {
            return Response.error("无权限操作");
        }
    }
}
