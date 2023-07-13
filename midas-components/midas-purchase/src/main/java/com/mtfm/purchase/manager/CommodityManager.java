package com.mtfm.purchase.manager;

import com.mtfm.purchase.manager.provisioning.CommodityDetails;

public interface CommodityManager {

    void createCommodity(CommodityDetails details);

    void updateCommodity(CommodityDetails details);
}
