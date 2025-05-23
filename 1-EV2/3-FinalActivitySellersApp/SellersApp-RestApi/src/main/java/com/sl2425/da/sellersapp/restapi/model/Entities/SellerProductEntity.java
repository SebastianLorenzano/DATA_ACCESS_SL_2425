package com.sl2425.da.sellersapp.restapi.model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "seller_products")
public class SellerProductEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_products_id_gen")
    @SequenceGenerator(name = "seller_products_id_gen", sequenceName = "seller_products_seller_product_id_seq", allocationSize = 1)
    @Column(name = "seller_product_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties({"id", "name", "plainPassword", "password", "pro"})
    private SellerEntity seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "offer_price", precision = 10, scale = 2)
    private BigDecimal offerPrice;

    @Column(name = "offer_start_date")
    private LocalDate offerStartDate;

    @Column(name = "offer_end_date")
    private LocalDate offerEndDate;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    public SellerProductEntity()
    {
    }


    public SellerProductEntity(SellerEntity seller,
                               ProductEntity product, BigDecimal price, Integer stock)
    {
        this.seller = seller;
        this.product = product;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public SellerEntity getSeller()
    {
        return seller;
    }

    public void setSeller(SellerEntity seller)
    {
        this.seller = seller;
    }

    public ProductEntity getProduct()
    {
        return product;
    }

    public void setProduct(ProductEntity product)
    {
        this.product = product;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getOfferPrice()
    {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice)
    {
        this.offerPrice = offerPrice;
    }

    public LocalDate getOfferStartDate()
    {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate)
    {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate()
    {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate)
    {
        this.offerEndDate = offerEndDate;
    }

    public Integer getStock()
    {
        return stock;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

}