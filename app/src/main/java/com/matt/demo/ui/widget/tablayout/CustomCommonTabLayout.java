package com.matt.demo.ui.widget.tablayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 创建日期 : 2019/4/2
 * 描 述 :    结合FragmentAttachManager用于Fragment的attach detach
 **/
public class CustomCommonTabLayout extends CommonTabLayout {

    private FragmentAttachManager fragmentAttachManager;

    public CustomCommonTabLayout(Context context) {
        super(context);
    }

    public CustomCommonTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 默认初始化第一个页面
     * 注意:这里是继承CommonTabLayout修改使用的attach detach 系统的使用hide show首次会全部初始化
     */
    @Override
    public void setTabData(ArrayList<CustomTabEntity> tabEntitys, FragmentActivity fa, int containerViewId, ArrayList<Fragment> fragments) {
        setTabData(tabEntitys, fa, containerViewId, fragments, 0);
    }

    /**
     * @param initTab 初始化第几个页面
     */
    public void setTabData(ArrayList<CustomTabEntity> tabEntitys, FragmentActivity fa, int containerViewId, ArrayList<Fragment> fragments, int initTab) {
        fragmentAttachManager = new FragmentAttachManager(fa.getSupportFragmentManager(), containerViewId, fragments);
        setTabData(tabEntitys);
        if (initTab < fragments.size()) {
            setCurrentTab(initTab);
        }
    }

    @Override
    public void setCurrentTab(int currentTab) {
        super.setCurrentTab(currentTab);
        if (fragmentAttachManager != null) {
            fragmentAttachManager.setFragments(currentTab);
        }
    }
}
