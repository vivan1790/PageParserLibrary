package com.sample.parserlibrary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Styler {

    static final int TAG_ALL = 0;
    static final int TAG_BOLD = 1;
    static final int TAG_ITALIC = 2;
    static final int TAG_UNDERLINE = 3;
    static final int TAG_CODE = 4;
    static final int TAG_TABLE_HEAD = 5;
    static final int TAG_SUP = 6;
    static final int TAG_SUB = 7;
    private float dpToPixelFactorValue = 0;
    boolean boldTagOpen = false, italicTagOpen = false, timeTagOpen = false,
            codeTagOpen = false, preTagOpen = false, supTagOpen = false, subTagOpen = false;
    int anchorTagStart = -1, anchorTagEnd = -1;
    int boldTagStart = -1, boldTagEnd = -1;
    int italicTagStart = -1, italicTagEnd = -1;
    int underlineTagStart = -1, underlineTagEnd = -1;
    int codeTagStart = -1, codeTagEnd = -1;
    int supTagStart = -1, supTagEnd = -1;
    int subTagStart = -1, subTagEnd = -1;
    int tHeadTagStart = -1, tHeadTagEnd = -1;
    ArrayList<TagAttribute> anchorTagAttributes = null;

    Styler(float value) {
        this.dpToPixelFactorValue = value;
    }

    float getDpToPixelFactorValue() {
        return dpToPixelFactorValue;
    }

    SpannableStringBuilder appendFormattedCharacter(SpannableStringBuilder text,
                                                           char character) {
        text.append(character);
        return text;
    }

    void resetSpan(int type) {
        switch (type) {
            case TAG_ALL :
                anchorTagStart = -1;
                anchorTagEnd = -1;
                boldTagStart = -1;
                boldTagEnd = -1;
                italicTagStart = -1;
                italicTagEnd = -1;
                underlineTagStart = -1;
                underlineTagEnd = -1;
                codeTagStart = -1;
                codeTagEnd = -1;
                tHeadTagStart = -1;
                tHeadTagEnd = -1;
                supTagStart = -1;
                supTagEnd = -1;
                subTagStart = -1;
                subTagEnd = -1;
                break;
            case TAG_BOLD :
                boldTagStart = -1;
                boldTagEnd = -1;
                break;
            case TAG_ITALIC :
                italicTagStart = -1;
                italicTagEnd = -1;
                break;
            case TAG_UNDERLINE :
                underlineTagStart = -1;
                underlineTagEnd = -1;
                break;
            case TAG_CODE :
                codeTagStart = -1;
                codeTagEnd = -1;
                break;
            case TAG_TABLE_HEAD :
                tHeadTagStart = -1;
                tHeadTagEnd = -1;
                break;
            case TAG_SUP :
                supTagStart = -1;
                supTagEnd = -1;
                break;
            case TAG_SUB :
                subTagStart = -1;
                subTagEnd = -1;
            default :
                break;
        }
    }

    public SpannableStringBuilder applySpanOnText(int type, SpannableStringBuilder text) {
        switch (type) {
            case TAG_BOLD :
                if (boldTagStart >= 0 && boldTagEnd >= 0) {
                    text.setSpan(new StyleSpan(Typeface.BOLD),
                            boldTagStart, boldTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                boldTagStart = -1;
                boldTagEnd = -1;
                break;
            case TAG_ITALIC :
                if (italicTagStart >= 0 && italicTagEnd >= 0) {
                    text.setSpan(new StyleSpan(Typeface.ITALIC),
                            italicTagStart, italicTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                italicTagStart = -1;
                italicTagEnd = -1;
                break;
            case TAG_UNDERLINE :
                if (underlineTagStart >= 0 && underlineTagEnd >= 0) {
                    text.setSpan(new UnderlineSpan(),
                            underlineTagStart, underlineTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                underlineTagStart = -1;
                underlineTagEnd = -1;
                break;
            case TAG_CODE :
                if (codeTagStart >= 0 && codeTagEnd >= 0) {
                    text.setSpan(new ForegroundColorSpan(
                                    Color.parseColor("#FF6347")),
                            codeTagStart, codeTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new BackgroundColorSpan(
                                    Color.parseColor("#44D3D3D3")),
                            codeTagStart, codeTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    /*text.setSpan(new CodeTagSpan(
                                    Color.parseColor("#44D3D3D3"), Color.parseColor("#FF6347")),
                            codeTagStart, codeTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
                }
                codeTagStart = -1;
                codeTagEnd = -1;
                break;
            case TAG_TABLE_HEAD :
                if (tHeadTagStart >= 0 && tHeadTagEnd >= 0) {
                    text.setSpan(new StyleSpan(Typeface.BOLD),
                            tHeadTagStart, tHeadTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(1.1f),
                            tHeadTagStart, tHeadTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tHeadTagStart = -1;
                tHeadTagEnd = -1;
                break;
            case TAG_SUP :
                if (supTagStart >= 0 && supTagEnd >= 0) {
                    text.setSpan(new SuperscriptSpan(),
                            supTagStart, supTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0.6f),
                            supTagStart, supTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                supTagStart = -1;
                supTagEnd = -1;
                break;
            case TAG_SUB :
                if (subTagStart >= 0 && subTagEnd >= 0) {
                    text.setSpan(new SubscriptSpan(),
                            subTagStart, subTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0.6f),
                            subTagStart, subTagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                subTagStart = -1;
                subTagEnd = -1;
                break;
            default :
                break;
        }
        return text;
    }

    public SpannableStringBuilder putURL(SpannableStringBuilder text, Context context) {
        String url = "", target = "";
        int num = anchorTagAttributes.size();
        for (int i = 0; i < num; i++) {
            if (anchorTagAttributes.get(i).getAttributeName().equals("href")) {
                url = anchorTagAttributes.get(i).getAttributeValue();
            } else if (anchorTagAttributes.get(i).getAttributeName().equals("target")) {
                target = anchorTagAttributes.get(i).getAttributeValue();
            }
        }
        text.setSpan(new CustomURLSpan(url, context), anchorTagStart, anchorTagEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (target.equals("_blank")) {
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#66666666")),
                    anchorTagStart, anchorTagEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        anchorTagStart = -1;
        anchorTagEnd = -1;
        anchorTagAttributes = null;
        return text;
    }

    public class BaseTextView extends android.support.v7.widget.AppCompatTextView {
        public BaseTextView(Context context) {
            super(context);
            this.setTextIsSelectable(true);
        }

        public void resetTextView() {
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorNormal);
        }
    }

    public class ContainerTextView extends BaseTextView {
        ArrayList<TagAttribute> attributes;
        String parentTagName;
        Context context;

        public ContainerTextView(Context context, String parentTag,
                                 ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.parentTagName = parentTag;
            this.attributes = attributes;
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorNormal);
            this.setTypeface(Utils.mTutorialTypeface);
            this.setLineSpacing(0, 1.2f);
            this.setMovementMethod(LinkMovementMethod.getInstance());
        }

        @Override
        public void resetTextView() {
            super.resetTextView();
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorNormal);
        }
    }

    public class QuestionParentDivTagView extends LinearLayout {
        Context context;
        int questionNumber;
        public QuestionParentDivTagView(Context context, int quesNum) {
            super(context);
            this.context = context;
            this.questionNumber = quesNum;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setPadding((int) (6 * dpToPixelFactorValue), (int) (12 * dpToPixelFactorValue),
                    (int) (6 * dpToPixelFactorValue), (int) (12 * dpToPixelFactorValue));
        }

        public int getQuestionNumber() {
            return questionNumber;
        }
    }

    public class QuestionContainerTextView extends BaseTextView {
        Context context;
        public QuestionContainerTextView(Context context) {
            super(context);
            this.context = context;
            this.setTextSize(Utils.TEXT_SIZE_TEST_QUESTION * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorHeader);
            this.setTypeface(Utils.mTutorialTypeface, Typeface.BOLD);
            this.setLineSpacing(0, 1.2f);
            this.setMovementMethod(LinkMovementMethod.getInstance());
            this.setPadding(0, (int) (6 * dpToPixelFactorValue),
                    0, (int) (6 * dpToPixelFactorValue));
        }

        @Override
        public void resetTextView() {
            super.resetTextView();
            this.setTextSize(Utils.TEXT_SIZE_TEST_QUESTION * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorHeader);
        }
    }

    public class QuestionRadioGroupView extends RadioGroup {
        Context context;
        public QuestionRadioGroupView(Context context) {
            super(context);
            this.context = context;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setPadding(0, (int) (4 * dpToPixelFactorValue),
                    0, (int) (4 * dpToPixelFactorValue));
        }
    }

    public class QuestionOptionRadioButton extends android.support.v7.widget.AppCompatRadioButton {
        Context context;
        public QuestionOptionRadioButton(Context context) {
            super(context);
            this.context = context;
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorNormal);
            this.setTypeface(Utils.mTutorialTypeface);
            this.setLineSpacing(0, 1.2f);
            this.setMovementMethod(LinkMovementMethod.getInstance());
            this.setPadding(0, (int) (6 * dpToPixelFactorValue),
                    0, (int) (6 * dpToPixelFactorValue));
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, //unchecked
                            new int[]{ android.R.attr.state_checked}  //checked
                    },
                    new int[] {
                            Color.parseColor("#6A67CE"), //unchecked
                            Color.parseColor("#6A67CE") //checked
                    }
            );
            this.setButtonTintList(colorStateList);
        }
    }

    public class AnswerTextView extends BaseTextView {
        Context context;

        public AnswerTextView(Context context) {
            super(context);
            this.context = context;
            this.setBackgroundResource(R.drawable.bg_test_answer);
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Color.parseColor("#444444"));
            this.setTypeface(Utils.mTutorialTypeface);
            this.setLineSpacing(0, 1.2f);
            this.setPadding((int) (12 * dpToPixelFactorValue), (int) (4 * dpToPixelFactorValue),
                    (int) (12 * dpToPixelFactorValue), (int) (4 * dpToPixelFactorValue));
            this.setMovementMethod(LinkMovementMethod.getInstance());
        }

        @Override
        public void resetTextView() {
            super.resetTextView();
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            this.setTextColor(Preferences.mTextColorNormal);
        }
    }

    public class ArticleTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public ArticleTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
        }
    }

    public class BodyTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public BodyTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setPadding(0, (int) (10 * dpToPixelFactorValue)
                    , 0, (int) (10 * dpToPixelFactorValue));
        }
    }

    public class DivTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public DivTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            String classAttributeValue = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("class")) {
                    classAttributeValue = attributes.get(i).getAttributeValue();
                }
            }
            switch (classAttributeValue) {
                case "note-buttons":
                    this.setVisibility(View.GONE);
                    break;
                case "tags":
                    this.setVisibility(View.GONE);
                    break;
                case "post-meta":
                    this.setOrientation(LinearLayout.HORIZONTAL);
                    this.setAlpha(0.4f);
                    break;
                case "meta-item":
                    this.setPadding(0, 0, (int) (15 * dpToPixelFactorValue), 0);
                    break;
                default:
            }
        }
    }

    public class FigureTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public FigureTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setPadding(0, (int) (10 * dpToPixelFactorValue)
                    , 0, (int) (10 * dpToPixelFactorValue));
        }
    }

    public class HeaderTagView extends BaseTextView {
        ArrayList<TagAttribute> attributes;
        String tagName;
        Context context;

        public HeaderTagView(Context context, String tagName, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.tagName = tagName;
            this.attributes = attributes;
            this.setGravity(Gravity.START);
            this.setTypeface(Utils.mTutorialTypeface);
            if (Preferences.mNightModeDisplay) {
                this.setBackgroundResource(R.drawable.bg_header_tag_night);
            } else {
                this.setBackgroundResource(R.drawable.bg_header_tag_normal);
            }
            this.setTextColor(Preferences.mTextColorHeader);
            this.setPadding(0, (int) (20 * dpToPixelFactorValue)
                    , 0, (int) (4 * dpToPixelFactorValue));
            switch (tagName.charAt(1)) {
                case '1':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H1 * Preferences.mTextScaleFactor);
                    break;
                case '2':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H2 * Preferences.mTextScaleFactor);
                    break;
                case '3':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H3 * Preferences.mTextScaleFactor);
                    break;
                case '4':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H4 * Preferences.mTextScaleFactor);
                    break;
                case '5':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H5 * Preferences.mTextScaleFactor);
                    break;
                case '6':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H6 * Preferences.mTextScaleFactor);
                    break;
                default:
                    this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
                    break;
            }
        }

        @Override
        public void resetTextView() {
            super.resetTextView();
            if (Preferences.mNightModeDisplay) {
                this.setBackgroundResource(R.drawable.bg_header_tag_night);
            } else {
                this.setBackgroundResource(R.drawable.bg_header_tag_normal);
            }
            this.setTextColor(Preferences.mTextColorHeader);
            switch (tagName.charAt(1)) {
                case '1':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H1 * Preferences.mTextScaleFactor);
                    break;
                case '2':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H2 * Preferences.mTextScaleFactor);
                    break;
                case '3':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H3 * Preferences.mTextScaleFactor);
                    break;
                case '4':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H4 * Preferences.mTextScaleFactor);
                    break;
                case '5':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H5 * Preferences.mTextScaleFactor);
                    break;
                case '6':
                    this.setTextSize(Utils.TEXT_SIZE_TAG_H6 * Preferences.mTextScaleFactor);
                    break;
                default:
                    this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
                    break;
            }
        }
    }

    public class IFrameTagView extends WebView {
        ArrayList<TagAttribute> attributes;
        Context context;

        @SuppressLint("SetJavaScriptEnabled")
        public IFrameTagView(Context context, ArrayList<TagAttribute> attributes, int screenWidth) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            String url = "", width = "", height = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("src")) {
                    url = attributes.get(i).getAttributeValue();
                } else if (attributes.get(i).getAttributeName().equals("width")) {
                    width = attributes.get(i).getAttributeValue();
                } else if (attributes.get(i).getAttributeName().equals("height")) {
                    height = attributes.get(i).getAttributeValue();
                }
            }
            this.getSettings().setJavaScriptEnabled(true);
            this.setWebViewClient(new WebViewClient());
            int frameWidth = screenWidth - (int) (60 * dpToPixelFactorValue);
            int frameHeight = 0;
            if ("100%".equals(width)) {
                frameHeight = Integer.parseInt(height);
            } else {
                frameHeight = (int) (frameWidth * ((Double.parseDouble(height))
                        / (Double.parseDouble(width))));
            }
            //this.loadUrl(url);
            String content = "<html><body><iframe width=\"" + frameWidth + "\" height=\""
                    + frameHeight + "\" src=\"" + url + "\"></body></html>";
            this.loadData(content, "text/html", "utf-8");
        }
    }

    public class ImageTagView extends android.support.v7.widget.AppCompatImageView {
        ArrayList<TagAttribute> attributes;
        Context context;
        String subjectURL;
        String mImageURL = null;

        public ImageTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            //this.setBackgroundResource(R.drawable.bg_selector);
            this.setClickable(true);
            String imageURL = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("src")) {
                    imageURL = attributes.get(i).getAttributeValue();
                } else if (attributes.get(i).getAttributeName().equals("data-cfsrc")) {
                    this.setVisibility(View.GONE);
                    return;
                }
            }
            Picasso.get().load(imageURL).into(this);
        }

        public ImageTagView(Context context, ArrayList<TagAttribute> attributes,
                            String subjectURL) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.subjectURL = subjectURL;
            //this.setBackgroundResource(R.drawable.bg_selector);
            this.setClickable(true);
            String imageURL = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("src")) {
                    imageURL = attributes.get(i).getAttributeValue();
                } else if (attributes.get(i).getAttributeName().equals("data-cfsrc")) {
                    this.setVisibility(View.GONE);
                    return;
                }
            }
            Picasso.get().load(imageURL).into(this);
        }

        public ImageTagView(Context context, String imageURL,
                            String subjectURL) {
            super(context);
            this.context = context;
            this.mImageURL = imageURL;
            this.subjectURL = subjectURL;
            //this.setBackgroundResource(R.drawable.bg_selector);
            this.setClickable(true);
            Picasso.get().load(imageURL).into(this);
        }

        public String getImageURL() {
            return mImageURL;
        }
    }

    public class ListIndexTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public ListIndexTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
        }
    }

    public class ListItemContainerView extends LinearLayout {
        Context context;

        public ListItemContainerView(Context context, String serialText, View contentView) {
            super(context);
            this.context = context;
            this.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams params1 = new LayoutParams((int) (24 * Utils.mDpToPixelFactor),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins((int) (6 * Utils.mDpToPixelFactor), 0, 0, 0);
            BaseTextView serialTextView = new BaseTextView(context);
            serialTextView.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
            serialTextView.setTextColor(Preferences.mTextColorNormal);
            serialTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            if (serialText != null) {
                serialTextView.setText(serialText);
            }
            serialTextView.setLayoutParams(params1);
            contentView.setLayoutParams(params2);
            this.addView(serialTextView);
            this.addView(contentView);
        }
    }

    public class OrderedListTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;
        public int childListIndexSerialNumber = 0;

        public OrderedListTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            int padding = (int) (6 * dpToPixelFactorValue);
            this.setPadding(padding, padding, padding, padding);
        }
    }

    public class ParagraphTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public ParagraphTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setPadding(0, (int) (10 * dpToPixelFactorValue)
                    , 0, (int) (10 * dpToPixelFactorValue));
        }
    }

    public class PreTagView extends BaseTextView {
        ArrayList<TagAttribute> attributes = null;
        Context context;
        public String classType = "";

        public PreTagView(Context context) {
            super(context);
            this.context = context;
            this.setTextColor(Color.parseColor("#EEEEEE"));
            this.setBackgroundColor(Utils.COLOR_BACKGROUND_PRE_TAG);
            this.setTypeface(Utils.mCodeTypeface);
            this.setTextSize(Utils.TEXT_SIZE_TAG_PRE * Preferences.mTextScaleFactor);
            this.setPadding((int) (4 * dpToPixelFactorValue), (int) (6 * dpToPixelFactorValue),
                    (int) (4 * dpToPixelFactorValue), (int) (6 * dpToPixelFactorValue));
        }

        public PreTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setTextColor(Color.parseColor("#EEEEEE"));
            this.setBackgroundColor(Utils.COLOR_BACKGROUND_PRE_TAG);
            this.setTypeface(Utils.mCodeTypeface);
            this.setTextSize(Utils.TEXT_SIZE_TAG_PRE * Preferences.mTextScaleFactor);
            this.setPadding((int) (4 * dpToPixelFactorValue), (int) (6 * dpToPixelFactorValue),
                    (int) (4 * dpToPixelFactorValue), (int) (6 * dpToPixelFactorValue));
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("class")) {
                    classType = attributes.get(i).getAttributeValue();
                }
            }
        }

        @Override
        public void resetTextView() {
            super.resetTextView();
            this.setTextSize(Utils.TEXT_SIZE_NORMAL * Preferences.mTextScaleFactor);
        }
    }

    public class TableTagView extends TableLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public TableTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setBackgroundResource(R.drawable.bg_table_td_tag);
            int padding = (int) (0.5 * dpToPixelFactorValue);
            this.setPadding(padding, padding, padding, padding);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(params);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int numberOfRows = this.getChildCount();
            for (int i = 0; i < numberOfRows; i++) {
                int actualChildCount = ((TableRow) getChildAt(i)).getChildCount();
                int weightSum = 0;
                for (int j = 0; j < actualChildCount; j++) {
                    View view = ((TableRow) getChildAt(i)).getChildAt(j);
                    int width = view.getMeasuredWidth();
                    ((TableRow.LayoutParams) view.getLayoutParams()).weight = width;
                    weightSum = weightSum + width;
                }
                ((TableRow) getChildAt(i)).setWeightSum(weightSum);
                getChildAt(i).requestLayout();
            }
        }
    }

    public class TableDivisionTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;
        String colSpan = null, rowSpan = null;

        public TableDivisionTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            this.setBackgroundResource(R.drawable.bg_table_td_tag);
            int padding = (int) (6 * dpToPixelFactorValue);
            this.setPadding(padding, padding, padding, padding);
            int num = attributes != null ? attributes.size() : 0;
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("colspan")) {
                    colSpan = attributes.get(i).getAttributeValue();
                }
                if (attributes.get(i).getAttributeName().equals("rowspan")) {
                    rowSpan = attributes.get(i).getAttributeValue();
                }
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (colSpan != null) {
                params.span = Integer.parseInt(colSpan);
            }
            setLayoutParams(params);
        }
    }

    public class TableRowTagView extends TableRow {
        ArrayList<TagAttribute> attributes;
        Context context;

        public TableRowTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(params);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class UnorderedListTagView extends LinearLayout {
        ArrayList<TagAttribute> attributes;
        Context context;

        public UnorderedListTagView(Context context, ArrayList<TagAttribute> attributes) {
            super(context);
            this.context = context;
            this.attributes = attributes;
            this.setOrientation(LinearLayout.VERTICAL);
            int padding = (int) (6 * dpToPixelFactorValue);
            this.setPadding(padding, padding, padding, padding);
            String classAttributeValue = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("class")) {
                    classAttributeValue = attributes.get(i).getAttributeValue();
                }
            }
            if (classAttributeValue.equals("pager")) {
                this.setVisibility(View.GONE);
            }
        }
    }

    private class CodeBackgroundSpan implements LineBackgroundSpan {

        int backgroundColor = 0;
        int padding = 0;
        Rect rect = null;

        public CodeBackgroundSpan(int backgroundColor, int padding) {
            this.backgroundColor = backgroundColor;
            this.padding = padding;
            rect = new Rect();
        }

        @Override
        public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                                   int baseline, int bottom,
                                   CharSequence text, int start, int end, int lnum) {
            final int textWidth = Math.round(paint.measureText(text, start, end));
            final int paintColor = paint.getColor();
            /*rect.set(left - padding,
                top - (lnum == 0 ? padding / 2 : - (padding / 2)),
                left + textWidth + padding,
                bottom + padding / 2);*/
            rect.set(left, top, left + textWidth, bottom);
            rect.inset(-40, 0);
            paint.setColor(backgroundColor);
            canvas.drawRect(rect, paint);
            //paint.setColor(paintColor);
        }
    }

    public class CustomURLSpan extends URLSpan {

        String mURL;
        Context context;

        CustomURLSpan(String url, Context ctx) {
            super(url);
            this.mURL = url;
            this.context = ctx;
        }

        @Override
        public void onClick(View widget) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setNegativeButton("Copy to clipboard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager clipboard = (ClipboardManager)
                            context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip =
                            ClipData.newPlainText("url_text", Styler.CustomURLSpan.this.mURL);
                    clipboard.setPrimaryClip(clip);
                }
            });
            builder.setPositiveButton("Open browser", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(Styler.CustomURLSpan.this.mURL));
                    context.startActivity(browserIntent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setMessage(CustomURLSpan.this.mURL);
            dialog.setCanceledOnTouchOutside(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        }
    }

    public class CodeTagSpan extends ReplacementSpan {
        private static final float PADDING = 30.0f;
        private RectF mRect;
        private int mBackgroundColor;
        private int mForegroundColor;

        public CodeTagSpan(int backgroundColor, int foregroundColor) {
            this.mRect = new RectF();
            this.mBackgroundColor = backgroundColor;
            this.mForegroundColor = foregroundColor;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            // Background
            mRect.set(x, top, x + paint.measureText(text, start, end) + PADDING, bottom);
            paint.setColor(mBackgroundColor);
            canvas.drawRect(mRect, paint);

            // Text
            paint.setColor(mForegroundColor);
            int xPos = Math.round(x + (PADDING / 2));
            int yPos = (int) ((canvas.getHeight() / 2)
             - ((paint.descent() + paint.ascent()) / 2));
            canvas.drawText(text, start, end, xPos, yPos, paint);
        }

        @Override
        public int getSize(Paint paint, CharSequence text,
                           int start, int end, Paint.FontMetricsInt fm) {
            return Math.round(paint.measureText(text, start, end) + PADDING);
        }
    }

}
