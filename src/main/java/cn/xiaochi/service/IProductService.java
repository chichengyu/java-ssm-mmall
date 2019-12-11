package cn.xiaochi.service;

import cn.xiaochi.common.Response;
import cn.xiaochi.pojo.Product;

public interface IProductService {

    /**
     * 商品新增与修改共用一个接口
     * @param product
     * @return
     */
    public Response saveOrUpdateProduct(Product product);

    /**
     * 更新商品销售状态
     * @param productId
     * @param status
     * @return
     */
    Response setSaltStatus(Integer productId, Integer status);

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    Response getDetail(Integer productId);

    /**
     * 获取商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Response getList(int pageNum, int pageSize);

    /**
     * 搜索商品列表
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Response productSearch(String productName, int pageNum, int pageSize);
}
