package com.mtfm.purchase.manager.service;

import com.mtfm.purchase.entity.CommodityImage;
import com.mtfm.purchase.manager.ImageManager;

import java.util.Collection;
import java.util.List;

public class CommodityImageService implements ImageManager<CommodityImage> {

    @Override
    public void setImages(long id, List<CommodityImage> images) {

    }

    @Override
    public void removeImages(long id, Collection<Long> images) {

    }

    @Override
    public List<CommodityImage> loadImages(long id) {
        return null;
    }
}
