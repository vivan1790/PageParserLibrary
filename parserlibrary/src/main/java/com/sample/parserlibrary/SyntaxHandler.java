package com.sample.parserlibrary;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.HashSet;

public class SyntaxHandler {
    private HashSet<String> mCLanguageReservedKeywords;
    private HashSet<String> mJavaReservedKeywords;
    private HashSet<String> mJavaLiteralValueKeywords;
    private HashSet<String> mJavaMostUsedClasses;

    public static final int TYPE_NONE = 0;
    public static final int TYPE_C_LANGUAGE = 1;
    public static final int TYPE_JAVA_LANGUAGE = 2;
    public static final int TYPE_XML = 3;

    private final int NOTHING = 0;
    private final int RESERVED_KEYWORD = 1;
    private final int LITERAL_VALUE_KEYWORD = 2;
    private final int NUMERIC_LITERAL = 3;
    private final int MOST_USED_CLASSES = 4;

    public SyntaxHandler() {
        mCLanguageReservedKeywords = new HashSet<>();
        addCLanguageKeywords();
        mJavaReservedKeywords = new HashSet<>();
        mJavaLiteralValueKeywords = new HashSet<>();
        mJavaMostUsedClasses = new HashSet<>();
        addJavaKeywords();
    }

    private void addCLanguageKeywords() {
        mCLanguageReservedKeywords.add("auto");
        mCLanguageReservedKeywords.add("break");
        mCLanguageReservedKeywords.add("case");
        mCLanguageReservedKeywords.add("char");
        mCLanguageReservedKeywords.add("const");
        mCLanguageReservedKeywords.add("continue");
        mCLanguageReservedKeywords.add("default");
        mCLanguageReservedKeywords.add("do");
        mCLanguageReservedKeywords.add("double");
        mCLanguageReservedKeywords.add("else");
        mCLanguageReservedKeywords.add("enum");
        mCLanguageReservedKeywords.add("extern");
        mCLanguageReservedKeywords.add("float");
        mCLanguageReservedKeywords.add("for");
        mCLanguageReservedKeywords.add("goto");
        mCLanguageReservedKeywords.add("if");
        mCLanguageReservedKeywords.add("inline");
        mCLanguageReservedKeywords.add("int");
        mCLanguageReservedKeywords.add("long");
        mCLanguageReservedKeywords.add("register");
        mCLanguageReservedKeywords.add("restrict");
        mCLanguageReservedKeywords.add("return");
        mCLanguageReservedKeywords.add("short");
        mCLanguageReservedKeywords.add("signed");
        mCLanguageReservedKeywords.add("sizeof");
        mCLanguageReservedKeywords.add("static");
        mCLanguageReservedKeywords.add("struct");
        mCLanguageReservedKeywords.add("switch");
        mCLanguageReservedKeywords.add("typedef");
        mCLanguageReservedKeywords.add("union");
        mCLanguageReservedKeywords.add("unsigned");
        mCLanguageReservedKeywords.add("void");
        mCLanguageReservedKeywords.add("volatile");
        mCLanguageReservedKeywords.add("while");
        mCLanguageReservedKeywords.add("_Bool");
        mCLanguageReservedKeywords.add("_Complex");
        mCLanguageReservedKeywords.add("_Imaginary");

        //C++ keywords
        mCLanguageReservedKeywords.add("alignas");
        mCLanguageReservedKeywords.add("alignof");
        mCLanguageReservedKeywords.add("and");
        mCLanguageReservedKeywords.add("and_eq");
        mCLanguageReservedKeywords.add("asm");
        mCLanguageReservedKeywords.add("atomic_cancel");
        mCLanguageReservedKeywords.add("atomic_commit");
        mCLanguageReservedKeywords.add("atomic_noexcept");
//mCLanguageReservedKeywords.add("auto");
        mCLanguageReservedKeywords.add("bitand");
        mCLanguageReservedKeywords.add("bitor");
        mCLanguageReservedKeywords.add("bool");
//mCLanguageReservedKeywords.add("break");
//mCLanguageReservedKeywords.add("case");
        mCLanguageReservedKeywords.add("catch");
//mCLanguageReservedKeywords.add("char");
        mCLanguageReservedKeywords.add("char16_t");
        mCLanguageReservedKeywords.add("char32_t");
        mCLanguageReservedKeywords.add("class");
        mCLanguageReservedKeywords.add("compl");
        mCLanguageReservedKeywords.add("concept");
//mCLanguageReservedKeywords.add("const");
        mCLanguageReservedKeywords.add("constexpr");
        mCLanguageReservedKeywords.add("const_cast");
//mCLanguageReservedKeywords.add("continue");
        mCLanguageReservedKeywords.add("decltype");
//mCLanguageReservedKeywords.add("default");
        mCLanguageReservedKeywords.add("delete");
//mCLanguageReservedKeywords.add("do");
//mCLanguageReservedKeywords.add("double");
        mCLanguageReservedKeywords.add("dynamic_cast");
//mCLanguageReservedKeywords.add("else");
//mCLanguageReservedKeywords.add("enum");
        mCLanguageReservedKeywords.add("explicit");
        mCLanguageReservedKeywords.add("export");
//mCLanguageReservedKeywords.add("extern");
        mCLanguageReservedKeywords.add("false");
//mCLanguageReservedKeywords.add("float");
//mCLanguageReservedKeywords.add("for");
        mCLanguageReservedKeywords.add("friend");
//mCLanguageReservedKeywords.add("goto");
//mCLanguageReservedKeywords.add("if");
//mCLanguageReservedKeywords.add("inline");
//mCLanguageReservedKeywords.add("int");
        mCLanguageReservedKeywords.add("import");
//mCLanguageReservedKeywords.add("long");
        mCLanguageReservedKeywords.add("module");
        mCLanguageReservedKeywords.add("mutable");
        mCLanguageReservedKeywords.add("namespace");
        mCLanguageReservedKeywords.add("new");
        mCLanguageReservedKeywords.add("noexcept");
        mCLanguageReservedKeywords.add("not");
        mCLanguageReservedKeywords.add("not_eq");
        mCLanguageReservedKeywords.add("nullptr");
        mCLanguageReservedKeywords.add("operator");
        mCLanguageReservedKeywords.add("or");
        mCLanguageReservedKeywords.add("or_eq");
        mCLanguageReservedKeywords.add("private");
        mCLanguageReservedKeywords.add("protected");
        mCLanguageReservedKeywords.add("public");
//mCLanguageReservedKeywords.add("register");
        mCLanguageReservedKeywords.add("reinterpret_cast");
        mCLanguageReservedKeywords.add("requires");
//mCLanguageReservedKeywords.add("return");
//mCLanguageReservedKeywords.add("short");
//mCLanguageReservedKeywords.add("signed");
//mCLanguageReservedKeywords.add("sizeof");
//mCLanguageReservedKeywords.add("static");
        mCLanguageReservedKeywords.add("static_assert");
        mCLanguageReservedKeywords.add("static_cast");
//mCLanguageReservedKeywords.add("struct");
//mCLanguageReservedKeywords.add("switch");
        mCLanguageReservedKeywords.add("synchronized");
        mCLanguageReservedKeywords.add("template");
        mCLanguageReservedKeywords.add("this");
        mCLanguageReservedKeywords.add("thread_local");
        mCLanguageReservedKeywords.add("throw");
        mCLanguageReservedKeywords.add("true");
        mCLanguageReservedKeywords.add("try");
//mCLanguageReservedKeywords.add("typedef");
        mCLanguageReservedKeywords.add("typeid");
        mCLanguageReservedKeywords.add("typename");
//mCLanguageReservedKeywords.add("union");
//mCLanguageReservedKeywords.add("unsigned");
        mCLanguageReservedKeywords.add("using");
        mCLanguageReservedKeywords.add("virtual");
//mCLanguageReservedKeywords.add("void");
//mCLanguageReservedKeywords.add("volatile");
        mCLanguageReservedKeywords.add("wchar_t");
//mCLanguageReservedKeywords.add("while");
        mCLanguageReservedKeywords.add("xor");
        mCLanguageReservedKeywords.add("xor_eq");
    }

    private void addJavaKeywords() {
        mJavaReservedKeywords.add("abstract");
        mJavaReservedKeywords.add("assert");
        mJavaReservedKeywords.add("boolean");
        mJavaReservedKeywords.add("break");
        mJavaReservedKeywords.add("byte");
        mJavaReservedKeywords.add("case");
        mJavaReservedKeywords.add("catch");
        mJavaReservedKeywords.add("char");
        mJavaReservedKeywords.add("class");
        mJavaReservedKeywords.add("const");
        mJavaReservedKeywords.add("continue");
        mJavaReservedKeywords.add("default");
        mJavaReservedKeywords.add("do");
        mJavaReservedKeywords.add("double");
        mJavaReservedKeywords.add("else");
        mJavaReservedKeywords.add("enum");
        mJavaReservedKeywords.add("extends");
        mJavaReservedKeywords.add("final");
        mJavaReservedKeywords.add("finally");
        mJavaReservedKeywords.add("float");
        mJavaReservedKeywords.add("for");
        mJavaReservedKeywords.add("goto");
        mJavaReservedKeywords.add("if");
        mJavaReservedKeywords.add("implements");
        mJavaReservedKeywords.add("import");
        mJavaReservedKeywords.add("instanceof");
        mJavaReservedKeywords.add("int");
        mJavaReservedKeywords.add("interface");
        mJavaReservedKeywords.add("long");
        mJavaReservedKeywords.add("native");
        mJavaReservedKeywords.add("new");
        mJavaReservedKeywords.add("package");
        mJavaReservedKeywords.add("private");
        mJavaReservedKeywords.add("protected");
        mJavaReservedKeywords.add("public");
        mJavaReservedKeywords.add("return");
        mJavaReservedKeywords.add("short");
        mJavaReservedKeywords.add("static");
        mJavaReservedKeywords.add("strictfp");
        mJavaReservedKeywords.add("super");
        mJavaReservedKeywords.add("switch");
        mJavaReservedKeywords.add("synchronized");
        mJavaReservedKeywords.add("this");
        mJavaReservedKeywords.add("throw");
        mJavaReservedKeywords.add("throws");
        mJavaReservedKeywords.add("transient");
        mJavaReservedKeywords.add("try");
        mJavaReservedKeywords.add("void");
        mJavaReservedKeywords.add("volatile");
        mJavaReservedKeywords.add("while");

        mJavaLiteralValueKeywords.add("false");
        mJavaLiteralValueKeywords.add("null");
        mJavaLiteralValueKeywords.add("true");

        mJavaMostUsedClasses.add("Integer");
        mJavaMostUsedClasses.add("String");
        mJavaMostUsedClasses.add("System");
    }

    private int getCLanguageTokenType(String token) {
        if(mCLanguageReservedKeywords.contains(token)) {
            return RESERVED_KEYWORD;
        } else if(token.equalsIgnoreCase("NULL")) {
            return LITERAL_VALUE_KEYWORD;
        } else if(token.matches("[0-9]+")) {
            return NUMERIC_LITERAL;
        }
        return NOTHING;
    }

    private int getJavaTokenType(String token) {
        if(mJavaReservedKeywords.contains(token)) {
            return RESERVED_KEYWORD;
        } else if(mJavaLiteralValueKeywords.contains(token)) {
            return LITERAL_VALUE_KEYWORD;
        } else if(token.matches("[0-9]+")) {
            return NUMERIC_LITERAL;
        } else if(mJavaMostUsedClasses.contains(token)) {
            return MOST_USED_CLASSES;
        }
        return NOTHING;
    }

    private boolean isOperatorCharacter(char ch) {
        if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '<' || ch == '>'
                || ch == '=' || ch == ':' || ch == '?' || ch == '%' || ch == '&'
                || ch == '^' || ch == '!' || ch == '|') {
            return true;
        }
        return false;
    }

    private boolean isBracketCharacter(char ch) {
        if(ch == '{' || ch == '}' || ch == '(' || ch == ')' || ch == '[' || ch == ']') {
            return true;
        }
        return false;
    }

    private boolean isDelimiterCharacter(char ch) {
        if(ch == ';' || ch == ',' || ch == '.' || ch == '$' || ch == '#' || ch == '@'
                || ch == '~' || ch == 32 || ch == 10) {
            return true;
        }
        return false;
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

    public SpannableStringBuilder obtainSpannedTextForType(String text, int type) {
        if (type == TYPE_XML) {
            return obtainSpannedTextForXML(text);
        } else if (type == TYPE_NONE) {
            return obtainDefaultSpannedText(text);
        }
        String mActualText = "";
        int index = 0, length = text.length();
        boolean ignorable = false, ignoreEndtag = false;
        while(index < length) {
            if(checkTextAtPosition("<b>", text, index)
                    || checkTextAtPosition("<i>", text, index)
                    || checkTextAtPosition("<font", text, index)
                    || checkTextAtPosition("<span", text, index)
                    || checkTextAtPosition("</b>", text, index)
                    || checkTextAtPosition("</i>", text, index)
                    || checkTextAtPosition("</font>", text, index)
                    || checkTextAtPosition("</span>", text, index)) {
                ignorable = true;
            } else if(ignorable && text.charAt(index) == '>') {
                ignoreEndtag = true;
                ignorable = false;
            }
            if(!ignorable) {
                if(ignoreEndtag) {
                    ignoreEndtag = false;
                } else {
                    mActualText = mActualText + text.charAt(index);
                }
            }
            index++;
        }
        SpannableStringBuilder mSpannedCode = new SpannableStringBuilder("");
        boolean mIncludeSection = false, mCommentType1Section = false,
                mCommentType2Section = false, mSingleQuoteSection = false,
                mDoubleQuoteSection = false;
        String mToken = "";
        index = 0;
        length = mActualText.length();
        while(index < length) {
            if(type == TYPE_C_LANGUAGE && checkTextAtPosition("#include",
                    mActualText, index)) {
                mIncludeSection = true;
                SpannableString temp = new SpannableString("#");
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_INCLUDE_STATEMENT)
                        , 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp);
            } else if(mActualText.charAt(index) == '\'') {
                if(mSingleQuoteSection) {
                    int len = mToken.length();
                    SpannableString temp = new SpannableString(mToken);
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_SINGLE_QUOTES)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpannedCode.append(temp);
                    mToken = "";
                    mSingleQuoteSection = false;
                } else {
                    mSingleQuoteSection = true;
                }
                SpannableString temp1 = new SpannableString("\'");
                temp1.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_SINGLE_QUOTES)
                        , 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp1);
            } else if(mActualText.charAt(index) == '\"') {
                if(mDoubleQuoteSection) {
                    int len = mToken.length();
                    SpannableString temp = new SpannableString(mToken);
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_DOUBLE_QUOTES)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpannedCode.append(temp);
                    mToken = "";
                    mDoubleQuoteSection = false;
                } else {
                    mDoubleQuoteSection = true;
                }
                SpannableString temp1 = new SpannableString("\"");
                temp1.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_DOUBLE_QUOTES)
                        , 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp1);
            } else if((type == TYPE_C_LANGUAGE || type == TYPE_JAVA_LANGUAGE)
                    && checkTextAtPosition("//", mActualText, index)) {
                mCommentType1Section = true;
                SpannableString temp = new SpannableString("//");
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_COMMENT)
                        , 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp);
                index++;
            } else if((type == TYPE_C_LANGUAGE || type == TYPE_JAVA_LANGUAGE)
                    && checkTextAtPosition("/*", mActualText, index)) {
                mCommentType2Section = true;
                SpannableString temp = new SpannableString("/*");
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_COMMENT)
                        , 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp);
                index++;
            } else if((type == TYPE_C_LANGUAGE || type == TYPE_JAVA_LANGUAGE)
                    && checkTextAtPosition("*/", mActualText, index)) {
                mCommentType2Section = false;
                SpannableString temp = new SpannableString("*/");
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_COMMENT)
                        , 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannedCode.append(temp);
                index++;
            } else if(mActualText.charAt(index) == '\n') {
                mSpannedCode.append(decideToken(mToken, type, mIncludeSection,
                        mCommentType1Section, mCommentType2Section,
                        mSingleQuoteSection, mDoubleQuoteSection));
                if(mIncludeSection) {
                    mIncludeSection = false;
                } else if(mCommentType1Section) {
                    mCommentType1Section = false;
                }
                mSpannedCode.append('\n');
                mToken = "";
            } else if((type == TYPE_C_LANGUAGE || type == TYPE_JAVA_LANGUAGE)
                    && (isBracketCharacter(mActualText.charAt(index))
                    || isOperatorCharacter(mActualText.charAt(index))
                    || isDelimiterCharacter(mActualText.charAt(index)))) {
                mSpannedCode.append(decideToken(mToken, type, mIncludeSection,
                        mCommentType1Section, mCommentType2Section,
                        mSingleQuoteSection, mDoubleQuoteSection));
                mToken = "";
                mSpannedCode.append(decideCharacterToken(mActualText.charAt(index),
                        mIncludeSection, mCommentType1Section, mCommentType2Section,
                        mSingleQuoteSection, mDoubleQuoteSection));
            } else {
                mToken = mToken + mActualText.charAt(index);
            }
            index++;
        }
        return mSpannedCode;
    }

    private SpannableString decideToken(String token, int langCode, boolean mIS, boolean mC1S,
                                        boolean mC2S, boolean mSQS, boolean mDQS) {
        int len = token.length();
        SpannableString temp = new SpannableString(token);
        if(mIS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_INCLUDE_STATEMENT)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mC1S || mC2S) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_COMMENT)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mSQS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_SINGLE_QUOTES)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mDQS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_DOUBLE_QUOTES)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            if(langCode == TYPE_C_LANGUAGE) {
                if(getCLanguageTokenType(token) == RESERVED_KEYWORD) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_RESERVED_KEYWORD)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if(getCLanguageTokenType(token) == LITERAL_VALUE_KEYWORD) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_LITERAL_VALUE_KEYWORD)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if(getCLanguageTokenType(token) == NUMERIC_LITERAL) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NUMERIC_LITERAL)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NORMAL_PRE_TAG)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if(langCode == TYPE_JAVA_LANGUAGE) {
                if(getJavaTokenType(token) == RESERVED_KEYWORD) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_RESERVED_KEYWORD)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if(getJavaTokenType(token) == LITERAL_VALUE_KEYWORD) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_LITERAL_VALUE_KEYWORD)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if(getJavaTokenType(token) == NUMERIC_LITERAL) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NUMERIC_LITERAL)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if(getJavaTokenType(token) == MOST_USED_CLASSES) {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_MOST_USED_CLASSES)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NORMAL_PRE_TAG)
                            , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NORMAL_PRE_TAG)
                        , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return temp;
    }

    private SpannableString decideCharacterToken(char character, boolean mIS, boolean mC1S,
                                                 boolean mC2S, boolean mSQS, boolean mDQS) {
        String token = "" + character;
        int len = 1;
        SpannableString temp = new SpannableString(token);
        if(mIS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_INCLUDE_STATEMENT)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mC1S || mC2S) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_COMMENT)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mSQS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_SINGLE_QUOTES)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if(mDQS) {
            temp.setSpan(new ForegroundColorSpan(Utils.COLOR_SECTION_DOUBLE_QUOTES)
                    , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            if(isBracketCharacter(character)) {
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_NORMAL_PRE_TAG)
                        , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if(isOperatorCharacter(character)) {
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_OPERATOR)
                        , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if(isDelimiterCharacter(character)) {
                temp.setSpan(new ForegroundColorSpan(Utils.COLOR_DELIMITER)
                        , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return temp;
    }

    private SpannableStringBuilder obtainSpannedTextForXML(String text) {
        String mActualText = "";
        int index = 0, length = text.length();
        boolean ignorable = false, ignoreEndtag = false;
        while(index < length) {
            if(checkTextAtPosition("<b>", text, index)
                    || checkTextAtPosition("<i>", text, index)
                    || checkTextAtPosition("<font", text, index)
                    || checkTextAtPosition("<span", text, index)
                    || checkTextAtPosition("</b>", text, index)
                    || checkTextAtPosition("</i>", text, index)
                    || checkTextAtPosition("</font>", text, index)
                    || checkTextAtPosition("</span>", text, index)) {
                ignorable = true;
            } else if(ignorable && text.charAt(index) == '>') {
                ignoreEndtag = true;
                ignorable = false;
            }
            if(!ignorable) {
                if(ignoreEndtag) {
                    ignoreEndtag = false;
                } else {
                    mActualText = mActualText + text.charAt(index);
                }
            }
            index++;
        }
        SpannableStringBuilder mSpannedCode = new SpannableStringBuilder("");
        index = 0;
        length = mActualText.length();
        int mTagStart = -1, mQuoteStart = -1;
        while(index < length) {
            char character = mActualText.charAt(index);
            mSpannedCode.append(character);
            if(mActualText.charAt(index) == '<') {
                mTagStart = index;
                mQuoteStart = -1;
            } else if(mActualText.charAt(index) == '>') {
                if (mTagStart >= 0) {
                    mSpannedCode.setSpan(new ForegroundColorSpan(
                            Utils.COLOR_XML_TAG), mTagStart
                            , index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mQuoteStart = -1;
                mTagStart = -1;
            } else if(mActualText.charAt(index) == '\"') {
                if(mTagStart != -1) {
                    if(mQuoteStart == -1) {
                        mQuoteStart = index;
                        mSpannedCode.setSpan(new ForegroundColorSpan(Utils.COLOR_XML_TAG),
                                mTagStart, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        mSpannedCode.setSpan(
                                new ForegroundColorSpan(Utils.COLOR_SECTION_DOUBLE_QUOTES),
                                mQuoteStart, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mQuoteStart = -1;
                        mTagStart = index + 1;
                    }
                }
            }
            index++;
        }
        return mSpannedCode;
    }

    private SpannableStringBuilder obtainDefaultSpannedText(String text) {
        text = text.replaceAll("&lt;","<")
                .replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"")
                .replaceAll("<b>","")
                .replaceAll("</b>","")
                .replaceAll("<i>","")
                .replaceAll("</i>","");
        SpannableStringBuilder mSpannedText = new SpannableStringBuilder(text);
        int len = mSpannedText.length();
        mSpannedText.setSpan(new ForegroundColorSpan(Utils.COLOR_NORMAL_PRE_TAG)
                , 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mSpannedText;
    }

}
