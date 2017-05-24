package com.example.episodicevents.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Rudyard Moreno on 5/22/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@Getter
@Setter
@NoArgsConstructor
public class DigitalProduct extends Product {
    private String fileUrl;

    public DigitalProduct(String sku, String name, int priceInCents, String fileUrl) {
        super(sku, name, priceInCents);
        this.fileUrl = fileUrl;
    }
    public String getProductCategory() {
        return "media";
    }
}
