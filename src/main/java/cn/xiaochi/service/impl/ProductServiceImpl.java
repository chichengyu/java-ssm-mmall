package cn.xiaochi.service.impl;

import cn.xiaochi.common.Response;
import cn.xiaochi.dao.CategoryDao;
import cn.xiaochi.dao.ProductDao;
import cn.xiaochi.pojo.Category;
import cn.xiaochi.pojo.Product;
import cn.xiaochi.service.IProductService;
import cn.xiaochi.util.DateTimeUtil;
import cn.xiaochi.util.PropertiesUtil;
import cn.xiaochi.vo.ProductDetailVo;
import cn.xiaochi.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 商品新增与修改共用一个接口
     * @param product
     * @return
     */
    public Response saveOrUpdateProduct(Product product){
        if (product != null){
            if (StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null){
                // 更新操作
                int rowCount = productDao.updateByPrimaryKeySelective(product);
                if (rowCount > 0) {
                    return Response.success("修改商品成功");
                }
                return Response.error("修改商品失败");
            }else {
                // 新增操作
                int rowCount = productDao.insertSelective(product);
                if (rowCount > 0) {
                    return Response.success("新增商品成功");
                }
                return Response.error("新增商品失败");
            }
        }
        return Response.error("新增或更新产品参数不正确");
    }

    /**
     * 更新商品销售状态
     * @param productId
     * @param status
     * @return
     */
    public Response setSaltStatus(Integer productId, Integer status){
        if (productId == null || status == null){
            return Response.error("参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowCount = productDao.updateByPrimaryKeySelective(product);
        if (rowCount > 0){
            return Response.success("修改产品销售状态成功");
        }
        return Response.error("修改产品销售状态失败");
    }

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    public Response getDetail(Integer productId){
        if (productId == null){
            return Response.error("参数错误");
        }
        Product product = productDao.selectByPrimaryKey(productId);
        if (product == null){
            return Response.error("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return Response.success(productDetailVo);
    }
    // 组装返回数据
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryDao.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    /**
     * 获取商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Response getList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productDao.selectAll();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        products.forEach(product -> productListVoList.add(assembleProductListVo(product)));
        PageInfo<ProductListVo> productPageInfo = new PageInfo<>(productListVoList);
        return Response.success(productPageInfo);
    }

    /**
     * 搜索商品列表
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Response productSearch(String productName, int pageNum, int pageSize){
        if (StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productDao.selectListSearchByProductName(productName);
        List<ProductListVo> productListVos = Lists.newArrayList();
        products.forEach(product -> productListVos.add(assembleProductListVo(product)));
        PageInfo<ProductListVo> productListVoPageInfo = new PageInfo<>(productListVos);
        return Response.success(productListVoPageInfo);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
}
