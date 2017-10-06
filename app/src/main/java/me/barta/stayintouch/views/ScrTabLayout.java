package me.barta.stayintouch.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.barta.stayintouch.R;

/**
 * TabLayout implementation with custom tab layout
 */

public class ScrTabLayout extends TabLayout {
    public ScrTabLayout(Context context) {
        super(context);
    }

    public ScrTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        TextView tabViewText = (TextView) View.inflate(getContext(), R.layout.scr_tab_textview, null);
        tabViewText.setText(tab.getText());

        tab.setCustomView(tabViewText);

        super.addTab(tab, setSelected);
    }

}
