package me.fofola1.customCraftingRecipesPlugin.Utils;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
