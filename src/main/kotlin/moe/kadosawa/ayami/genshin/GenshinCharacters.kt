package moe.kadosawa.ayami.genshin

/**
 * Enumeration of playable Genshin Impact
 * characters as of version 2.4
 */
enum class GenshinCharacters(val fullName: String) {
    ALBEDO("Albedo"),
    ALOY("Aloy"),
    ARATAKI_ITTO("Arataki Itto"),
    BARBARA("Barbara"),
    BEIDOU("Beidou"),
    BENNETT("Bennett"),
    CHONGYUN("Chongyun"),
    DILUC("Diluc"),
    DIONA("Diona"),
    EULA("Eula"),
    FISCHL("Fischl"),
    GANYU("Ganyu"),
    GOROU("Gorou"),
    HU_TAO("Hu Tao"),
    JEAN("Jean"),
    KAEDEHARA_KAZUHA("Kaedehara Kazuha"),
    KAEYA("Kaeya"),
    KAMISATO_AYAKA("Kamisato Ayaka"),
    KEQING("Keqing"),
    KLEE("Klee"),
    KUJOU_SARA("Kujou Sara"),
    LISA("Lisa"),
    MONA("Mona"),
    NINGGUANG("Ningguang"),
    NOELLE("Noelle"),
    QIQI("Qiqi"),
    RAIDEN_SHOGUN("Raiden Shogun"),
    RAZOR("Razor"),
    ROSARIA("Rosaria"),
    SANGONOMIYA_KOKOMI("Sangonomiya Kokomi"),
    SAYU("Sayu"),
    SHENHE("Shenhe"),
    SUCROSE("Sucrose"),
    TARTAGLIA("Tartaglia"),
    THOMA("Thoma"),
    VENTI("Venti"),
    XIANGLING("Xiangling"),
    XIAO("Xiao"),
    XINGQIU("Xingqiu"),
    XINYAN("Xinyan"),
    YANFEI("Yanfei"),
    YOIMIYA("Yoimiya"),
    YUN_JIN("Yun Jin"),
    ZHONGLI("Zhongli");

    companion object {
        fun fromName(s: String) = valueOf(s.replace(" ", "_").uppercase())
    }
}