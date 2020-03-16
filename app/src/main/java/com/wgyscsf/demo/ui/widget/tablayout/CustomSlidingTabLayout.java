package com.wgyscsf.demo.ui.widget.tablayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 创建日期 : 2019/4/2
 * 描 述 :    结合FragmentAttachManager用于Fragment的attach detach
 **/
// FIXME: 2019-07-16 待完善....
public class CustomSlidingTabLayout extends SlidingTabLayout {

    private FragmentAttachManager fragmentAttachManager;

    public CustomSlidingTabLayout(Context context) {
        super(context);
    }

    public CustomSlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments) {
        super.setViewPager(vp, titles, fa, fragments);
    }

    @Override
    public void setCurrentTab(int currentTab) {
        super.setCurrentTab(currentTab);
        if (fragmentAttachManager != null) {
            fragmentAttachManager.setFragments(currentTab);
        }
    }
}
