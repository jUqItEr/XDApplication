package com.dita.xd.view.theme;

import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.MaterialColors;

public class XdMaterialTheme extends MaterialLiteTheme {
    @Override
    protected void installColor() {
        super.installColor();
        this.backgroundPrimary = MaterialColors.WHITE;
    }

    @Override
    public String getName() {
        return "XdMaterialTheme";
    }
}