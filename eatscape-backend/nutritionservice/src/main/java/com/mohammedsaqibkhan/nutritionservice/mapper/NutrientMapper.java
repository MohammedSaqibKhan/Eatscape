package com.mohammedsaqibkhan.nutritionservice.mapper;

import java.util.HashMap;
import java.util.Map;

public class NutrientMapper {
    // Define a map to hold nutrient information
    private static final Map<Integer, NutrientInfo> NUTRIENT_MAP = new HashMap<>();

    // Static block to initialize the nutrient data
    static {
        NUTRIENT_MAP.put(203, new NutrientInfo("Protein", "g", "Macronutrient"));
        NUTRIENT_MAP.put(204, new NutrientInfo("Total Fat", "g", "Macronutrient"));
        NUTRIENT_MAP.put(205, new NutrientInfo("Total Carbohydrate", "g", "Macronutrient"));
        NUTRIENT_MAP.put(207, new NutrientInfo("Ash", "g", "Other"));
        NUTRIENT_MAP.put(208, new NutrientInfo("Calories", "kcal", "Energy"));
        NUTRIENT_MAP.put(209, new NutrientInfo("Starch", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(210, new NutrientInfo("Sucrose", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(211, new NutrientInfo("Glucose", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(212, new NutrientInfo("Fructose", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(213, new NutrientInfo("Lactose", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(214, new NutrientInfo("Maltose", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(221, new NutrientInfo("Alcohol", "g", "Other"));
        NUTRIENT_MAP.put(255, new NutrientInfo("Water", "g", "Other"));
        NUTRIENT_MAP.put(262, new NutrientInfo("Caffeine", "mg", "Other"));
        NUTRIENT_MAP.put(263, new NutrientInfo("Theobromine", "mg", "Other"));
        NUTRIENT_MAP.put(268, new NutrientInfo("Energy", "kJ", "Energy"));
        NUTRIENT_MAP.put(269, new NutrientInfo("Sugars", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(291, new NutrientInfo("Dietary Fiber", "g", "Carbohydrates"));
        NUTRIENT_MAP.put(301, new NutrientInfo("Calcium", "mg", "Minerals"));
        NUTRIENT_MAP.put(303, new NutrientInfo("Iron", "mg", "Minerals"));
        NUTRIENT_MAP.put(304, new NutrientInfo("Magnesium", "mg", "Minerals"));
        NUTRIENT_MAP.put(305, new NutrientInfo("Phosphorus", "mg", "Minerals"));
        NUTRIENT_MAP.put(306, new NutrientInfo("Potassium", "mg", "Minerals"));
        NUTRIENT_MAP.put(307, new NutrientInfo("Sodium", "mg", "Minerals"));
        NUTRIENT_MAP.put(309, new NutrientInfo("Zinc", "mg", "Minerals"));
        NUTRIENT_MAP.put(312, new NutrientInfo("Copper", "mg", "Minerals"));
        NUTRIENT_MAP.put(313, new NutrientInfo("Fluoride", "mg", "Minerals"));
        NUTRIENT_MAP.put(315, new NutrientInfo("Manganese", "mg", "Minerals"));
        NUTRIENT_MAP.put(317, new NutrientInfo("Selenium", "mcg", "Minerals"));
        NUTRIENT_MAP.put(318, new NutrientInfo("Vitamin A", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(319, new NutrientInfo("Retinol", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(320, new NutrientInfo("Vitamin A, IU", "IU", "Vitamins"));
        NUTRIENT_MAP.put(321, new NutrientInfo("Beta Carotene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(322, new NutrientInfo("Alpha Carotene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(323, new NutrientInfo("Vitamin E", "mg", "Vitamins"));
        NUTRIENT_MAP.put(324, new NutrientInfo("Vitamin D", "IU", "Vitamins"));
        NUTRIENT_MAP.put(328, new NutrientInfo("Vitamin D (D2 + D3)", "IU", "Vitamins"));
        NUTRIENT_MAP.put(334, new NutrientInfo("Cryptoxanthin", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(337, new NutrientInfo("Lutein + Zeaxanthin", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(338, new NutrientInfo("Vitamin K", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(341, new NutrientInfo("Lycopene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(342, new NutrientInfo("Phytoene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(343, new NutrientInfo("Phytofluene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(401, new NutrientInfo("Vitamin C", "mg", "Vitamins"));
        NUTRIENT_MAP.put(404, new NutrientInfo("Thiamin (Vitamin B1)", "mg", "Vitamins"));
        NUTRIENT_MAP.put(405, new NutrientInfo("Riboflavin (Vitamin B2)", "mg", "Vitamins"));
        NUTRIENT_MAP.put(406, new NutrientInfo("Niacin (Vitamin B3)", "mg", "Vitamins"));
        NUTRIENT_MAP.put(410, new NutrientInfo("Pantothenic Acid", "mg", "Vitamins"));
        NUTRIENT_MAP.put(415, new NutrientInfo("Vitamin B6", "mg", "Vitamins"));
        NUTRIENT_MAP.put(417, new NutrientInfo("Folate", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(418, new NutrientInfo("Vitamin B12", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(421, new NutrientInfo("Choline", "mg", "Vitamins"));
        NUTRIENT_MAP.put(429, new NutrientInfo("Betaine", "mg", "Vitamins"));
        NUTRIENT_MAP.put(430, new NutrientInfo("Vitamin K2", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(431, new NutrientInfo("Folic Acid", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(432, new NutrientInfo("Dietary Folate Equivalents", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(435, new NutrientInfo("Folate, food", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(454, new NutrientInfo("Vitamin E, added", "mg", "Vitamins"));
        NUTRIENT_MAP.put(501, new NutrientInfo("Tryptophan", "g", "Amino Acids"));
        NUTRIENT_MAP.put(502, new NutrientInfo("Threonine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(503, new NutrientInfo("Isoleucine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(504, new NutrientInfo("Leucine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(505, new NutrientInfo("Lysine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(506, new NutrientInfo("Methionine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(507, new NutrientInfo("Cystine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(508, new NutrientInfo("Phenylalanine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(509, new NutrientInfo("Tyrosine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(510, new NutrientInfo("Valine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(511, new NutrientInfo("Arginine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(512, new NutrientInfo("Histidine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(513, new NutrientInfo("Alanine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(514, new NutrientInfo("Aspartic Acid", "g", "Amino Acids"));
        NUTRIENT_MAP.put(515, new NutrientInfo("Glutamic Acid", "g", "Amino Acids"));
        NUTRIENT_MAP.put(516, new NutrientInfo("Glycine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(517, new NutrientInfo("Proline", "g", "Amino Acids"));
        NUTRIENT_MAP.put(518, new NutrientInfo("Serine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(601, new NutrientInfo("Cholesterol", "mg", "Lipids"));
        NUTRIENT_MAP.put(605, new NutrientInfo("Trans Fat", "g", "Lipids"));
        NUTRIENT_MAP.put(606, new NutrientInfo("Saturated Fat", "g", "Lipids"));
        NUTRIENT_MAP.put(607, new NutrientInfo("4:0 Butyric Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(608, new NutrientInfo("6:0 Caproic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(609, new NutrientInfo("8:0 Caprylic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(610, new NutrientInfo("10:0 Capric Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(611, new NutrientInfo("12:0 Lauric Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(612, new NutrientInfo("14:0 Myristic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(613, new NutrientInfo("16:0 Palmitic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(614, new NutrientInfo("18:0 Stearic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(617, new NutrientInfo("18:1 Oleic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(618, new NutrientInfo("18:2 Linoleic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(619, new NutrientInfo("18:3 Linolenic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(620, new NutrientInfo("20:1 Eicosenoic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(621, new NutrientInfo("20:5 EPA", "g", "Lipids"));
        NUTRIENT_MAP.put(626, new NutrientInfo("22:1 Erucic Acid", "g", "Lipids"));
        NUTRIENT_MAP.put(627, new NutrientInfo("22:6 DHA", "g", "Lipids"));
        NUTRIENT_MAP.put(636, new NutrientInfo("Vitamin K, added", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(645, new NutrientInfo("Monounsaturated Fat", "g", "Lipids"));
        NUTRIENT_MAP.put(646, new NutrientInfo("Polyunsaturated Fat", "g", "Lipids"));
        NUTRIENT_MAP.put(539, new NutrientInfo("Fluoride", "mg", "Minerals"));
        NUTRIENT_MAP.put(639, new NutrientInfo("Tryptophan", "g", "Amino Acids"));
        NUTRIENT_MAP.put(326, new NutrientInfo("Vitamin C", "mg", "Vitamins"));
        NUTRIENT_MAP.put(325, new NutrientInfo("Lycopene", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(696, new NutrientInfo("Fatty Acids, Total Saturated", "g", "Lipids"));
        NUTRIENT_MAP.put(625, new NutrientInfo("Vitamin B12", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(652, new NutrientInfo("Vitamin B6", "mg", "Vitamins"));
        NUTRIENT_MAP.put(697, new NutrientInfo("Fatty Acids, Total Trans", "g", "Lipids"));
        NUTRIENT_MAP.put(673, new NutrientInfo("Lutein + Zeaxanthin", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(662, new NutrientInfo("Trans Fat, Total", "g", "Lipids"));
        NUTRIENT_MAP.put(653, new NutrientInfo("Vitamin E", "mg", "Vitamins"));
        NUTRIENT_MAP.put(687, new NutrientInfo("Phenylalanine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(674, new NutrientInfo("Vitamin A", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(663, new NutrientInfo("Monounsaturated Fat", "g", "Lipids"));
        NUTRIENT_MAP.put(859, new NutrientInfo("Serine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(670, new NutrientInfo("Omega-3 Fatty Acids", "g", "Lipids"));
        NUTRIENT_MAP.put(675, new NutrientInfo("Omega-6 Fatty Acids", "g", "Lipids"));
        NUTRIENT_MAP.put(669, new NutrientInfo("Saturated Fatty Acids", "g", "Lipids"));
        NUTRIENT_MAP.put(851, new NutrientInfo("Lysine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(685, new NutrientInfo("Alanine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(615, new NutrientInfo("Isoleucine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(628, new NutrientInfo("Leucine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(672, new NutrientInfo("Vitamin K", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(689, new NutrientInfo("Magnesium", "mg", "Minerals"));
        NUTRIENT_MAP.put(852, new NutrientInfo("Threonine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(853, new NutrientInfo("Methionine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(855, new NutrientInfo("Glutamine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(629, new NutrientInfo("Tyrosine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(857, new NutrientInfo("Cystein", "g", "Amino Acids"));
        NUTRIENT_MAP.put(624, new NutrientInfo("Proline", "g", "Amino Acids"));
        NUTRIENT_MAP.put(630, new NutrientInfo("Serine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(858, new NutrientInfo("Tryptophan", "g", "Amino Acids"));
        NUTRIENT_MAP.put(631, new NutrientInfo("Histidine", "g", "Amino Acids"));
        NUTRIENT_MAP.put(654, new NutrientInfo("Vitamin D", "mcg", "Vitamins"));
        NUTRIENT_MAP.put(671, new NutrientInfo("Calcium", "mg", "Minerals"));

    }

    // Method to fetch nutrient name, unit, and category
    public static NutrientInfo getNutrientInfo(int attrId) {
        return NUTRIENT_MAP.getOrDefault(attrId, new NutrientInfo("Unknown Nutrient", "Unknown Unit", "Unknown Category"));
    }

}

