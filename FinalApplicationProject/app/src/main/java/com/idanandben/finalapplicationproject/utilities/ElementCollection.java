package com.idanandben.finalapplicationproject.utilities;

import android.content.Context;
import android.graphics.Color;

import com.idanandben.finalapplicationproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the whole element collection. Also contain Family names and group numbers for elements.
 */
public class ElementCollection {

    private final Map<Integer, Element> elementMap = new HashMap<>();
    private final Map<Integer, Integer> colorMap = new HashMap<>();
    private final ArrayList<Integer> wantedList = new ArrayList<>();
    private final Context context;

    public ElementCollection(Context context) {
        this.context = context;
        createElementMap();
        createColorMap();
        createWantedList();
    }

    public Map<Integer, Element> getElements() {
        return elementMap;
    }

    public Map<Integer, Integer> getColorMap() { return colorMap; }

    public ArrayList<Integer> getWantedList() { return wantedList; }

    public String[] getFamilyNames() {
        return new String[] { context.getString(R.string.element_family_name_none), context.getString(R.string.element_family_name_non_Metal), context.getString(R.string.element_family_name_noble_gas), context.getString(R.string.element_family_name_alkali_metal),
                context.getString(R.string.element_family_name_alkaline_earth), context.getString(R.string.element_family_name_semi_metal), context.getString(R.string.element_family_name_halogen_gas), context.getString(R.string.element_family_name_basic_metal), context.getString(R.string.element_family_name_transition_metal) };
    }

    private void createElementMap() {
        elementMap.put(1, new Element(1,"H",context.getString(R.string.element_H),1,1,1.008,1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(2, new Element(2,"He",context.getString(R.string.element_He),1,18,4.003,2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(3, new Element(3,"Li",context.getString(R.string.element_Li),2,1,6.941,3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(4, new Element(4,"Be",context.getString(R.string.element_Be),2,2,9.012,4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(5, new Element(5,"B",context.getString(R.string.element_B),2,13,10.811,5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(6, new Element(6, "C", context.getString(R.string.element_C), 2, 14, 12.011, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(7, new Element(7, "N", context.getString(R.string.element_N), 2, 15, 14.007, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(8, new Element(8, "O", context.getString(R.string.element_O), 2,16,15.999, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(9, new Element(9, "F", context.getString(R.string.element_F), 2, 17, 18.998,6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(10, new Element(10, "Ne", context.getString(R.string.element_Ne), 2, 18, 20.180, 2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(11, new Element(11, "Na", context.getString(R.string.element_Na), 3, 1, 22.990, 3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(12, new Element(12,"Mg", context.getString(R.string.element_Mg), 3, 2, 24.305, 4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(13, new Element(13, "Al", context.getString(R.string.element_Al), 3, 13, 26.982, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(14, new Element(14, "Si", context.getString(R.string.element_Si), 3, 14, 28.086, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(15, new Element(15, "P", context.getString(R.string.element_P), 3, 15, 30.974, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(16, new Element(16, "S", context.getString(R.string.element_S), 3, 16, 32.065, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(17, new Element(17, "Cl", context.getString(R.string.element_Cl), 3, 17, 35.453, 6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(18, new Element(18, "Ar", context.getString(R.string.element_Ar), 3, 18, 39.948, 2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(19, new Element(19, "K", context.getString(R.string.element_K), 4, 1, 39.098, 3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(20, new Element(20, "Ca", context.getString(R.string.element_Ca), 4, 2, 40.08, 4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(21, new Element(21, "Sc", context.getString(R.string.element_Sc), 4, 3, 44.96, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(22, new Element(22, "Ti", context.getString(R.string.element_Ti), 4, 4, 47.87, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(23, new Element(23, "V", context.getString(R.string.element_V), 4, 5, 50.94	, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(24, new Element(24, "Cr", context.getString(R.string.element_Cr), 4, 6, 52.00, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(25, new Element(25, "Mn", context.getString(R.string.element_Mn), 4, 7, 54.94, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(26, new Element(26, "Fe", context.getString(R.string.element_Fe), 4, 8, 55.85, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(27, new Element(27, "Co", context.getString(R.string.element_Co), 4, 9, 58.93, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(28, new Element(28, "Ni", context.getString(R.string.element_Ni), 4, 10, 58.69, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(29, new Element(29, "Cu", context.getString(R.string.element_Cu), 4, 11, 63.55, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(30, new Element(30, "Zn", context.getString(R.string.element_Zn), 4, 12, 65.38, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(31, new Element(31, "Ga", context.getString(R.string.element_Ga), 4, 13, 69.72, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(32, new Element(32, "Ge", context.getString(R.string.element_Ge), 4, 14, 72.63, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(33, new Element(33, "As", context.getString(R.string.element_As), 4, 15, 74.92, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(34, new Element(34, "Se", context.getString(R.string.element_Se), 4, 16, 78.97, 1, context.getString(R.string.element_family_name_non_Metal)));
        elementMap.put(35, new Element(35, "Br", context.getString(R.string.element_Br), 4, 17, 79.90, 6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(36, new Element(36, "Kr", context.getString(R.string.element_Kr), 4, 18, 83.80, 2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(37, new Element(37, "Rb", context.getString(R.string.element_Rb), 5, 1, 85.468, 3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(38, new Element(38, "Sr", context.getString(R.string.element_Sr), 5, 2, 87.62, 4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(39, new Element(39, "Y", context.getString(R.string.element_Y), 5, 3, 88.91, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(40, new Element(40, "Zr", context.getString(R.string.element_Zr), 5, 4, 91.22, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(41, new Element(41, "Nb", context.getString(R.string.element_Nb), 5, 5, 92.91, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(42, new Element(42, "Mo", context.getString(R.string.element_Mo), 5, 6, 95.95, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(43, new Element(43, "Tc", context.getString(R.string.element_Tc), 5, 7, 98, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(44, new Element(44, "Ru", context.getString(R.string.element_Ru), 5, 8, 101.1	, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(45, new Element(45, "Rh", context.getString(R.string.element_Rh), 5, 9, 102.9	, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(46, new Element(46, "Pd", context.getString(R.string.element_Pd), 5, 10, 106.4, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(47, new Element(47, "Ag", context.getString(R.string.element_Ag), 5, 11, 107.9 , 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(48, new Element(48, "Cd", context.getString(R.string.element_Cd), 5, 12, 112.4, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(49, new Element(49, "In", context.getString(R.string.element_In), 5, 13, 114.8, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(50, new Element(50, "Sn", context.getString(R.string.element_Sn), 5, 14, 118.7, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(51, new Element(51, "Sb", context.getString(R.string.element_Sb), 5, 15, 121.8, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(52, new Element(52, "Te", context.getString(R.string.element_Te), 5, 16, 127.6, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(53, new Element(53, "I", context.getString(R.string.element_I), 5, 17, 126.9, 6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(54, new Element(54, "Xe", context.getString(R.string.element_Xe), 5, 18, 131.3, 2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(55, new Element(55, "Cs", context.getString(R.string.element_Cs), 6, 1, 132.9, 3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(56, new Element(56, "Ba", context.getString(R.string.element_Ba), 6, 2, 137.3, 4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(71, new Element(71, "*", "*", 6, 3, 0, 0, context.getString(R.string.element_family_name_none)));
        elementMap.put(72, new Element(72, "Hf", context.getString(R.string.element_Hf), 6, 4, 178.5, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(73, new Element(73, "Ta", context.getString(R.string.element_Ta), 6, 5, 180.9, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(74, new Element(74, "W", context.getString(R.string.element_W), 6, 6, 183.8, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(75, new Element(75, "Re", context.getString(R.string.element_Re), 6, 7, 186.2	, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(76, new Element(76, "Os", context.getString(R.string.element_Os), 6, 8, 190.2, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(77, new Element(77, "Ir", context.getString(R.string.element_Ir), 6, 9, 192.2, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(78, new Element(78, "Pt", context.getString(R.string.element_Pt), 6, 10, 195.1, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(79, new Element(79, "Au", context.getString(R.string.element_Au), 6, 11, 197, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(80, new Element(80, "Hg", context.getString(R.string.element_Hg), 6, 12, 200.6, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(81, new Element(81, "Tl", context.getString(R.string.element_Tl), 6, 13, 204.4, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(82, new Element(82, "Pb", context.getString(R.string.element_Pb), 6, 14, 207.2, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(83, new Element(83, "Bi", context.getString(R.string.element_Bi), 6, 15, 209.0, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(84, new Element(84, "Po", context.getString(R.string.element_Po), 6, 16, 209, 5, context.getString(R.string.element_family_name_semi_metal)));
        elementMap.put(85, new Element(85, "At", context.getString(R.string.element_At), 6, 17, 210, 6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(86, new Element(86, "Rn", context.getString(R.string.element_Rn), 6, 18, 222, 2, context.getString(R.string.element_family_name_noble_gas)));
        elementMap.put(87, new Element(87, "Fr", context.getString(R.string.element_Fr), 7, 1, 223, 3, context.getString(R.string.element_family_name_alkali_metal)));
        elementMap.put(88, new Element(88, "Ra", context.getString(R.string.element_Ra), 7, 2, 226, 4, context.getString(R.string.element_family_name_alkaline_earth)));
        elementMap.put(103, new Element(103, "**", "**", 7, 3, 0, 0, context.getString(R.string.element_family_name_none)));
        elementMap.put(104, new Element(104, "Rf", context.getString(R.string.element_Rf), 7, 4, 267, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(105, new Element(105, "Db", context.getString(R.string.element_Db), 7, 5, 268, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(106, new Element(106, "Sg", context.getString(R.string.element_Sg), 7, 6, 271, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(107, new Element(107, "Bh", context.getString(R.string.element_Bh), 7, 7, 272, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(108, new Element(108, "Hs", context.getString(R.string.element_Hs), 7, 8, 270, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(109, new Element(109, "Mt", context.getString(R.string.element_Mt), 7, 9, 276, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(110, new Element(110, "Ds", context.getString(R.string.element_Ds), 7, 10, 281, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(111, new Element(111, "Rg", context.getString(R.string.element_Rg), 7, 11, 280, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(112, new Element(112, "Cn", context.getString(R.string.element_Cn), 7, 12, 285, 8, context.getString(R.string.element_family_name_transition_metal)));
        elementMap.put(113, new Element(113, "Nh", context.getString(R.string.element_Nh), 7, 13, 284, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(114, new Element(114, "Fl", context.getString(R.string.element_Fi), 7, 14, 289, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(115, new Element(115, "Mc", context.getString(R.string.element_Mc), 7, 15, 288, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(116, new Element(116, "Lv", context.getString(R.string.element_Lv), 7, 16, 293, 7, context.getString(R.string.element_family_name_basic_metal)));
        elementMap.put(117, new Element(117, "Ts", context.getString(R.string.element_Ts), 7, 17, 292, 6, context.getString(R.string.element_family_name_halogen_gas)));
        elementMap.put(118, new Element(118, "Og", context.getString(R.string.element_Og), 7, 18, 294, 2, context.getString(R.string.element_family_name_noble_gas)));
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
