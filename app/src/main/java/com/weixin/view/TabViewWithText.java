package com.weixin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.weixin.activity.R;

/**
 * Created by yh on 2016/6/8.
 */
public class TabViewWithText extends View {
    private String mTitle;
    private Bitmap mIconBitmap;
    private float mTextSize;
    private int mColor;

    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private Rect mTextBound;
    private Paint mTextPaint;
    private Rect mIconRect;

    private float mAlpha;


    public TabViewWithText(Context context) {
        this(context, null);
    }

    public TabViewWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabViewWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabViewWithText);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TabViewWithText_text:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.TabViewWithText_myicon:
                    BitmapDrawable drable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drable.getBitmap();
                    break;
                case R.styleable.TabViewWithText_text_size:
                    mTextSize = a.getDimension(attr,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TabViewWithText_mycolor:
                    mColor = a.getColor(attr, Color.RED);
                    break;

            }
        }
        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);

        //测量字的范围
        mTextPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量图标的宽度
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - iconWidth / 2 - mTextBound.height() / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int) Math.ceil(255*mAlpha);

        setupTargetBitmap(alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        drawSourceText(canvas, alpha);

        drawTargetText(canvas, alpha);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mTitle, x, y, mTextPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255-alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mTitle, x, y, mTextPaint);
    }

    public void setupTargetBitmap(int alpha) {
        //创建一个画板
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        //创建一只画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(alpha);
        mPaint.setDither(true);
        mPaint.setColor(mColor);
        mCanvas.drawRect(mIconRect, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(alpha);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
