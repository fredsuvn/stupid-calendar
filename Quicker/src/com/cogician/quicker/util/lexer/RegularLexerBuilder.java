package com.cogician.quicker.util.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cogician.quicker.Buildable;
import com.cogician.quicker.Checker;
import com.cogician.quicker.Quicker;
import com.cogician.quicker.util.ReferencedCharSequence;

/**
 * <p>
 * Regular builder of {@linkplain Lexer}. This builder will build a type of lexical analyzer which use regular
 * expression.
 * </p>
 * <p>
 * For this analyzer, each token is a {@linkplain RegularLexToken} which includes a pattern. Parser built by this
 * builder has a whole pattern combines all patterns of given token in adding order, each in a group with a group name.
 * When it starts to tokenize, it will try to match by the whole pattern and find which token matched by group name. The
 * prefix of group name can be set to avoid the case that there exist same group names between one of given token's
 * pattern and the whole pattern. Default the prefix is {@linkplain #GROUP_PREFIX}.
 * </p>
 *
 * @author Fred Suvn
 * @version 0.0.0, 2016-05-06T11:27:37+08:00
 * @since 0.0.0, 2016-05-06T11:27:37+08:00
 * @see Lexer
 */
public class RegularLexerBuilder implements Buildable<Lexer> {

    /**
     * <p>
     * Default prefix name of capture group: {@value #GROUP_PREFIX}.
     * </p>
     * 
     * @since 0.0.0
     */
    public static final String GROUP_PREFIX = "g";

    private final List<RegularLexToken> tokens = new ArrayList<>();

    private String groupPrefix = GROUP_PREFIX;

    /**
     * <p>
     * Constructs an empty instance.
     * </p>
     * 
     * @since 0.0.0
     */
    public RegularLexerBuilder() {

    }

    /**
     * <p>
     * Adds given token and returns this instance. Given token should non-contained in the added tokens, and its pattern
     * should be existing, or it will not be added.
     * </p>
     * 
     * @param token
     *            given token
     * @return this
     * @throws NullPointerException
     *             if given token is null
     * @since 0.0.0
     */
    public RegularLexerBuilder addToken(RegularLexToken token) throws NullPointerException {
        Checker.checkNull(token);
        if (token.hasPattern() && !tokens.contains(token)) {
            tokens.add(token);
        }
        return this;
    }

    /**
     * <p>
     * Adds given tokens in index order and returns this instance. Each token of Given tokens should non-contained in
     * the added tokens, and its pattern should be existing, or it will not be added.
     * </p>
     * 
     * @param tokens
     *            given tokens
     * @return this
     * @throws NullPointerException
     *             if given token array is null
     * @since 0.0.0
     */
    public RegularLexerBuilder addTokens(RegularLexToken... tokens) throws NullPointerException {
        Checker.checkNull(tokens);
        if (Checker.isNotEmpty(tokens)) {
            Quicker.each(tokens, t -> {
                addToken(t);
            });
        }
        return this;
    }

    /**
     * <p>
     * Adds given tokens in index order and returns this instance. Each token of Given tokens should non-contained in
     * the added tokens, and its pattern should be existing, or it will not be added.
     * </p>
     * 
     * @param tokens
     *            given tokens
     * @return this
     * @throws NullPointerException
     *             if given token list is null
     * @since 0.0.0
     */
    public RegularLexerBuilder addTokens(List<RegularLexToken> tokens) throws NullPointerException {
        Checker.checkNull(tokens);
        if (Checker.isNotEmpty(tokens)) {
            Quicker.each(tokens, t -> {
                addToken(t);
            });
        }
        return this;
    }

    /**
     * <p>
     * Inserts given token at specified index and returns this instance. Shifts tokens currently at specified index and
     * subsequence after it to right. Given token should non-contained in the added tokens, and its pattern should be
     * existing, or it will not be added.
     * </p>
     * 
     * @param index
     *            specified index in bounds
     * @param token
     *            given token
     * @return this
     * @throws NullPointerException
     *             if given token is null
     * @throws IllegalArgumentException
     *             if index out of bounds
     * @since 0.0.0
     */
    public RegularLexerBuilder insertToken(int index, RegularLexToken token)
            throws NullPointerException, IllegalArgumentException {
        Checker.checkNull(token);
        if (token.hasPattern()) {
            tokens.add(index, token);
        }
        return this;
    }

    /**
     * <p>
     * Inserts given tokens at specified index in index order and returns this instance. Shifts tokens currently at
     * specified index and subsequence after it to right. Each token of Given tokens should non-contained in the added
     * tokens, and its pattern should be existing, or it will not be added.
     * </p>
     * 
     * @param index
     *            specified index in bounds
     * @param tokens
     *            given tokens
     * @return this
     * @throws NullPointerException
     *             if given tokens is null
     * @throws IllegalArgumentException
     *             if index out of bounds
     * @since 0.0.0
     */
    public RegularLexerBuilder insertTokens(int index, RegularLexToken... tokens)
            throws NullPointerException, IllegalArgumentException {
        Checker.checkNull(tokens);
        if (Checker.isNotEmpty(tokens)) {
            int[] count = {0};
            Quicker.each(tokens, t -> {
                insertToken(count[0]++, t);
            });
        }
        return this;
    }

    /**
     * <p>
     * Inserts given tokens at specified index in index order and returns this instance. Shifts tokens currently at
     * specified index and subsequence after it to right. Each token of Given tokens should non-contained in the added
     * tokens, and its pattern should be existing, or it will not be added.
     * </p>
     * 
     * @param index
     *            specified index in bounds
     * @param tokens
     *            given tokens
     * @return this
     * @throws NullPointerException
     *             if given tokens is null
     * @throws IllegalArgumentException
     *             if index out of bounds
     * @since 0.0.0
     */
    public RegularLexerBuilder insertTokens(int index, List<RegularLexToken> tokens)
            throws NullPointerException, IllegalArgumentException {
        Checker.checkNull(tokens);
        if (Checker.isNotEmpty(tokens)) {
            int[] count = {0};
            Quicker.each(tokens, t -> {
                insertToken(count[0]++, t);
            });
        }
        return this;
    }

    /**
     * <p>
     * Removes first occurrence of given token. If there is no given token in this builder, do nothing.
     * </p>
     * 
     * @param token
     *            given token
     * @return this
     * @throws NullPointerException
     *             if given token is null
     * @since 0.0.0
     */
    public RegularLexerBuilder removeToken(RegularLexToken token) throws NullPointerException {
        Checker.checkNull(token);
        tokens.remove(token);
        return this;
    }

    /**
     * <p>
     * Sets prefix name of each capture group.
     * </p>
     * 
     * @param prefix
     *            prefix name of each capture group
     * @return this
     * @throws NullPointerException
     *             if given prefix name of each capture group is null
     * @since 0.0.0
     */
    public RegularLexerBuilder setGroupPrefixName(String prefix) throws NullPointerException {
        this.groupPrefix = Quicker.require(prefix);
        return this;
    }

    private Pattern createPattern() {
        int[] counter = {0};
        StringBuilder sb = new StringBuilder("^(");
        tokens.iterator().forEachRemaining(token -> {
            if (token.hasPattern()) {
                String pattern = token.getPattern().pattern();
                sb.append("(?<");
                sb.append(groupPrefix).append(counter[0]);
                sb.append(">");
                sb.append(pattern);
                sb.append(")|");
            }
            counter[0]++;
        });
        String regex = sb.delete(sb.length() - 1, sb.length()).append(")").toString();
        return Pattern.compile(regex);
    }

    /**
     * <p>
     * Builds a {@linkplain Lexer} according to current build status.
     * </p>
     * 
     * @return a {@linkplain Lexer} according to current build status
     * @since 0.0.0
     */
    @Override
    public Lexer build() {
        return new Lexer() {

            private final List<RegularLexToken> regularTokens = new ArrayList<>(tokens);

            private final Pattern pattern = createPattern();

            @Override
            public List<LexTetrad> tokenize(String input) throws NullPointerException, ParsingException {
                Checker.checkNull(input);
                List<LexTetrad> list = new ArrayList<>();
                CharSequence inputSequence = new ReferencedCharSequence(input);
                int cur = 0;
                int row = 1;
                int col = 1;
                while (true) {
                    CharSequence toBeMatched = inputSequence.subSequence(cur, input.length());
                    Matcher m = pattern.matcher(toBeMatched);
                    if (m.find()) {
                        int e = m.end();
                        String matched = toBeMatched.subSequence(0, e).toString();
                        LexToken token = null;
                        for (int i = 0; i < regularTokens.size(); i++) {
                            if (m.group(groupPrefix + i) != null) {
                                token = regularTokens.get(i);
                                break;
                            }
                        }
                        list.add(new LexTetrad(matched, token, row, col));
                        boolean isR = false;
                        for (int i = 0; i < matched.length(); i++) {
                            char ch = matched.charAt(i);
                            if (ch == '\r') {
                                isR = true;
                                row++;
                                col = 1;
                            } else if (ch == '\n') {
                                if (!isR) {
                                    row++;
                                    col = 1;
                                } else {
                                    isR = false;
                                }
                            } else {
                                col++;
                                isR = false;
                            }
                        }
                        cur += e;
                        if (cur == input.length()) {
                            break;
                        }
                    } else {
                        throw parsingException(generalFormat(toBeMatched.toString()), row, col);
                    }
                }
                return list;
            }

            private ParsingException parsingException(String illegal, int row, int col) {
                return new ParsingException("Tokenizing failed, Illegal characters: \"" + illegal + "\" at line " + row
                        + ", column " + col + ".");
            }

            private String generalFormat(String illegal) {
                return illegal.length() <= 3 ? illegal : illegal.substring(0, 3) + "...";
            }
        };
    }

}
