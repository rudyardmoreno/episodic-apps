package com.example.episodicevents.events.dataTypes;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor

public class ForwardData {
    private int startOffset;
    private int endOffset;
    private double speed;
}
