package ir.DEFINEit.tools.country;

import java.util.ArrayList;
import java.util.List;

import ir.DEFINEit.models.LangModel;

public class Country {

    private static List<LangModel> langModels;

    public static List<LangModel> generateLanguage() {
        if (langModels == null) {
            langModels = new ArrayList<>();
            langModels.add(new LangModel("Persian", "fa-IR", "ic_iran.png"));
            langModels.add(new LangModel("English", "en-GB", "ic_united_kingdom.png"));
            langModels.add(new LangModel("French", "fr-FR", "ic_france.png"));
            langModels.add(new LangModel("German", "de-DE", "ic_germany.png"));
            langModels.add(new LangModel("Italian", "it-IT", "ic_italy.png"));
            langModels.add(new LangModel("Spanish", "es-ES", "ic_spain.png"));
            langModels.add(new LangModel("Dutch", "nl-NL", "ic_netherlands.png"));
            langModels.add(new LangModel("Arabic", "ar-SA", "ic_saudi.png"));
            langModels.add(new LangModel("Turkish", "tr-TR", "ic_turkey.png"));
            langModels.add(new LangModel("Chinese", "zh-CN", "ic_china.png"));
            langModels.add(new LangModel("Russian", "ru-RU", "ic_russia.png"));
            langModels.add(new LangModel("Korean", "ko-KR", "ic_south_korea.png"));
            langModels.add(new LangModel("Armenian", "hy-AM", "ic_armenia.png"));
            langModels.add(new LangModel("Australian", "en-AU", "ic_australia.png"));
            langModels.add(new LangModel("Azerbaijani", "az-AZ", "ic_azerbaijan.png"));
            langModels.add(new LangModel("Bengali", "bn-BD", "ic_bangladesh.png"));
            langModels.add(new LangModel("Bosnian", "bs-BA", "ic_bosnia.png"));
            langModels.add(new LangModel("Bulgarian", "bg-BG", "ic_bulgaria.png"));
            langModels.add(new LangModel("Khmer", "km-KH", "ic_cambodia.png"));
            langModels.add(new LangModel("Canadian", "en-CA", "ic_canada.png"));
            langModels.add(new LangModel("Croatian", "hr-HR", "ic_croatia.png"));
            langModels.add(new LangModel("Czech", "cs-CZ", "ic_czech.png"));
            langModels.add(new LangModel("Danish", "da-DK", "ic_denmark.png"));
            langModels.add(new LangModel("Estonian", "et-EE", "ic_estonia.png"));
            langModels.add(new LangModel("Finnish", "fi-FI", "ic_finland.png"));
            langModels.add(new LangModel("Georgian", "ka-GE", "ic_georgia.png"));
            langModels.add(new LangModel("Greek", "el-GR", "ic_greece.png"));
            langModels.add(new LangModel("Hungarian", "hu-HU", "ic_hungary.png"));
            langModels.add(new LangModel("Hindi", "hi-IN", "ic_india.png"));
            langModels.add(new LangModel("Indonesian", "id-ID", "ic_indonesia.png"));
            langModels.add(new LangModel("Irish", "en-IE", "ic_ireland.png"));
            langModels.add(new LangModel("Hebrew", "he-IL", "ic_israel.png"));
            langModels.add(new LangModel("Japanese", "ja-JP", "ic_japan.png"));
            langModels.add(new LangModel("Malay", "ms-MY", "ic_malaysia.png"));
            langModels.add(new LangModel("Mexican Spanish", "es-MX", "ic_mexico.png"));
            langModels.add(new LangModel("Nepali", "ne-NP", "ic_nepal.png"));
            langModels.add(new LangModel("Norwegian", "no-NO", "ic_norway.png"));
            langModels.add(new LangModel("Pakistani English", "en-PK", "ic_pakistan.png"));
            langModels.add(new LangModel("Polish", "pl-PL", "ic_poland.png"));
            langModels.add(new LangModel("Portuguese", "pt-PT", "ic_portugal.png"));
            langModels.add(new LangModel("Romanian", "ro-RO", "ic_romania.png"));
            langModels.add(new LangModel("Serbian", "sr-RS", "ic_serbia.png"));
            langModels.add(new LangModel("Slovak", "sk-SK", "ic_slovakia.png"));
            langModels.add(new LangModel("Swedish", "sv-SE", "ic_sweden.png"));
            langModels.add(new LangModel("Taiwanese Mandarin", "zh-TW", "ic_taiwan.png"));
            langModels.add(new LangModel("Thai", "th-TH", "ic_thailand.png"));
            langModels.add(new LangModel("Ukrainian", "uk-UA", "ic_ukraine.png"));
            langModels.add(new LangModel("Uzbek", "uz-UZ", "ic_uzbekistan.png"));
            langModels.add(new LangModel("Vietnamese", "vi-VN", "ic_vietnam.png"));
            langModels.add(new LangModel("Welsh", "cy-GB", "ic_wales.png"));
        }
        return langModels;
    }

}
