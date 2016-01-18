package gois.io.bestbuycatalog.model;

import java.util.List;

/**
 * This class represents a department at BetBuy.com
 */
public class Department {

    /**
     * The id of the department.
     */
    private Integer id;

    /**
     * The name of the department.
     */
    private String name;

    /**
     * The list of products in this department.
     *
     * @see Product
     */
    private List<Product> products;

    /**
     * Class constructor.
     */
    public Department() {
    }

    /**
     * Returns the department's id.
     *
     * @return the department's id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the department's id.
     *
     * @param id the department's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the department's name.
     *
     * @return the department's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the department's name.
     *
     * @param name the department's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the list of department's products.
     *
     * @return the list of department's products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of department's products.
     *
     * @param products the list of department's products.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
