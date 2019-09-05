package com.groundpush.security.core.properties;

/**
 * @description:  一键登录配置
 * @author: zhangxinzhong
 * @date: 2019-09-05 上午10:51
 */
public class OclProperties {

    private String regionId;

    private String endpoint;

    private String product;

    private String domain;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
