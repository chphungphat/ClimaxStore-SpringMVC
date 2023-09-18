package com.codegym.ClimaxStoreSpring.entity.product;

import com.codegym.ClimaxStoreSpring.entity.business.BoughtProduct;
import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_cover_url")
    private String productCoverUrl;

    @Column(name = "product_detail_description")
    private String productDetailDescription;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "reduction_rate")
    private Double reductionRate;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne(targetEntity = Developer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "developer_id", referencedColumnName = "developer_id")
    private Developer developer;

    @OneToMany(targetEntity = ProductImage.class, fetch = FetchType.LAZY, mappedBy = "product",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProductImage> productImageList;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER, mappedBy = "productList")
    private List<Category> categoryList;

    @OneToMany(targetEntity = CartDetail.class, fetch = FetchType.LAZY, mappedBy = "product",
            cascade = {CascadeType.PERSIST, CascadeType.PERSIST})
    private List<CartDetail> cartDetailList;

    @OneToMany(targetEntity = BoughtProduct.class, fetch = FetchType.LAZY, mappedBy = "product")
    private List<BoughtProduct> boughtProductList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCoverUrl() {
        return productCoverUrl;
    }

    public void setProductCoverUrl(String productCoverUrl) {
        this.productCoverUrl = productCoverUrl;
    }

    public String getProductDetailDescription() {
        return productDetailDescription;
    }

    public void setProductDetailDescription(String productDetailDescription) {
        this.productDetailDescription = productDetailDescription;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getReductionRate() {
        return reductionRate;
    }

    public void setReductionRate(Double reductionRate) {
        this.reductionRate = reductionRate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<CartDetail> getCartDetailList() {
        return cartDetailList;
    }

    public void setCartDetailList(List<CartDetail> cartDetailList) {
        this.cartDetailList = cartDetailList;
    }

    public List<BoughtProduct> getBoughtProductList() {
        return boughtProductList;
    }

    public void setBoughtProductList(List<BoughtProduct> boughtProductList) {
        this.boughtProductList = boughtProductList;
    }
}
