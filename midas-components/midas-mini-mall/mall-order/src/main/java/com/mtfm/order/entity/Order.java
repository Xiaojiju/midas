package com.mtfm.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

@TableName(value = "mall_order", autoResultMap = true)
public class Order extends BaseModel<Order> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("order_code")
    private String orderCode;

    @TableField(value = "had_wrote", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge hadWrote;

    @TableField(value = "order_status", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private OrderStatus orderStatus;

    @TableField("price_before_discount")
    private String priceBeforeDiscount;

    @TableField("price_after_discount")
    private String priceAfterDiscount;

    @TableField("expressPrice")
    private String expressPrice;

    @TableField("payment_amount")
    private String paymentAmount;

    @TableField(value = "payment_method", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private PaymentMethod paymentMethod;

    @TableField("express_address_id")
    private Long expressAddressId;

    @TableField("spending_points")
    private Integer spendingPoints;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Judge getHadWrote() {
        return hadWrote;
    }

    public void setHadWrote(Judge hadWrote) {
        this.hadWrote = hadWrote;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(String priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public String getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(String priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getExpressAddressId() {
        return expressAddressId;
    }

    public void setExpressAddressId(Long expressAddressId) {
        this.expressAddressId = expressAddressId;
    }

    public Integer getSpendingPoints() {
        return spendingPoints;
    }

    public void setSpendingPoints(Integer spendingPoints) {
        this.spendingPoints = spendingPoints;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
