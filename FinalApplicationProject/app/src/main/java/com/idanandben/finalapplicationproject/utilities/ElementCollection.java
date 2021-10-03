package com.idanandben.finalapplicationproject.utilities;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElementCollection {

    private final Map<Integer, Element> elementMap = new HashMap<>();
    private final Map<Integer, Integer> colorMap = new HashMap<>();
    private final ArrayList<Integer> wantedList = new ArrayList<>();

    public ElementCollection() {
        createElementMap();
        createColorMap();
        createWantedList();
    }

    public Map<Integer, Element> getElements() {
        return elementMap;
    }

    public Map<Integer, Integer> getColorMap() { return colorMap; }

    public ArrayList<Integer> getWantedList() { return wantedList; }

    private void createElementMap() {
        elementMap.put(1, new Element(1,"H","Hydrogen",1,1,1.008,1));
        elementMap.put(2, new Element(2,"He","Helium",1,18,4.003,2));
        elementMap.put(3, new Element(3,"Li","Lithium",2,1,6.941,3));
        elementMap.put(4, new Element(4,"Be","Beryllium",2,2,9.012,4));
        elementMap.put(5, new Element(5,"B","Boron",2,13,10.811,5));
        elementMap.put(6, new Element(6, "C", "Carbon", 2, 14, 12.011, 1));
        elementMap.put(7, new Element(7, "N", "Nitrogen", 2, 15, 14.007, 1));
        elementMap.put(8, new Element(8, "O", "Oxygen", 2,16,15.999, 1));
        elementMap.put(9, new Element(9, "F", "Fluorine", 2, 17, 18.998,6));
        elementMap.put(10, new Element(10, "Ne", "Neon", 2, 18, 20.180, 2));
        elementMap.put(11, new Element(11, "Na", "Sodium", 3, 1, 22.990, 3));
        elementMap.put(12, new Element(12,"Mg", "Magnesium", 3, 2, 24.305, 4 ));
        elementMap.put(13, new Element(13, "Al", "Aluminium", 3, 13, 26.982, 7));
        elementMap.put(14, new Element(14, "Si", "Silicon", 3, 14, 28.086, 5));
        elementMap.put(15, new Element(15, "P", "Phosphorus", 3, 15, 30.974, 1));
        elementMap.put(16, new Element(16, "S", "Sulfur", 3, 16, 32.065, 1));
        elementMap.put(17, new Element(17, "Cl", "Chlorine", 3, 17, 35.453, 6));
        elementMap.put(18, new Element(18, "Ar", "Argon", 3, 18, 39.948, 2));
        elementMap.put(19, new Element(19, "K", "Potassium", 4, 1, 39.098, 3));
        elementMap.put(20, new Element(20, "Ca", "calcium", 4, 2, 40.08, 4));
        //מתכות
        elementMap.put(21, new Element(21, "Sc", "scandium", 4, 3, 44.96, 8));
        elementMap.put(22, new Element(22, "Ti", "titanium", 4, 4, 47.87, 8));
        elementMap.put(23, new Element(23, "V", "vanadium", 4, 5, 50.94	, 8));
        elementMap.put(24, new Element(24, "Cr", "chromium", 4, 6, 52.00, 8));
        elementMap.put(25, new Element(25, "Mn", "manganese", 4, 7, 54.94, 8));
        elementMap.put(26, new Element(26, "Fe", "iron", 4, 8, 55.85, 8));
        elementMap.put(27, new Element(27, "Co", "cobalt", 4, 9, 58.93, 8));
        elementMap.put(28, new Element(28, "Ni", "nickel", 4, 10, 58.69, 8));
        elementMap.put(29, new Element(29, "Cu", "copper", 4, 11, 63.55, 8));
        elementMap.put(30, new Element(30, "Zn", "zinc", 4, 12, 65.38, 8));
        //מתכות עמידות
        elementMap.put(31, new Element(31, "Ga", "gallium", 4, 13, 69.72, 7));
        //מתכות למחצה
        elementMap.put(32, new Element(32, "Ge", "germanium", 4, 14, 72.63, 5));
        elementMap.put(33, new Element(33, "As", "arsenic", 4, 15, 74.92, 5));
        elementMap.put(34, new Element(34, "Se", "selenium", 4, 16, 78.97, 1));
        elementMap.put(35, new Element(35, "Br", "bromine", 4, 17, 79.90, 6));
        elementMap.put(36, new Element(36, "Kr", "krypton", 4, 18, 83.80, 2));
        elementMap.put(37, new Element(37, "Rb", "Rubidium", 5, 1, 85.468, 3));
        elementMap.put(38, new Element(38, "Sr", "strontium", 5, 2, 87.62, 4));
        elementMap.put(39, new Element(39, "Y", "yttrium", 5, 3, 88.91, 8));
        elementMap.put(40, new Element(40, "Zr", "zirconium", 5, 4, 91.22, 8));
        elementMap.put(41, new Element(41, "Nb", "niobium", 5, 5, 92.91, 8));
        elementMap.put(42, new Element(42, "Mo", "molybdenum", 5, 6, 95.95, 8));
        elementMap.put(43, new Element(43, "Tc", "technetium", 5, 7, 98, 8));
        elementMap.put(44, new Element(44, "Ru", "ruthenium", 5, 8, 101.1	, 8));
        elementMap.put(45, new Element(45, "Rh", "rhodium", 5, 9, 102.9	, 8));
        elementMap.put(46, new Element(46, "Pd", "palladium", 5, 10, 106.4, 8));
        elementMap.put(47, new Element(47, "Ag", "silver", 5, 11, 107.9 , 8));
        elementMap.put(48, new Element(48, "Cd", "cadmium", 5, 12, 112.4, 8));
        elementMap.put(49, new Element(49, "In", "indium", 5, 13, 114.8, 7));
        elementMap.put(50, new Element(50, "Sn", "tin", 5, 14, 118.7, 7));
        elementMap.put(51, new Element(51, "Sb", "antimony", 5, 15, 121.8, 5));
        elementMap.put(52, new Element(52, "Te", "tellurium", 5, 16, 127.6, 5));
        elementMap.put(53, new Element(53, "I", "iodine", 5, 17, 126.9, 6));
        elementMap.put(54, new Element(54, "Xe", "xenon", 5, 18, 131.3, 2));
        elementMap.put(55, new Element(55, "Cs", "cesium", 6, 1, 132.9, 3));
        elementMap.put(56, new Element(56, "Ba", "barium", 6, 2, 137.3, 4));
        elementMap.put(71, new Element(71, "*", "*", 6, 3, 0, 0));
        elementMap.put(72, new Element(72, "Hf", "hafnium", 6, 4, 178.5, 8));
        elementMap.put(73, new Element(73, "Ta", "tantalum", 6, 5, 180.9, 8));
        elementMap.put(74, new Element(74, "W", "tungsten", 6, 6, 183.8, 8));
        elementMap.put(75, new Element(75, "Re", "rhenium", 6, 7, 186.2	, 8));
        elementMap.put(76, new Element(76, "Os", "osmium", 6, 8, 190.2, 8));
        elementMap.put(77, new Element(77, "Ir", "iridium", 6, 9, 192.2, 8));
        elementMap.put(78, new Element(78, "Pt", "platinum", 6, 10, 195.1, 8));
        elementMap.put(79, new Element(79, "Au", "gold", 6, 11, 197, 8));
        elementMap.put(80, new Element(80, "Hg", "mercury", 6, 12, 200.6, 8));
        elementMap.put(81, new Element(81, "TI", "thallium", 6, 13, 204.4, 7));
        elementMap.put(82, new Element(82, "Pb", "lead", 6, 14, 207.2, 7));
        elementMap.put(83, new Element(83, "Bi", "bismuth", 6, 15, 209.0, 7));
        elementMap.put(84, new Element(84, "Po", "polonium", 6, 16, 209, 5));
        elementMap.put(85, new Element(85, "At", "astatine", 6, 17, 210, 6));
        elementMap.put(86, new Element(86, "Rn", "radon", 6, 18, 222, 2));
        elementMap.put(87, new Element(87, "Fr", "Francium", 7, 1, 223, 3));
        elementMap.put(88, new Element(88, "Ra", "radon", 7, 2, 226, 4));
        elementMap.put(103, new Element(103, "**", "**", 7, 3, 0, 0));
        elementMap.put(104, new Element(104, "Rf", "rutherfordium", 7, 4, 267, 8));
        elementMap.put(105, new Element(105, "Db", "dubnium", 7, 5, 268, 8));
        elementMap.put(106, new Element(106, "Sg", "seaborgium", 7, 6, 271, 8));
        elementMap.put(107, new Element(107, "Bh", "bohrium", 7, 7, 272, 8));
        elementMap.put(108, new Element(108, "Hs", "hassium", 7, 8, 270, 8));
        elementMap.put(109, new Element(109, "Mt", "meitnerium", 7, 9, 276, 8));
        elementMap.put(110, new Element(110, "Ds", "darmstadtium", 7, 10, 281, 8));
        elementMap.put(111, new Element(111, "Rg", "roentgentium", 7, 11, 280, 8));
        elementMap.put(112, new Element(112, "Cn", "copernicum", 7, 12, 285, 8));
        elementMap.put(113, new Element(113, "Nh", "nihonium", 7, 13, 284, 7));
        elementMap.put(114, new Element(114, "Fi", "flerovium", 7, 14, 289, 7));
        elementMap.put(115, new Element(115, "Mc", "moscovium", 7, 15, 288, 7));
        elementMap.put(116, new Element(116, "Lv", "livermorium", 7, 16, 293, 7));
        elementMap.put(117, new Element(117, "Ts", "tennessine", 7, 17, 292, 6));
        elementMap.put(118, new Element(118, "Og", "oganesson", 7, 18, 294, 2));
    }

    private void createColorMap() {
        colorMap.put(0, Color.parseColor("#ffffff"));
        colorMap.put(1, Color.parseColor("#ff6666"));
        colorMap.put(2, Color.parseColor("#fedead"));
        colorMap.put(3, Color.parseColor("#ffbffe"));
        colorMap.put(4, Color.parseColor("#ff99cb"));
        colorMap.put(5, Color.parseColor("#ffc0bf"));
        colorMap.put(6, Color.parseColor("#cccccc"));
        colorMap.put(7, Color.parseColor("#cccc9a"));
        colorMap.put(8, Color.parseColor("#f1ff90"));
    }

    private void createWantedList() {
        wantedList.add(1);
        wantedList.add(2);
        wantedList.add(5);
        wantedList.add(7);
        wantedList.add(8);
        wantedList.add(9);
        wantedList.add(10);
        wantedList.add(11);
        wantedList.add(13);
        wantedList.add(14);
        wantedList.add(15);
        wantedList.add(16);
        wantedList.add(17);
        wantedList.add(18);
        wantedList.add(19);
        wantedList.add(20);
        wantedList.add(22);
        wantedList.add(24);
        wantedList.add(26);
        wantedList.add(28);
        wantedList.add(29);
        wantedList.add(30);
        wantedList.add(35);
        wantedList.add(47);
        wantedList.add(50);
        wantedList.add(53);
        wantedList.add(78);
        wantedList.add(79);
        wantedList.add(80);
        wantedList.add(82);
        wantedList.add(84);
    }
}
