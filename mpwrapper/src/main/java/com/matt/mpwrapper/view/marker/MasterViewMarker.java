package com.matt.mpwrapper.view.marker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.ColorInt;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.matt.mpwrapper.R;
import com.matt.mpwrapper.utils.ScreenUtils;
import com.matt.mpwrapper.utils.TimeUtils;
import com.matt.mpwrapper.view.MasterView;


/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/29 14:12
 * 描 述 ：主图的markerview，何为markerview:就是长按高亮线上显示的文字，
 * 默认情况下显示在x、y轴高亮线交互点处右下角显示。
 * 现在自定义其在x轴内侧显示x的数值，在y轴外侧显示y轴数值。
 * ============================================================
 */
public class MasterViewMarker extends MarkerView {
    public static final String TAG = "BaseKViewMarker";
    private Context mContext;

    //绘制背景
    private int mMpTxtColor;
    private Paint mMpTxtPaint;
    private int mMpBgColor;
    private Paint mMpBgPaint;
    //背景的圆角
    private int mRadius = ScreenUtils.dip2px(2);

    private Entry mEntry;
    private MasterView mMasterView;

    public MasterViewMarker(MasterView masterView, Context context) {
        super(context, R.layout.markerview_k_view_shell);
        this.mMasterView = masterView;
        mContext = getContext();
        initAttrs();
        initPaint();
    }

    private void initPaint() {
        mMpTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMpTxtPaint.setTextSize(ScreenUtils.dip2px(8));
        mMpTxtPaint.setColor(mMpTxtColor);

        mMpBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMpBgPaint.setColor(mMpBgColor);
    }

    private void initAttrs() {
        mMpTxtColor = colorResId(R.color.mp_marker_txtColor);
        mMpBgColor = colorResId(R.color.mp_marker_bgColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mEntry = e;
        super.refreshContent(e, highlight);
    }

    /**
     * posX、posY就是十字高亮的十字交叉点
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas, posX, posY);
        Log.d(TAG, "draw: " + posX + "," + posY);
        Chart chartView = getChartView();
        if (chartView == null) return;
        if (mEntry == null) return;
        drawXMarker(canvas, posX, posY);
        drawYMarker(canvas, posX, posY);

    }

    /**
     * 绘制y轴的markerview。
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    private void drawYMarker(Canvas canvas, float posX, float posY) {
        RectF contentRect = getMasterViewRectF();
        String txt = String.valueOf(mEntry.getY());
        int txtHeight = Utils.calcTextHeight(mMpTxtPaint, txt);
        int txtWidth = Utils.calcTextWidth(mMpTxtPaint, txt);
        float borderWidth = ScreenUtils.dip2px(2);
        float xL = contentRect.right + borderWidth;
        float yL = posY - txtHeight / 2.0f;
        float xR = contentRect.right + txtWidth + borderWidth;
        float yR = posY + txtHeight / 2.0f;
        //左右边距、上下边距
        float paddingLR = ScreenUtils.dip2px(2);
        float paddingTB = ScreenUtils.dip2px(2);
        //这里看似复杂的加减，主要为文字显示居中，背景加一个padding值和圆角
        RectF rectF = new RectF(xL - paddingLR, yL, xR + paddingLR, yR + paddingTB + paddingTB);
        canvas.drawRoundRect(rectF, mRadius, mRadius, mMpBgPaint);
        float txtOffset = ScreenUtils.dip2px(1);
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint);
    }

    /**
     * 绘制x轴的markerview,注意x轴多了一个边界校验。
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    private void drawXMarker(Canvas canvas, float posX, float posY) {
        RectF contentRect = getMasterViewRectF();
        String txt = TimeUtils.millis2String((long) posX);
        int txtHeight = Utils.calcTextHeight(mMpTxtPaint, txt);
        int txtWidth = Utils.calcTextWidth(mMpTxtPaint, txt);
        //左右边距、上下边距
        float paddingLR = ScreenUtils.dip2px(2);
        float paddingTB = ScreenUtils.dip2px(2);

        /**
         * 确认x轴左右边界，并且限制在contentRect范围内。y轴不用处理，因为本来y轴的显示范围本来上下就有padding值。
         */
        if ((posX - txtWidth / 2.0f) <= contentRect.left) {
            posX = contentRect.left + txtWidth / 2.0f + paddingLR;
        } else if ((posX + txtWidth / 2.0f) >= contentRect.right) {
            posX = contentRect.right - txtWidth / 2.0f - paddingLR;
        } else {
            //正常范围内
        }
        float xL = posX - txtWidth / 2.0f;
        float yL = contentRect.top;
        float xR = posX + txtWidth / 2.0f;
        float yR = contentRect.top + txtHeight;


        //这里看似复杂的加减，主要为文字显示居中，背景加一个padding值和圆角
        RectF rectF = new RectF(xL - paddingLR, yL, xR + paddingLR, yR + paddingTB + paddingTB);
        canvas.drawRoundRect(rectF, mRadius, mRadius, mMpBgPaint);
        float txtOffset = ScreenUtils.dip2px(1);
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint);
    }

    /**
     * 获取主图绘制区域的范围
     *
     * @return
     */
    private RectF getMasterViewRectF() {
        ViewPortHandler viewPortHandler = mMasterView.getViewPortHandler();
        //就是这个图表绘制线的区域
        return viewPortHandler.getContentRect();
    }

    @ColorInt
    public int colorResId(int id) {
        return mContext.getResources().getColor(id);
    }
}
