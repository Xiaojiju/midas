package com.mtfm.express.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.express.entity.Express;
import com.mtfm.express.manager.provisioning.ExpressItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpressMapper extends BaseMapper<Express> {
    /**
     * 查询物流简要信息
     * @param expressService 物流名称
     * @return 物流简要信息
     */
    List<ExpressItem> selectExpressItem(String expressService);
}
