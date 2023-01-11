package com.example.demo.enity;

import java.util.ArrayList;
import java.util.List;

public class Poisk {
    public static Carta poisk(Carta input, String filter){
            if (input.getMesto().contains(filter)){
                return input;
            }
        return null;
    }
}
