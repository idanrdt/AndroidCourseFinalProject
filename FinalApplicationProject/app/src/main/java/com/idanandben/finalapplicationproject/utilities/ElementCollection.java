package com.idanandben.finalapplicationproject.utilities;

import com.idanandben.finalapplicationproject.widgets.ElementTableBlock;

import java.util.HashMap;
import java.util.Map;

public class ElementCollection {

    private static final Map<Integer, Element> ElementMap = new HashMap<>();

    public ElementCollection() {
        CreateElementMap();
    }

    public Map<Integer, Element> GetElements() {
        return ElementMap;
    }

    private void CreateElementMap() {
        ElementMap.put(1, new Element(1,"H","Hydrogen",1,1,1.008,1));
        ElementMap.put(2, new Element(2,"He","Helium",1,18,4.003,2));
        ElementMap.put(3, new Element(3,"Li","Lithium",2,1,6.941,3));
        ElementMap.put(4, new Element(4,"Be","Beryllium",2,2,9.012,4));
        ElementMap.put(5, new Element(5,"B","Boron",2,13,10.811,5));
        ElementMap.put(6, new Element(6, "C", "Carbon", 2, 14, 12.011, 1));
        ElementMap.put(7, new Element(7, "N", "Nitrogen", 2, 15, 14.007, 1));
        ElementMap.put(8, new Element(8, "O", "Oxygen", 2,16,15.999, 1));
        ElementMap.put(9, new Element(9, "F", "Fluorine", 2, 17, 18.998,6));
        ElementMap.put(10, new Element(10, "Ne", "Neon", 2, 18, 20.180, 2));
        ElementMap.put(11, new Element(11, "Na", "Sodium", 3, 1, 22.990, 3));
        ElementMap.put(12, new Element(12,"Mg", "Magnesium", 3, 2, 24.305, 4 ));
        ElementMap.put(13, new Element(13, "Al", "Aluminium", 3, 13, 26.982, 7));
        ElementMap.put(14, new Element(14, "Si", "Silicon", 3, 14, 28.086, 5));
        ElementMap.put(15, new Element(15, "P", "Phosphorus", 3, 15, 30.974, 5));
        ElementMap.put(16, new Element(16, "S", "Sulfur", 3, 16, 32.065, 5));
        ElementMap.put(17, new Element(17, "Cl", "Chlorine", 3, 17, 35.453, 6));
        ElementMap.put(18, new Element(18, "Ar", "Argon", 3, 18, 39.948, 2));
        ElementMap.put(19, new Element(19, "K", "Potassium", 4, 1, 39.098, 3));

















        ElementMap.put(37, new Element(37, "Rb", "Rubidium", 5, 1, 85.468, 2));

















        ElementMap.put(55, new Element(55, "Cs", "Cerium", 6, 1, 132.906, 3));
















        ElementMap.put(87, new Element(87, "Fr", "Francium", 7, 1, 223, 3));
    }
}
