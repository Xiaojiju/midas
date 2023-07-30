package com.mtfm.purchase.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.purchase.entity.Commodity;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.Sku;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理mapper
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    /**
     * 查询规格库存
     * @param spuId 商品id
     * @return 库存
     */
    List<Sku> selectStocks(long spuId);
    /**
     *
     * 商品系列列表
     * 由于查询的结果需要1对多的组合，使用mybatisPlus的分页会导致结果根据行数进行返回，但是需要根据spu的数量来进行分页，所以使用自定义limit分页，
     * 后续如果业务量上分，需要使用根据id来分页
     * @param query 过滤参数
     * @return 分页
     */
    List<CommoditySplitDetails> selectCommodities(@Param("query") SplitPageQuery query);

    /**
     * 查询总数
     * @param query 过滤参数
     * @return 总数
     */
    long selectSpuCount(@Param("query") SplitPageQuery query);

    /**
     * 查询系列下的所有规格商品
     * @param spuId 商品id
     * @return 所有规格商品
     */
    List<CommodityDetails> selectCommodityDetails(@Param("spuId") long spuId);

    /**
     * 查询规格商品详情
     * @param commodityId 商品id
     * @return 商品详情
     */
    CommodityDetails selectCommodityById(long commodityId);

    /**
     * 查询规格商品的简要信息
     * @param query 过滤条件
     * @return 简要信息
     */
    List<CommodityView> selectViews(@Param("query") CommodityPageQuery query);

    long selectTotal(@Param("query") CommodityPageQuery query);
}
