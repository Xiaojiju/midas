package com.mtfm.app_purchase.web.api;

import com.mtfm.app_purchase.service.purchase.BrandService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solar/api/v1")
public class BrandManageApi {

    private final BrandService brandService;

    public BrandManageApi(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brand/{id}")
    public RestResult<BrandDetails> getBrand(@PathVariable("id") long id) {
        return RestResult.success(this.brandService.getBrand(id));
    }
}
