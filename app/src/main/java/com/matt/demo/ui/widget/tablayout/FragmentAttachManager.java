package com.matt.demo.ui.widget.tablayout;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * 创建日期 : 2019/4/2
 * 描 述 :    结合MyCommonTabLayout使用
 **/
@SuppressWarnings("WeakerAccess")
public class FragmentAttachManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /**
     * Fragment切换数组
     */
    private List<Fragment> mFragments;
    /**
     * 当前选中的Tab
     */
    private int mCurrentTab;

    public FragmentAttachManager(FragmentManager fm, int containerViewId, List<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
    }

    /**
     * 界面切换控制
     */
    public void setFragments(int index) {
        changeFragment(mFragments.get(index), index);
        mCurrentTab = index;
    }

    public void changeFragment(Fragment fragment, int index) {
        Fragment fragmentById = mFragmentManager.findFragmentById(mContainerViewId);
        if (fragmentById != null) {
            detachFragment(fragmentById);
        }
        if (fragment == null) return;
        Fragment fragmentByTag = mFragmentManager.findFragmentByTag(fragment.getClass().getSimpleName() + index);
        if (fragmentByTag == null) {
            addFragment(fragment, mContainerViewId, index);
        } else {
            attachFragment(fragmentByTag);
        }
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }


    public void addFragment(Fragment fragment, @IdRes int containerViewId, int index) {
        if (fragment == null) return;
        mFragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragment.getClass().getSimpleName() + index)
                .commitAllowingStateLoss();
    }

    public void detachFragment(Fragment fragment) {
        if (fragment == null) return;
        mFragmentManager.beginTransaction()
                .detach(fragment)
                .commitAllowingStateLoss();
    }

    public void attachFragment(Fragment fragment) {
        if (fragment == null) return;
        mFragmentManager.beginTransaction()
                .attach(fragment)
                .commitAllowingStateLoss();
    }
}
