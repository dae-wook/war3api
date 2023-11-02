package com.daesoo.war3api.mrpg.model.dto;

import lombok.Data;
import lombok.ToString;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@ToString
public class MrpgEquipmentDto {

    private String name;
    private String dropMonster;
    private String ingredients;
    private String type;
    private String option;
    private String dedicatedOption;
    private String passive;
    private String active;
    private String grade;
    private int level;

    private static final Pattern NAME_PATTERN = Pattern.compile("^(\\S+?)\\s+유형:\\s+\\[(\\S+?)\\]");
    private static final Pattern OPTION_PATTERN = Pattern.compile("^(☆[^\\n]+)*");
    private static final Pattern PASSIVE_PATTERN = Pattern.compile("^☆패시브-([^\\n]+)\\n(([^\\n]+\\n)+)");
    private static final Pattern INGREDIENTS_PATTERN = Pattern.compile("^조합법:\\n(([^\\n]+\\n)+)");

    public MrpgEquipmentDto parse(String input) {
        MrpgEquipmentDto dto = new MrpgEquipmentDto();
        Matcher nameMatcher = NAME_PATTERN.matcher(input);
        if (nameMatcher.find()) {
            dto.setName(nameMatcher.group(1));
            dto.setType(nameMatcher.group(2));
        }

        Matcher optionMatcher = OPTION_PATTERN.matcher(input);
        if (optionMatcher.find()) {
            String option = optionMatcher.group().replace("\n", " ");
            dto.setOption(getFormattedOption(option));
        }

        Matcher passiveMatcher = PASSIVE_PATTERN.matcher(input);
        if (passiveMatcher.find()) {
            dto.setPassive(passiveMatcher.group(1) + ":" + passiveMatcher.group(2).replace("\n", ""));
        }

        Matcher ingredientsMatcher = INGREDIENTS_PATTERN.matcher(input);
        if (ingredientsMatcher.find()) {
            dto.setIngredients(getFormattedIngredients(ingredientsMatcher.group(1)));
        }

        // 아래는 직접 구현해야할 부분이므로 예시로 작성하지 않았습니다.
        // dropMonster, grade, level, active, dedicatedOption 값을 설정해주세요.

        return dto;
    }

    private String getFormattedOption(String option) {
        String[] lines = option.split("☆");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] tokens = line.split("\\s+");
            String key = tokens[0].replace(":", "");
            String value = tokens[1].replace("+", "");
            sb.append(key).append(":").append(value).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String getFormattedIngredients(String ingredients) {
        String[] lines = ingredients.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] tokens = line.split("\\*");
            String name = tokens[0].trim();
            int count = Integer.parseInt(tokens[1].trim());
            sb.append(name).append(":").append(count).append("|");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}


