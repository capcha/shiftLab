package shift.lab.models;

import lombok.Getter;

import java.util.List;

public class ProductList<T> {
    private final @Getter List<T> productList;

    public ProductList(List<T> productList) {
        this.productList = productList;
    }
}
