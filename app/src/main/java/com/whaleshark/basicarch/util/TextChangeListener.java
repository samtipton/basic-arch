package com.whaleshark.basicarch.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author stipton
 */

public abstract class TextChangeListener implements TextWatcher {
    @Override
    public abstract void afterTextChanged(Editable s);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
}
