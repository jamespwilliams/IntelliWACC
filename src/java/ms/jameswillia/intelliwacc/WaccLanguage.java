package ms.jameswillia.intelliwacc;

import com.intellij.lang.Language;

public class WaccLanguage extends Language {
    public static final WaccLanguage INSTANCE = new WaccLanguage();

    private WaccLanguage() {
        super("Wacc");
    }
}
