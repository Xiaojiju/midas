package com.mtfm.backend_mall.web.api;

import com.mtfm.backend_mall.service.purchase.BrandService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solar/api/v1")
public class BrandManageApi {

    private final BrandService brandService;

    public BrandManageApi(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brand")
    public RestResult<Void> getBrand(@RequestBody BrandDetails details) {
        details.setIndexLetter(null);
        this.brandService.saveOne(details);
        return RestResult.success();
    }
}
