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
public class StreamingProduct extends Product {
    private String streamUrl;

    public StreamingProduct(String sku, String name, int priceInCents, String streamUrl) {
        super(sku, name, priceInCents);
        this.streamUrl = streamUrl;
    }
    public String getProductCategory() {
        return "stream";
    }
}
