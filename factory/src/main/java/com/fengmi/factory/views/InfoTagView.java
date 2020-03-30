package com.fengmi.factory.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_utils.DisplayUtils;

public class InfoTagView extends View {
    private float mTagNameSize;
    private float mTagValueSize;
    private int mTagNameColor;
    private int mTagValueColor;
    private int mTagBackgroundColor = Color.LTGRAY;

    private String mTagName;
    private String mTagValue;

    private int mWidth;
    private int mHeight;

    private Paint mTXPaint;
    private Paint mBGPaint;

    private int mMargin = 3;
    private int mTXMargin = 3;

    private float mConner = 8f;


    public InfoTagView(Context context) {
        super(context);
        initTag(context, null, null);
    }

    public InfoTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTag(context, attrs, null);
    }

    public InfoTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTag(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureW(widthMeasureSpec), measureH(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int coreWidth = mWidth - (2 * mMargin);
        int coreHeight = mHeight - (2 * mMargin);
        float tagNameWidth = coreWidth * 0.39f;
        float tagValueWidth = coreWidth * 0.6f;
        float tagSplitWidth = coreWidth * 0.01f;

        mBGPaint.setColor(mTagBackgroundColor);
        canvas.drawRoundRect(
                mMargin, mMargin,
                (mMargin + tagNameWidth),
                (mMargin + coreHeight),
                mConner, mConner,
                mBGPaint);

        canvas.drawRoundRect(
                (mMargin + tagNameWidth + tagSplitWidth),
                mMargin,
                (mWidth - mMargin),
                (mMargin + coreHeight),
                mConner, mConner,
                mBGPaint
        );

        mTXPaint.setTextSize(mTagNameSize);

        Rect temp = new Rect();
        mTXPaint.getTextBounds(mTagName, 0, mTagName.length(), temp);
        int tagNameW = temp.width();
        int tagNameH = temp.height();

        mTXPaint.setColor(mTagNameColor);
        float tagNameX = mMargin + (Math.abs(tagNameWidth - tagNameW) / 2);
        float tagNameY = (mHeight / 2f) + (tagNameH / 2f);
//        Log.d(TAG, "Text x=$tagNameX, y=$tagNameY")
        canvas.drawText(
                mTagName,
                tagNameX,
                tagNameY,
                mTXPaint
        );
        //draw Tag Value Text
        mTXPaint.setTextSize(mTagValueSize);
        mTXPaint.getTextBounds(mTagValue, 0, mTagValue.length(), temp);
        tagNameW = temp.width();
        tagNameH = temp.height();
        tagNameX = mMargin + tagNameWidth + tagSplitWidth + (Math.abs(tagValueWidth - tagNameW) / 2);
        tagNameY = (mHeight / 2f) + (tagNameH / 2f);
//        Log.d(TAG, "Text x=$tagNameX, y=$tagNameY")
        canvas.drawText(
                mTagValue,
                tagNameX,
                tagNameY,
                mTXPaint
        );
    }


    public void setTagName(String name) {
        if (name != null) {
            mTagName = name;
        } else {
            mTagName = "null";
        }
    }

    public void setTagValue(String value) {
        if (value != null) {
            mTagValue = value;
        } else {
            mTagValue = "null";
        }
    }

    private int measureH(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        float defaultHeight;
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                defaultHeight = heightSize;
                break;
            default:
                defaultHeight = DisplayUtils.sp2px(getContext(), 25f);
                break;
        }
        return (int) defaultHeight;
    }

    private int measureW(int wightMeasureSpec) {
        int wightMode = MeasureSpec.getMode(wightMeasureSpec);
        int wightSize = MeasureSpec.getSize(wightMeasureSpec);
        float defaultWight;
        switch (wightMode) {
            case MeasureSpec.EXACTLY:
                defaultWight = wightSize;
                break;
            default:
                defaultWight = DisplayUtils.sp2px(getContext(), 150);
                break;
        }
        return (int) defaultWight;
    }

    private void initTag(Context context, AttributeSet attrs, Integer defStyleAttr) {
        mBGPaint = new Paint();
        mTXPaint = new Paint();
        mTXPaint.setAntiAlias(true);
        if (attrs != null) {
            TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.InfoTagView);
            mTagName = typeArray.getString(R.styleable.InfoTagView_info_tag_name);
            mTagNameSize = typeArray.getDimension(
                    R.styleable.InfoTagView_info_tag_name_size,
                    DisplayUtils.sp2px(context, 11f)
            );
            mTagNameColor = typeArray.getColor(R.styleable.InfoTagView_info_tag_name_color, Color.BLACK);
            mTagValue = typeArray.getString(R.styleable.InfoTagView_info_tag_value);
            mTagValueColor = typeArray.getColor(R.styleable.InfoTagView_info_tag_value_color, Color.BLACK);
            mTagValueSize = typeArray.getDimension(
                    R.styleable.InfoTagView_info_tag_value_size,
                    DisplayUtils.sp2px(context, 11f)
            );
            mConner = typeArray.getFloat(R.styleable.InfoTagView_info_tag_bg_conner, 3f);
            typeArray.recycle();
        } else {
            mTagNameSize = 11f;
            mTagValueSize = 11f;
            mTagNameColor = Color.BLACK;
            mTagValueColor = Color.BLACK;
            mConner = 3f;
        }

        if (mTagName == null) {
            mTagName = "";
        }
        if (mTagValue == null) {
            mTagValue = "";
        }
    }

}
