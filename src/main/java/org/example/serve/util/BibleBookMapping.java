package org.example.serve.util;

import java.util.HashMap;
import java.util.Map;

public class BibleBookMapping {

    private static final Map<String, String> tagalogBookNames = new HashMap<>();
    private static final Map<String, String> abbrevToFullName = new HashMap<>();

    static {
        // Mapping from full English book names to Tagalog names
        tagalogBookNames.put("Genesis", "Genesis");
        tagalogBookNames.put("Exodus", "Exodo");
        tagalogBookNames.put("Leviticus", "Levitico");
        tagalogBookNames.put("Numbers", "Bilang");
        tagalogBookNames.put("Deuteronomy", "Deuteronomio");
        tagalogBookNames.put("Joshua", "Josue");
        tagalogBookNames.put("Judges", "Mga Hukom");
        tagalogBookNames.put("Ruth", "Ruth");
        tagalogBookNames.put("1 Samuel", "1 Samuel");
        tagalogBookNames.put("2 Samuel", "2 Samuel");
        tagalogBookNames.put("1 Kings", "1 Mga Hari");
        tagalogBookNames.put("2 Kings", "2 Mga Hari");
        tagalogBookNames.put("1 Chronicles", "1 Mga Cronica");
        tagalogBookNames.put("2 Chronicles", "2 Mga Cronica");
        tagalogBookNames.put("Ezra", "Ezra");
        tagalogBookNames.put("Nehemiah", "Nehemias");
        tagalogBookNames.put("Esther", "Esther");
        tagalogBookNames.put("Job", "Job");
        tagalogBookNames.put("Psalms", "Mga Awit");
        tagalogBookNames.put("Proverbs", "Mga Kawikaan");
        tagalogBookNames.put("Ecclesiastes", "Eclesiastes");
        tagalogBookNames.put("Song of Solomon", "Awit ni Solomon");
        tagalogBookNames.put("Isaiah", "Isaias");
        tagalogBookNames.put("Jeremiah", "Jeremias");
        tagalogBookNames.put("Lamentations", "Panaghoy");
        tagalogBookNames.put("Ezekiel", "Ezekiel");
        tagalogBookNames.put("Daniel", "Daniel");
        tagalogBookNames.put("Hosea", "Oseas");
        tagalogBookNames.put("Joel", "Joel");
        tagalogBookNames.put("Amos", "Amos");
        tagalogBookNames.put("Obadiah", "Obadias");
        tagalogBookNames.put("Jonah", "Jonas");
        tagalogBookNames.put("Micah", "Mikas");
        tagalogBookNames.put("Nahum", "Nahum");
        tagalogBookNames.put("Habakkuk", "Habakuk");
        tagalogBookNames.put("Zephaniah", "Zefanias");
        tagalogBookNames.put("Haggai", "Hagai");
        tagalogBookNames.put("Zechariah", "Zacarias");
        tagalogBookNames.put("Malachi", "Malakias");
        tagalogBookNames.put("Matthew", "Mateo");
        tagalogBookNames.put("Mark", "Marcos");
        tagalogBookNames.put("Luke", "Lucas");
        tagalogBookNames.put("John", "Juan");
        tagalogBookNames.put("Acts", "Mga Gawa");
        tagalogBookNames.put("Romans", "Mga Taga-Roma");
        tagalogBookNames.put("1 Corinthians", "1 Mga Taga-Corinto");
        tagalogBookNames.put("2 Corinthians", "2 Mga Taga-Corinto");
        tagalogBookNames.put("Galatians", "Mga Taga-Galacia");
        tagalogBookNames.put("Ephesians", "Mga Taga-Efeso");
        tagalogBookNames.put("Philippians", "Mga Taga-Filipos");
        tagalogBookNames.put("Colossians", "Mga Taga-Colosas");
        tagalogBookNames.put("1 Thessalonians", "1 Mga Taga-Tesalonica");
        tagalogBookNames.put("2 Thessalonians", "2 Mga Taga-Tesalonica");
        tagalogBookNames.put("1 Timothy", "1 Timoteo");
        tagalogBookNames.put("2 Timothy", "2 Timoteo");
        tagalogBookNames.put("Titus", "Tito");
        tagalogBookNames.put("Philemon", "Filemon");
        tagalogBookNames.put("Hebrews", "Mga Hebreo");
        tagalogBookNames.put("James", "Santiago");
        tagalogBookNames.put("1 Peter", "1 Pedro");
        tagalogBookNames.put("2 Peter", "2 Pedro");
        tagalogBookNames.put("1 John", "1 Juan");
        tagalogBookNames.put("2 John", "2 Juan");
        tagalogBookNames.put("3 John", "3 Juan");
        tagalogBookNames.put("Jude", "Judas");
        tagalogBookNames.put("Revelation", "Pahayag");

        // Mapping from abbreviations to full English book names
        abbrevToFullName.put("gn", "Genesis");
        abbrevToFullName.put("ex", "Exodus");
        abbrevToFullName.put("lv", "Leviticus");
        abbrevToFullName.put("nu", "Numbers");
        abbrevToFullName.put("dt", "Deuteronomy");
        abbrevToFullName.put("js", "Joshua");
        abbrevToFullName.put("jdg", "Judges");
        abbrevToFullName.put("ru", "Ruth");
        abbrevToFullName.put("1sm", "1 Samuel");
        abbrevToFullName.put("2sm", "2 Samuel");
        abbrevToFullName.put("1ki", "1 Kings");
        abbrevToFullName.put("2ki", "2 Kings");
        abbrevToFullName.put("1ch", "1 Chronicles");
        abbrevToFullName.put("2ch", "2 Chronicles");
        abbrevToFullName.put("ezr", "Ezra");
        abbrevToFullName.put("neh", "Nehemiah");
        abbrevToFullName.put("est", "Esther");
        abbrevToFullName.put("job", "Job");
        abbrevToFullName.put("ps", "Psalms");
        abbrevToFullName.put("pr", "Proverbs");
        abbrevToFullName.put("eccl", "Ecclesiastes");
        abbrevToFullName.put("so", "Song of Solomon");
        abbrevToFullName.put("isa", "Isaiah");
        abbrevToFullName.put("jer", "Jeremiah");
        abbrevToFullName.put("lam", "Lamentations");
        abbrevToFullName.put("ezk", "Ezekiel");
        abbrevToFullName.put("dan", "Daniel");
        abbrevToFullName.put("hos", "Hosea");
        abbrevToFullName.put("jol", "Joel");
        abbrevToFullName.put("amo", "Amos");
        abbrevToFullName.put("obad", "Obadiah");
        abbrevToFullName.put("jon", "Jonah");
        abbrevToFullName.put("mic", "Micah");
        abbrevToFullName.put("nah", "Nahum");
        abbrevToFullName.put("hab", "Habakkuk");
        abbrevToFullName.put("zep", "Zephaniah");
        abbrevToFullName.put("hag", "Haggai");
        abbrevToFullName.put("zec", "Zechariah");
        abbrevToFullName.put("mal", "Malachi");
        abbrevToFullName.put("mt", "Matthew");
        abbrevToFullName.put("mk", "Mark");
        abbrevToFullName.put("lk", "Luke");
        abbrevToFullName.put("jn", "John");
        abbrevToFullName.put("acts", "Acts");
        abbrevToFullName.put("rom", "Romans");
        abbrevToFullName.put("1co", "1 Corinthians");
        abbrevToFullName.put("2co", "2 Corinthians");
        abbrevToFullName.put("gal", "Galatians");
        abbrevToFullName.put("eph", "Ephesians");
        abbrevToFullName.put("php", "Philippians");
        abbrevToFullName.put("col", "Colossians");
        abbrevToFullName.put("1th", "1 Thessalonians");
        abbrevToFullName.put("2th", "2 Thessalonians");
        abbrevToFullName.put("1tm", "1 Timothy");
        abbrevToFullName.put("2tm", "2 Timothy");
        abbrevToFullName.put("tit", "Titus");
        abbrevToFullName.put("phm", "Philemon");
        abbrevToFullName.put("heb", "Hebrews");
        abbrevToFullName.put("jas", "James");
        abbrevToFullName.put("1pe", "1 Peter");
        abbrevToFullName.put("2pe", "2 Peter");
        abbrevToFullName.put("1jn", "1 John");
        abbrevToFullName.put("2jn", "2 John");
        abbrevToFullName.put("3jn", "3 John");
        abbrevToFullName.put("jud", "Jude");
        abbrevToFullName.put("rev", "Revelation");
    }

    public static String getTagalogBookName(String abbrev, String version) {
        String fullName = abbrevToFullName.getOrDefault(abbrev.toLowerCase(), abbrev);
        if ("MBBTAG".equalsIgnoreCase(version) || "FSV".equalsIgnoreCase(version)) {
            return tagalogBookNames.getOrDefault(fullName, fullName);
        }
        return fullName;
    }

    public static String getBookName(String abbrev) {
        return abbrevToFullName.getOrDefault(abbrev.toLowerCase(), abbrev);
    }
}
