package com.sample.parserlibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Stack;

public class ParserView extends LinearLayout {

    final String YOUTUBE_API_KEY = "AIzaSyDph-whisa8me0VEKQ0yUgbO5haK_VRjv0";

    private Styler styler;
    private SyntaxHandler syntaxHandler;
    private String initialTag = "body";
    private String textToStartFrom = "<body";
    private Context context;
    private Stack<Tag> stack;
    boolean tagStart = false, tagEnd = false, comment = false, ignorable = false,
            pager_prev = false, pager_next = false;
    String tagStartText = "", tagEndText = "";
    SpannableStringBuilder text = new SpannableStringBuilder("");
    int text_len = 0;
    char lastCharacter = 0;
    //String mTopicURL = "", mSubjectURL = null, pagerPrevURL = null, pagerNextURL = null;
    YouTubeVideoPlayListener mYouTubeVideoPlayListener = null;

    public ParserView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        float dpToPixelFactorValue =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                        context.getResources().getDisplayMetrics());
        stack = new Stack<>();
        styler = new Styler(dpToPixelFactorValue);
        syntaxHandler = new SyntaxHandler();
    }

    public ParserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        float dpToPixelFactorValue =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                        context.getResources().getDisplayMetrics());
        stack = new Stack<>();
        styler = new Styler(dpToPixelFactorValue);
        syntaxHandler = new SyntaxHandler();
    }

    public ParserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        float dpToPixelFactorValue =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                context.getResources().getDisplayMetrics());
        stack = new Stack<>();
        styler = new Styler(dpToPixelFactorValue);
        syntaxHandler = new SyntaxHandler();
    }

    public void setYoutubeVideoPlayListener(YouTubeVideoPlayListener youtubeVideoPlayListener) {
        mYouTubeVideoPlayListener = youtubeVideoPlayListener;
    }

    public void loadURLContent(String subjectURL, String topicURL, String content,
                               String initialTag, String textToStartFrom) {
        //this.mSubjectURL = subjectURL;
        //this.mTopicURL = topicURL;
        if (initialTag == null) {
            this.initialTag = "body";
        } else {
            this.initialTag = initialTag;
        }
        if (textToStartFrom == null) {
            this.textToStartFrom = "<body";
        } else {
            this.textToStartFrom = textToStartFrom;
        }
        parseHTML(content);
    }

    /*public String getSubjectURL() {
        return mSubjectURL;
    }

    public String getTopicURL() {
        return mTopicURL;
    }*/

    public void clear() {
        this.removeAllViewsInLayout();
        this.removeAllViews();
    }

    public void parseHTML(String htmlText) {
        if (htmlText == null) {
            return;
        }
        //int start = htmlText.indexOf(textToStartFrom);
        int index = htmlText.indexOf(textToStartFrom);
        char character, nextCharacter;
        while (true) {
            character = htmlText.charAt(index);
            switch (character) {
                case '<':
                    nextCharacter = htmlText.charAt(index + 1);
                    if (!tagStart && (nextCharacter == '_'
                            || (nextCharacter >= 'a' && nextCharacter <= 'z')
                            || (nextCharacter >= 'A' && nextCharacter <= 'Z'))) {
                        tagStart = true;
                    } else if (!tagEnd && nextCharacter == '/') {
                        tagEnd = true;
                        index++;
                    } else if (checkTextAtPosition("!--", htmlText, index + 1)) {
                        comment = true;
                        index = index + 3;
                    } else {
                        lastCharacter = character;
                        text = styler.appendFormattedCharacter(text, character);
                        text_len++;
                    }
                    break;
                case '>':
                    if (tagStart) {
                        tagStart = false;
                        pushTag(tagStartText);
                        tagStartText = "";
                    } else if (tagEnd) {
                        tagEnd = false;
                        popTag(tagEndText);
                        tagEndText = "";
                    } else if (comment &&
                            checkTextAtPosition("--", htmlText, index - 2)) {
                        comment = false;
                    } else {
                        lastCharacter = character;
                        text = styler.appendFormattedCharacter(text, character);
                        text_len++;
                    }
                    break;
                case '&':
                    if (tagStart) {
                        tagStartText = tagStartText + character;
                        if (checkTextAtPosition("amp;", htmlText, index + 1)) {
                            index = index + 4;
                        }
                        break;
                    }
                    if (tagEnd || comment) {
                        // do nothing
                    } else if (checkTextAtPosition("amp;", htmlText, index + 1)) {
                        index = index + 4;
                        text = styler.appendFormattedCharacter(text, '&');
                        text_len++;
                        lastCharacter = '&';
                        break;
                    } else if (checkTextAtPosition("#61623;", htmlText, index + 1)) {
                        index = index + 7;
                        break;
                    } else if (checkTextAtPosition("#65533;", htmlText, index + 1)) {
                        index = index + 7;
                        break;
                    } else if (checkTextAtPosition("#1436;", htmlText, index + 1)) {
                        index = index + 6;
                        break;
                    } else if(checkTextAtPosition("nbsp;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, ' ');
                        text_len++;
                        lastCharacter = ' ';
                        break;
                    } else if(checkTextAtPosition("ndash;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, '-');
                        text_len++;
                        lastCharacter = '-';
                        break;
                    } else if(checkTextAtPosition("apos;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '\'');
                        text_len++;
                        lastCharacter = '"';
                        break;
                    } else if(checkTextAtPosition("quot;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '"');
                        text_len++;
                        lastCharacter = '"';
                        break;
                    } else if(checkTextAtPosition("lsquo;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, '\'');
                        text_len++;
                        lastCharacter = '\'';
                        break;
                    } else if(checkTextAtPosition("rsquo;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, '\'');
                        text_len++;
                        lastCharacter = '\'';
                        break;
                    } else if(checkTextAtPosition("sbquo;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, ',');
                        text_len++;
                        lastCharacter = ',';
                        break;
                    } else if(checkTextAtPosition("ldquo;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, '"');
                        text_len++;
                        lastCharacter = '"';
                        break;
                    } else if(checkTextAtPosition("rdquo;", htmlText, index + 1)) {
                        index = index + 6;
                        text = styler.appendFormattedCharacter(text, '"');
                        text_len++;
                        lastCharacter = '"';
                        break;
                    } else if(checkTextAtPosition("middot;", htmlText, index + 1)) {
                        index = index + 7;
                        text = styler.appendFormattedCharacter(text, '\u00B7');
                        text_len++;
                        lastCharacter = '\u00B7';
                        break;
                    } else if(checkTextAtPosition("frac12;", htmlText, index + 1)) {
                        index = index + 7;
                        text = styler.appendFormattedCharacter(text, '\u00BD');
                        text_len++;
                        lastCharacter = '\u00BD';
                        break;
                    } else if(checkTextAtPosition("larr;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '\u2190');
                        text_len++;
                        lastCharacter = '\u2190';
                        break;
                    } else if(checkTextAtPosition("rarr;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '\u2192');
                        text_len++;
                        lastCharacter = '\u2192';
                        break;
                    } else if(checkTextAtPosition("lt;", htmlText, index + 1)) {
                        index = index + 3;
                        text = styler.appendFormattedCharacter(text, '<');
                        text_len++;
                        lastCharacter = '<';
                        break;
                    } else if(checkTextAtPosition("gt;", htmlText, index + 1)) {
                        index = index + 3;
                        text = styler.appendFormattedCharacter(text, '>');
                        text_len++;
                        lastCharacter = '>';
                        break;
                    } else if(checkTextAtPosition("#160;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, ' ');
                        text_len++;
                        lastCharacter = ' ';
                        break;
                    } else if(checkTextAtPosition("#64;", htmlText, index + 1)) {
                        index = index + 4;
                        text = styler.appendFormattedCharacter(text, '@');
                        text_len++;
                        lastCharacter = '@';
                        break;
                    } else if(checkTextAtPosition("#960;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '\u03C0');
                        text_len++;
                        lastCharacter = '\u03C0';
                        break;
                    } else if(checkTextAtPosition("#215;", htmlText, index + 1)) {
                        index = index + 5;
                        text = styler.appendFormattedCharacter(text, '\u00D7');
                        text_len++;
                        lastCharacter = '\u00D7';
                        break;
                    } else {
                        lastCharacter = character;
                        text = styler.appendFormattedCharacter(text, character);
                        text_len++;
                        break;
                    }
                case 9:
                    if (!styler.preTagOpen) {
                        character = ' ';
                    }
                case 10:
                    if (!styler.preTagOpen) {
                        character = ' ';
                    }
                default:
                    if (comment) {
                        // do nothing
                    } else if (tagStart) {
                        tagStartText = tagStartText + character;
                    } else if (tagEnd) {
                        tagEndText = tagEndText + character;
                    } else {
                        if (styler.preTagOpen) {
                            // do nothing
                        } else if ((text_len == 0 || lastCharacter == ' ') && character == ' ') {
                            break;
                        } else {
                            lastCharacter = character;
                        }
                        if (!ignorable) {
                            text = styler.appendFormattedCharacter(text, character);
                        }
                        text_len++;
                    }
            }
            if (!tagStart && !tagEnd && stack.isEmpty()) {
                break;
            }
            index++;
        }
    }

    private void insertTextView() {
        if (!stack.isEmpty()) {
            View topTextView = stack.pop().view;
            if (topTextView instanceof Styler.PreTagView) {
                String classType = ((Styler.PreTagView) topTextView).classType;
                int type = -1;
                if (classType.equalsIgnoreCase("c")
                        || classType.equalsIgnoreCase("cpp")) {
                    type = SyntaxHandler.TYPE_C_LANGUAGE;
                } else if (classType.equalsIgnoreCase("java")) {
                    type = SyntaxHandler.TYPE_JAVA_LANGUAGE;
                } else if (classType.equalsIgnoreCase("xml")) {
                    type = SyntaxHandler.TYPE_XML;
                } else if (classType.equalsIgnoreCase("none")) {
                    type = SyntaxHandler.TYPE_NONE;
                } else {
                    type = SyntaxHandler.TYPE_NONE;
                }
                ((TextView) topTextView).setText(syntaxHandler
                        .obtainSpannedTextForType(text.toString(), type));
            } else if (topTextView instanceof TextView) {
                ((TextView) topTextView).setText(text);
            }
            if (text_len > 0) {
                View topContainerView = stack.peek().view;
                if (topContainerView instanceof LinearLayout
                        && text.toString().trim().length() > 0) {
                    ((LinearLayout) topContainerView).addView(topTextView);
                    /*if (toBeAdded) {
                        View v = new ImageView(context);
                        ((ImageView) v).setImageResource(R.drawable.feedback_screen_about_us2);
                        ((LinearLayout) topContainerView).addView(v);
                        toBeAdded = false;
                    }*/
                }
                text = new SpannableStringBuilder("");
                text_len = 0;
            }
            styler.resetSpan(Styler.TAG_ALL);
            styler.timeTagOpen = false;
        }
    }

    private void pushTag(String string) {
        boolean endsStraightAway = false, tN = true, aN = false, aV = false;
        String tagName = "", attrName = "", attrValue = "";
        ArrayList<TagAttribute> attributes = new ArrayList<>();
        int len = string.length();
        for (int i = 0; i < len; i++) {
            char ch = string.charAt(i);
            if (tN) {
                if (ch == ' ') {
                    tN = false;
                    aN = true;
                } else if (ch == '/') {
                    endsStraightAway = true;
                } else {
                    tagName = tagName + ch;
                }
            } else if (aN) {
                if (ch == '"') {
                    aN = false;
                    aV = true;
                } else if (ch == '=' || ch == ' ') {
                } else if (ch == '/') {
                    endsStraightAway = true;
                } else {
                    attrName = attrName + ch;
                }
            } else if (aV) {
                if (ch == '"') {
                    aV = false;
                    aN = true;
                    attributes.add(new TagAttribute(attrName, attrValue));
                    attrName = "";
                    attrValue = "";
                } else {
                    attrValue = attrValue + ch;
                }
            }
        }
        if (tagName.equals("a")) {
            if (!endsStraightAway) {
                /*styler.anchorTagStart = text_len;
                styler.anchorTagAttributes = attributes;
                String url = "";
                int num = attributes.size();
                for (int i = 0; i < num; i++) {
                    if (attributes.get(i).getAttributeName().equals("href")) {
                        url = attributes.get(i).getAttributeValue();
                    }
                }
                if (pager_prev) {
                    pagerPrevURL = url;
                    pager_prev = false;
                } else if (pager_next) {
                    pagerNextURL = url;
                    pager_next = false;
                } else {
                    pagerPrevURL = null;
                    pagerNextURL = null;
                }*/
            }
        } else if (tagName.equals("article")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.ArticleTagView linearLayout =
                    styler.new ArticleTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("b")) {
            if (!endsStraightAway) {
                styler.boldTagStart = text_len;
            }
        } else if (tagName.equals("strong")) {
            if (!endsStraightAway) {
                styler.boldTagStart = text_len;
            }
        } else if (tagName.equals("body")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.BodyTagView linearLayout =
                    styler.new BodyTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("br")) {
            insertTextView();
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("code")) {
            if (!endsStraightAway) {
                styler.codeTagStart = text_len;
            }
        } else if (tagName.equals("div")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.DivTagView linearLayout =
                    styler.new DivTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("figure")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.FigureTagView linearLayout =
                    styler.new FigureTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("h1") || tagName.equals("h2") || tagName.equals("h3")
                || tagName.equals("h4") || tagName.equals("h5") || tagName.equals("h6")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.HeaderTagView textView =
                    styler.new HeaderTagView(context, tagName, attributes);
            stack.push(new Tag(tagName, attributes, textView));
        } else if (tagName.equals("i")) {
            if (!endsStraightAway) {
                styler.italicTagStart = text_len;
            }
        } else if (tagName.equals("iframe")) {
            insertTextView();
            String url = "";
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("src")) {
                    url = attributes.get(i).getAttributeValue();
                }
            }
            if (url.contains("www.youtube.com")) {
                String code;
                if (url.contains("/embed/")) {
                    int start = url.indexOf("/embed/") + 7;
                    int end = url.indexOf('?', start);
                    code = url.substring(start, end);
                } else {
                    int start = url.indexOf("?v=") + 3;
                    code = url.substring(start);
                }
                final String videoCode = code;
                YouTubePlayerView youTubePlayerView = new YouTubePlayerView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMarginStart((int) (12 * styler.getDpToPixelFactorValue()));
                lp.setMarginEnd((int) (12 * styler.getDpToPixelFactorValue()));
                youTubePlayerView.setLayoutParams(lp);
                youTubePlayerView.initialize(YOUTUBE_API_KEY,
                        new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        if (!b) {
                            youTubePlayer.cueVideo(videoCode);
                            youTubePlayer.setPlaybackEventListener(
                                    new YouTubePlayer.PlaybackEventListener() {
                                @Override
                                public void onPlaying() {
                                    mYouTubeVideoPlayListener.onYouTubeVideoPlaying();
                                }

                                @Override
                                public void onPaused() {
                                    mYouTubeVideoPlayListener.onYouTubeVideoPaused();
                                }

                                @Override
                                public void onStopped() {
                                    mYouTubeVideoPlayListener.onYouTubeVideoStopped();
                                }

                                @Override
                                public void onBuffering(boolean b) {

                                }

                                @Override
                                public void onSeekTo(int i) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult
                                                                youTubeInitializationResult) {

                    }
                });
                View topContainerView = stack.peek().view;
                if (topContainerView instanceof LinearLayout) {
                    ((LinearLayout) topContainerView).addView(youTubePlayerView);
                }
            } else {
                Styler.IFrameTagView iFrameWebView = styler.new IFrameTagView(
                        context, attributes, ParserView.this.getMeasuredWidth());
                iFrameWebView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                /*iFrameWebView.getSettings().setLoadWithOverviewMode(true);
                iFrameWebView.getSettings().setUseWideViewPort(true);*/
                View topContainerView = stack.peek().view;
                if (topContainerView instanceof LinearLayout) {
                    ((LinearLayout) topContainerView).addView(iFrameWebView);
                }
            }
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("img")) {
            insertTextView();
            final Styler.ImageTagView imageView =
                    styler.new ImageTagView(context, attributes/*, mSubjectURL*/);
            imageView.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imageView.getDrawable() == null) {
                        view.setVisibility(View.GONE);
                    } else {
                        view.setVisibility(View.VISIBLE);
                        Utils.mCurrentPreviewImageBitmap =
                                ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Intent intent = new Intent(context, ImagePreviewActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
            View topContainerView = stack.peek().view;
            if (topContainerView instanceof LinearLayout) {
                ((LinearLayout) topContainerView).addView(imageView);
            }
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("li")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.ListIndexTagView linearLayout =
                    styler.new ListIndexTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            int num = attributes.size();
            for (int i = 0; i < num; i++) {
                if (attributes.get(i).getAttributeName().equals("class")) {
                    String classValue = attributes.get(i).getAttributeValue();
                    if (classValue.equalsIgnoreCase("previous")) {
                        pager_prev = true;
                    } else if (classValue.equalsIgnoreCase("next")) {
                        pager_next = true;
                    }
                }
            }
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("ol")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.OrderedListTagView linearLayout =
                    styler.new OrderedListTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("p")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.ParagraphTagView linearLayout =
                    styler.new ParagraphTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("pre")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            } else {
                styler.preTagOpen = true;
            }
            Styler.PreTagView textView =
                    styler.new PreTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, textView));
        } else if (tagName.equals("script")) {
            if (!endsStraightAway) {
                ignorable = true;
            }
        } else if (tagName.equals("time")) {
            if (!endsStraightAway) {
                styler.timeTagOpen = true;
            }
        } else if (tagName.equals("table")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.TableTagView linearLayout =
                    styler.new TableTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("tr")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.TableRowTagView linearLayout =
                    styler.new TableRowTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("th") || tagName.equals("td")) {
            if (tagName.equals("th")) {
                styler.tHeadTagStart = text_len;
            }
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.TableDivisionTagView linearLayout =
                    styler.new TableDivisionTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("u")) {
            if (!endsStraightAway) {
                styler.underlineTagStart = text_len;
            }
        } else if (tagName.equals("ul")) {
            insertTextView();
            if (endsStraightAway) {
                return;
            }
            Styler.UnorderedListTagView linearLayout =
                    styler.new UnorderedListTagView(context, attributes);
            stack.push(new Tag(tagName, attributes, linearLayout));
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, tagName, attributes);
            stack.push(new Tag("", null, textView));
        }
    }

    //private boolean toBeAdded = false;

    private void popTag(String tagName) {
        if (tagName.equals("a")) {
            styler.anchorTagEnd = text_len;
            //text = styler.putURL(text, context);
            //toBeAdded = true;
        } else if (tagName.equals("b")) {
            styler.boldTagEnd = text_len;
            text = styler.applySpanOnText(Styler.TAG_BOLD, text);
        } else if (tagName.equals("strong")) {
            styler.boldTagEnd = text_len;
            text = styler.applySpanOnText(Styler.TAG_BOLD, text);
        } else if (tagName.equals("i")) {
            styler.italicTagEnd = text_len;
            text = styler.applySpanOnText(Styler.TAG_ITALIC, text);
        } else if (tagName.equals("script")) {
            ignorable = false;
            stack.peek().view.setVisibility(View.GONE);
        } else if (tagName.equals("time")) {
            styler.timeTagOpen = false;
        } else if (tagName.equals("u")) {
            styler.underlineTagEnd = text_len;
            text = styler.applySpanOnText(Styler.TAG_UNDERLINE, text);
        } else if (tagName.equals("code")) {
            styler.codeTagEnd = text_len;
            text = styler.applySpanOnText(Styler.TAG_CODE, text);
        } else if (tagName.equals("pre")) {
            styler.preTagOpen = false;
            insertTextView();
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, stack.peek().name,
                            stack.peek().attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("h1") || tagName.equals("h2") || tagName.equals("h3")
                || tagName.equals("h4") || tagName.equals("h5") || tagName.equals("h6")) {
            insertTextView();
            Styler.ContainerTextView textView =
                    styler.new ContainerTextView(context, stack.peek().name,
                            stack.peek().attributes);
            stack.push(new Tag("", null, textView));
        } else if (tagName.equals("article") || tagName.equals("body")
                || tagName.equals("div") || tagName.equals("figure")
                || tagName.equals("li") || tagName.equals("ol")
                || tagName.equals("p") || tagName.equals("ul")
                || tagName.equals("table") || tagName.equals("th")
                || tagName.equals("tr") || tagName.equals("td")) {
            if (tagName.equals("th")) {
                styler.tHeadTagEnd = text_len;
                text = styler.applySpanOnText(Styler.TAG_TABLE_HEAD, text);
            }
            insertTextView();
            Tag tagOnTopOfStack = stack.pop();
            View topView = tagOnTopOfStack.view;
            if (topView instanceof ViewGroup && tagOnTopOfStack.name.equals(tagName)) {
                if (tagName.equals(initialTag) && stack.size() == 0) {
                    ParserView.this.addView(topView);
                } else {
                    Tag containerTagOfTopView = stack.peek();
                    if (containerTagOfTopView.view instanceof ViewGroup) {
                        if (containerTagOfTopView.name.equals("ol")) {
                            int childCountOfThisOrderedList =
                                    ++((Styler.OrderedListTagView)containerTagOfTopView.view)
                                            .childListIndexSerialNumber;
                            ((Styler.OrderedListTagView)containerTagOfTopView.view)
                                    .childListIndexSerialNumber = childCountOfThisOrderedList;
                            String serialText = null;
                            if (tagName.equals("li")) {
                                serialText = childCountOfThisOrderedList + ".";
                            }
                            Styler.ListItemContainerView listItemContainerLayout =
                                    styler.new ListItemContainerView(context, serialText, topView);
                            ((ViewGroup) containerTagOfTopView.view)
                                    .addView(listItemContainerLayout);
                        } else if (containerTagOfTopView.name.equals("ul")) {
                            String serialText = null;
                            if (tagName.equals("li")) {
                                serialText = "" + '\u2022';
                            }
                            Styler.ListItemContainerView listItemContainerLayout =
                                    styler.new ListItemContainerView(context, serialText, topView);
                            ((ViewGroup) containerTagOfTopView.view)
                                    .addView(listItemContainerLayout);
                        } else {
                            ((ViewGroup) containerTagOfTopView.view).addView(topView);
                        }
                    }
                    Styler.ContainerTextView textView =
                            styler.new ContainerTextView(context, stack.peek().name,
                                    stack.peek().attributes);
                    stack.push(new Tag("", null, textView));
                }
            }
        }
    }

    private boolean checkTextAtPosition(String text, String source, int start) {
        int len = text.length();
        if(source.length() < len) {
            return false;
        }
        for(int i = 0; i < len; i++) {
            if(source.charAt(start + i) != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
