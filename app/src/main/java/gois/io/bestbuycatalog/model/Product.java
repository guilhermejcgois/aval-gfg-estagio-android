package gois.io.bestbuycatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a product at BetBuy.com
 */
public class Product implements Serializable {

    /**
     * The id of the product.
     */
    private Integer id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The brand of the product.
     */
    private String brand;

    /**
     * The price of the product ($).
     */
    private double price;

    /**
     * The url for item image.
     */
    private String urlItemImage;

    /**
     * The url for detail image.
     */
    private String urlDetailImage;

    /**
     * The description of the product.
     */
    private String description;

    /**
     * Class constructor.
     */
    public Product() {
    }

    /**
     * Returns the product's id.
     *
     * @return the product's id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the product's id.
     *
     * @param id the product's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the product's name.
     *
     * @return the product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product's name.
     *
     * @param name the product's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the brand of this product.
     * @return the brand of this product.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of this product.
     *
     * @param brand the brand of this product.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the price of this product.
     * @return the price of this product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of this product.
     * @param price the price of this product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the url for item image.
     *
     * @return the url for item image.
     */
    public String getUrlItemImage() {
        return urlItemImage;
    }

    /**
     * Sets the url for item image.
     *
     * @param urlItemImage the url for item image.
     */
    public void setUrlItemImage(String urlItemImage) {
        this.urlItemImage = urlItemImage;
    }

    /**
     * Returns the url for detail image.
     *
     * @return the url for detail image.
     */
    public String getUrlDetailImage() {
        return urlDetailImage;
    }

    /**
     * Sets the url for detail image.
     *
     * @param urlDetailImage the url for detail image.
     */
    public void setUrlDetailImage(String urlDetailImage) {
        this.urlDetailImage = urlDetailImage;
    }

    /**
     * Returns the description of this product.
     *
     * @return the description of this product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this product.
     *
     * @param description the description of this product.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
