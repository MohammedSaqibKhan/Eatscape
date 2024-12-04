package com.mohammedsaqibkhan.nutritionservice.mapper;

import java.util.HashMap;
import java.util.Map;

public class NutrientMapper {
    private static final Map<Integer, String> NUTRIENT_MAP = new HashMap<>();

    static {
        NUTRIENT_MAP.put(203, "Protein");
        NUTRIENT_MAP.put(204, "Total Fat");
        NUTRIENT_MAP.put(205, "Total Carbohydrate");
        NUTRIENT_MAP.put(207, "Ash");
        NUTRIENT_MAP.put(208, "Calories");
        NUTRIENT_MAP.put(209, "Starch");
        NUTRIENT_MAP.put(210, "Sucrose");
        NUTRIENT_MAP.put(211, "Glucose");
        NUTRIENT_MAP.put(212, "Fructose");
        NUTRIENT_MAP.put(213, "Lactose");
        NUTRIENT_MAP.put(214, "Maltose");
        NUTRIENT_MAP.put(221, "Alcohol");
        NUTRIENT_MAP.put(255, "Water");
        NUTRIENT_MAP.put(262, "Caffeine");
        NUTRIENT_MAP.put(263, "Theobromine");
        NUTRIENT_MAP.put(268, "Energy in kilojoules");
        NUTRIENT_MAP.put(269, "Sugars");
        NUTRIENT_MAP.put(291, "Dietary Fiber");
        NUTRIENT_MAP.put(301, "Calcium");
        NUTRIENT_MAP.put(303, "Iron");
        NUTRIENT_MAP.put(304, "Magnesium");
        NUTRIENT_MAP.put(305, "Phosphorus");
        NUTRIENT_MAP.put(306, "Potassium");
        NUTRIENT_MAP.put(307, "Sodium");
        NUTRIENT_MAP.put(309, "Zinc");
        NUTRIENT_MAP.put(312, "Copper");
        NUTRIENT_MAP.put(313, "Fluoride");
        NUTRIENT_MAP.put(315, "Manganese");
        NUTRIENT_MAP.put(317, "Selenium");
        NUTRIENT_MAP.put(318, "Vitamin A");
        NUTRIENT_MAP.put(319, "Retinol");
        NUTRIENT_MAP.put(320, "Vitamin A, IU");
        NUTRIENT_MAP.put(321, "Beta Carotene");
        NUTRIENT_MAP.put(322, "Alpha Carotene");
        NUTRIENT_MAP.put(323, "Vitamin E");
        NUTRIENT_MAP.put(324, "Vitamin D");
        NUTRIENT_MAP.put(328, "Vitamin D (D2 + D3)");
        NUTRIENT_MAP.put(334, "Cryptoxanthin");
        NUTRIENT_MAP.put(337, "Lutein + Zeaxanthin");
        NUTRIENT_MAP.put(338, "Vitamin K");
        NUTRIENT_MAP.put(341, "Lycopene");
        NUTRIENT_MAP.put(342, "Phytoene");
        NUTRIENT_MAP.put(343, "Phytofluene");
        NUTRIENT_MAP.put(401, "Vitamin C");
        NUTRIENT_MAP.put(404, "Thiamin (Vitamin B1)");
        NUTRIENT_MAP.put(405, "Riboflavin (Vitamin B2)");
        NUTRIENT_MAP.put(406, "Niacin (Vitamin B3)");
        NUTRIENT_MAP.put(410, "Pantothenic Acid");
        NUTRIENT_MAP.put(415, "Vitamin B6");
        NUTRIENT_MAP.put(417, "Folate");
        NUTRIENT_MAP.put(418, "Vitamin B12");
        NUTRIENT_MAP.put(421, "Choline");
        NUTRIENT_MAP.put(429, "Betaine");
        NUTRIENT_MAP.put(430, "Vitamin K2");
        NUTRIENT_MAP.put(431, "Folic Acid");
        NUTRIENT_MAP.put(432, "Dietary Folate Equivalents");
        NUTRIENT_MAP.put(435, "Folate, food");
        NUTRIENT_MAP.put(454, "Vitamin E, added");
        NUTRIENT_MAP.put(501, "Tryptophan");
        NUTRIENT_MAP.put(502, "Threonine");
        NUTRIENT_MAP.put(503, "Isoleucine");
        NUTRIENT_MAP.put(504, "Leucine");
        NUTRIENT_MAP.put(505, "Lysine");
        NUTRIENT_MAP.put(506, "Methionine");
        NUTRIENT_MAP.put(507, "Cystine");
        NUTRIENT_MAP.put(508, "Phenylalanine");
        NUTRIENT_MAP.put(509, "Tyrosine");
        NUTRIENT_MAP.put(510, "Valine");
        NUTRIENT_MAP.put(511, "Arginine");
        NUTRIENT_MAP.put(512, "Histidine");
        NUTRIENT_MAP.put(513, "Alanine");
        NUTRIENT_MAP.put(514, "Aspartic Acid");
        NUTRIENT_MAP.put(515, "Glutamic Acid");
        NUTRIENT_MAP.put(516, "Glycine");
        NUTRIENT_MAP.put(517, "Proline");
        NUTRIENT_MAP.put(518, "Serine");
        NUTRIENT_MAP.put(601, "Cholesterol");
        NUTRIENT_MAP.put(605, "Trans Fat");
        NUTRIENT_MAP.put(606, "Saturated Fat");
        NUTRIENT_MAP.put(607, "4:0 Butyric Acid");
        NUTRIENT_MAP.put(608, "6:0 Caproic Acid");
        NUTRIENT_MAP.put(609, "8:0 Caprylic Acid");
        NUTRIENT_MAP.put(610, "10:0 Capric Acid");
        NUTRIENT_MAP.put(611, "12:0 Lauric Acid");
        NUTRIENT_MAP.put(612, "14:0 Myristic Acid");
        NUTRIENT_MAP.put(613, "16:0 Palmitic Acid");
        NUTRIENT_MAP.put(614, "18:0 Stearic Acid");
        NUTRIENT_MAP.put(617, "18:1 Oleic Acid");
        NUTRIENT_MAP.put(618, "18:2 Linoleic Acid");
        NUTRIENT_MAP.put(619, "18:3 Linolenic Acid");
        NUTRIENT_MAP.put(620, "20:1 Eicosenoic Acid");
        NUTRIENT_MAP.put(621, "20:5 EPA");
        NUTRIENT_MAP.put(626, "22:1 Erucic Acid");
        NUTRIENT_MAP.put(627, "22:6 DHA");
        NUTRIENT_MAP.put(636, "Vitamin K, added");
        NUTRIENT_MAP.put(645, "Monounsaturated Fat");
        NUTRIENT_MAP.put(646, "Polyunsaturated Fat");
        NUTRIENT_MAP.put(328, "Vitamin D3 (Cholecalciferol)");
        NUTRIENT_MAP.put(429, "Betaine");
        NUTRIENT_MAP.put(432, "Dietary Folate Equivalents");
        NUTRIENT_MAP.put(454, "Vitamin E, added");
        NUTRIENT_MAP.put(521, "Omega-3 Fatty Acids");
        NUTRIENT_MAP.put(522, "Omega-6 Fatty Acids");
        NUTRIENT_MAP.put(512, "Zeaxanthin");
        NUTRIENT_MAP.put(513, "Cryptoxanthin");
        NUTRIENT_MAP.put(514, "Molybdenum");
        NUTRIENT_MAP.put(518, "Iodine");
        NUTRIENT_MAP.put(525, "Vitamin K2 (MK-4)");
        NUTRIENT_MAP.put(526, "Vitamin K2 (MK-7)");
        NUTRIENT_MAP.put(601, "Cholesterol");
        NUTRIENT_MAP.put(626, "DHA (Docosahexaenoic Acid)");
        NUTRIENT_MAP.put(627, "EPA (Eicosapentaenoic Acid)");
        NUTRIENT_MAP.put(628, "Vitamin B5 (Pantothenic Acid)");
        NUTRIENT_MAP.put(645, "Omega-9 Fatty Acids");
        NUTRIENT_MAP.put(646, "Phytosterols");
        NUTRIENT_MAP.put(647, "Lignans");
        NUTRIENT_MAP.put(648, "Polyphenols");
        NUTRIENT_MAP.put(649, "Taurine");
        NUTRIENT_MAP.put(650, "Resveratrol");
        NUTRIENT_MAP.put(651, "Glucosinolates");
        NUTRIENT_MAP.put(652, "Chlorophyll");
        NUTRIENT_MAP.put(653, "Melatonin");
        NUTRIENT_MAP.put(654, "Beta-cryptoxanthin");
        NUTRIENT_MAP.put(655, "Galactose");
    }

    public static String getNutrientName(int attrId) {
        return NUTRIENT_MAP.getOrDefault(attrId, "Unknown Nutrient");
    }
}
