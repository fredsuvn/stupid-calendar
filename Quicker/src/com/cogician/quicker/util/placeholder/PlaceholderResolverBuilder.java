package com.cogician.quicker.util.placeholder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntPredicate;

import javax.annotation.Nullable;

import com.cogician.quicker.Buildable;
import com.cogician.quicker.Checker;
import com.cogician.quicker.Quicker;
import com.cogician.quicker.util.lexer.LexToken;

/**
 * <p>
 * Default builder of {@linkplain PlaceholderResolver}. Placeholder resolver built by this builder is default resolver.
 * </p>
 * <p>
 * This builder support two-tokens and single token placeholder. If suffix of placeholder is set to null, placeholder
 * will be considered as single token placeholder. Single placeholder doesn't support nesting resolving.
 * </p>
 * <p>
 * For delimiters, delimiters should be set in pairs. Any one of delimiter is set to null, both will be set to null if
 * {@linkplain #build()} called.
 * </p>
 * <p>
 * There are some policy for if specified placeholder is not found. See {@linkplain NotFoundPolicy}. Default is
 * {@linkplain NotFoundPolicy#IGNORE}.
 * </p>
 * <p>
 * Initial settings of a new builder are place prefix "${", place suffix "}", delimiter prefix "<[", delimiter suffix
 * "]>", determine "?", escape "\". Any two tokens setting of all cannot be equal except null.
 * </p>
 * <p>
 * Note a legal param name only consists of numbers and letters.
 * </p>
 *
 * @author Fred Suvn
 * @version 0.0.0, 2016-08-03T15:48:56+08:00
 * @since 0.0.0, 2016-08-03T15:48:56+08:00
 * @see PlaceholderResolver
 * @see NotFoundPolicy
 */
public class PlaceholderResolverBuilder implements Buildable<PlaceholderResolver> {

    static final PlaceholderResolverBuilder DEFAULT_RESOLVER_BUILDER = new PlaceholderResolverBuilder()
            .setPlacePrefix("${").setPlaceSuffix("}").setDelimiterPrefix("<[").setDelimiterSuffix("]>")
            .setDetermine("?").setEscape("\\");

    static final PlaceholderResolver DEFAULT_RESOLVER = DEFAULT_RESOLVER_BUILDER.build();

    static final PlaceholderResolverBuilder DEFAULT_LOG_RESOLVER_BUILDER = new PlaceholderResolverBuilder()
            .setPlacePrefix("%").setPlaceSuffix(null).setDelimiterPrefix("<[").setDelimiterSuffix("]>")
            .setDetermine("?").setEscape("\\");

    static final PlaceholderResolver DEFAULT_LOG_RESOLVER = DEFAULT_LOG_RESOLVER_BUILDER.build();

    static final PlaceholderResolverBuilder DEFAULT_SQL_RESOLVER_BUILDER = new PlaceholderResolverBuilder()
            .setPlacePrefix(":").setPlaceSuffix(null).setDelimiterPrefix("<[").setDelimiterSuffix("]>")
            .setDetermine("?").setEscape("\\");

    static final PlaceholderResolver DEFAULT_SQL_RESOLVER = DEFAULT_SQL_RESOLVER_BUILDER.build();

    private String placePrefix = "${";

    private @Nullable String placeSuffix = "}";

    private @Nullable String delimiterPrefix = "<[";

    private @Nullable String delimiterSuffix = "]>";

    private @Nullable String determine = "?";

    private @Nullable String escape = "\\";

    private NotFoundPolicy notFoundPolicy = NotFoundPolicy.IGNORE;

    private @Nullable Map<String, ? extends Object> valueMap;

    /**
     * <p>
     * Constructs an empty builder.
     * </p>
     * 
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder() {

    }

    /**
     * <p>
     * Sets placeholder prefix.
     * </p>
     * 
     * @param placePrefix
     *            specified placeholder prefix
     * @return this
     * @throws NullPointerException
     *             if specified placeholder prefix is null
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setPlacePrefix(String placePrefix) throws NullPointerException {
        this.placePrefix = Quicker.require(placePrefix);
        return this;
    }

    /**
     * <p>
     * Sets placeholder suffix.
     * </p>
     * 
     * @param placeSuffix
     *            specified placeholder suffix
     * @return this
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setPlaceSuffix(@Nullable String placeSuffix) {
        this.placeSuffix = placeSuffix;
        return this;
    }

    /**
     * <p>
     * Sets delimiter preffix.
     * </p>
     * 
     * @param delimiterPrefix
     *            specified delimiter preffix
     * @return this
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setDelimiterPrefix(@Nullable String delimiterPrefix) {
        this.delimiterPrefix = delimiterPrefix;
        return this;
    }

    /**
     * <p>
     * Sets delimiter suffix.
     * </p>
     * 
     * @param delimiterSuffix
     *            specified delimiter suffix
     * @return this
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setDelimiterSuffix(@Nullable String delimiterSuffix) {
        this.delimiterSuffix = delimiterSuffix;
        return this;
    }

    /**
     * <p>
     * Sets determine.
     * </p>
     * 
     * @param determine
     *            specified determine
     * @return this
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setDetermine(@Nullable String determine) {
        this.determine = determine;
        return this;
    }

    /**
     * <p>
     * Sets escape.
     * </p>
     * 
     * @param escape
     *            specified escape
     * @return this
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setEscape(@Nullable String escape) {
        this.escape = escape;
        return this;
    }

    /**
     * <p>
     * Sets policy to deal with if specified value is not found.
     * </p>
     * 
     * @param notFoundPolicy
     *            specified policy
     * @return this
     * @throws NullPointerException
     *             if specified policy is null
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setNotFoundPolicy(NotFoundPolicy notFoundPolicy) throws NullPointerException {
        this.notFoundPolicy = Quicker.require(notFoundPolicy);
        return this;
    }

    /**
     * <p>
     * Sets value map.
     * </p>
     * 
     * @param valueMap
     *            specified value map
     * @return this
     * @throws NullPointerException
     *             if specified value map is null
     * @since 0.0.0
     */
    public PlaceholderResolverBuilder setValueMap(Map<String, ? extends Object> valueMap) {
        this.valueMap = valueMap;
        return this;
    }

    /**
     * <p>
     * Builds a {@linkplain PlaceholderResolver} with current setting.
     * </p>
     * 
     * @return a {@linkplain PlaceholderResolver} with current setting
     * @throws IllegalArgumentException
     *             if any two setting are equal except null
     * @since 0.0.0
     */
    @Override
    public PlaceholderResolver build() throws IllegalArgumentException {
        if (delimiterPrefix == null || delimiterSuffix == null) {
            delimiterPrefix = null;
            delimiterSuffix = null;
            if (placeSuffix == null) {
                Checker.isEqual(placePrefix, determine, escape);
            } else {
                Checker.isEqual(placePrefix, placeSuffix, determine, escape);
            }
        } else {
            if (placeSuffix == null) {
                Checker.isEqual(placePrefix, delimiterPrefix, delimiterSuffix, determine, escape);
            } else {
                Checker.isEqual(placePrefix, placeSuffix, delimiterPrefix, delimiterSuffix, determine, escape);
            }
        }

        return new DefaultPlaceholderResolver(placePrefix, placeSuffix, delimiterPrefix, delimiterSuffix, determine,
                escape, notFoundPolicy, valueMap);
    }

    /**
     * <p>
     * Policy when corresponding value of a placeholder is not found.
     * </p>
     *
     * @author Fred Suvn
     * @version 0.0.0, 2016-08-03T17:19:01+08:00
     * @since 0.0.0, 2016-08-03T17:19:01+08:00
     */
    public static enum NotFoundPolicy {

        /**
         * <p>
         * Policy throws exception directly.
         * </p>
         * 
         * @since 0.0.0
         */
        THROW_EXCEPTION,

        /**
         * <p>
         * Policy ignores whole text between current delimiters which placeholder in. Note a non-found placeholder can
         * be ignored when it in a pair of delimiters, or an exception will be thrown.
         * </p>
         * 
         * @since 0.0.0
         */
        IGNORE,

        /**
         * <p>
         * Policy considers as "null" string.
         * </p>
         * 
         * @since 0.0.0
         */
        AS_NULL,

        /**
         * <p>
         * Policy outputs the string literally.
         * </p>
         * 
         * @since 0.0.0
         */
        AS_LITERAL,

        /**
         * <p>
         * Policy considers as an empty string.
         * </p>
         * 
         * @since 0.0.0
         */
        AS_EMPTY
    }

    static class DefaultPlaceholderResolver implements PlaceholderResolver {

        private static final LexToken PLACE_PREFIX = new LexToken("PLACE_PREFIX");

        private static final LexToken DELIMITER_PREFIX = new LexToken("DELIMITER_PREFIX");

        private static final LexToken DELIMITER_DELETE = new LexToken("DELIMITER_DELETE");

        private static final String AFFIX_NOT_MATCHEDED = "Affix of spaceholder or delimiter are not matched.";

        private static final String NO_MATCHED_VALUE = "No matched value.";

        private static final String THROW_FLAG = "THROW_FLAG";

        private static final int HOLDER_PLC_PREFIX = 0;

        private static final int HOLDER_PLC_SUFFIX = 1;

        private static final int HOLDER_DEL_PREFIX = 2;

        private static final int HOLDER_DEL_SUFFIX = 3;

        private static final int HOLDER_DET = 4;

        private static final int HOLDER_ESC = 5;

        private final String[] holders = {null, null, null, null, null, null};

        private final NotFoundPolicy notFoundPolicy;

        private final Map<String, ? extends Object> valueMap;

        DefaultPlaceholderResolver(String placePrefix, String placeSuffix, String delimiterPrefix,
                String delimiterSuffix, String determine, String escape, NotFoundPolicy notFoundPolicy,
                Map<String, ? extends Object> replacedMap) {
            holders[HOLDER_PLC_PREFIX] = placePrefix;
            holders[HOLDER_PLC_SUFFIX] = placeSuffix;
            holders[HOLDER_DEL_PREFIX] = delimiterPrefix;
            holders[HOLDER_DEL_SUFFIX] = delimiterSuffix;
            holders[HOLDER_DET] = determine;
            holders[HOLDER_ESC] = escape;
            this.notFoundPolicy = notFoundPolicy;
            this.valueMap = replacedMap;
        }

        @Override
        public String resolve(String input)
                throws NullPointerException, PlaceholderException, UnsupportedOperationException {
            return resolveDetail(input).getResultString();
        }

        @Override
        public String resolve(String input, Object... args) throws NullPointerException, PlaceholderException {
            return resolveDetail(input, args).getResultString();
        }

        @Override
        public String resolve(String input, Map<String, ? extends Object> replacedMap)
                throws NullPointerException, PlaceholderException {
            return resolveDetail(input, replacedMap).getResultString();
        }

        @Override
        public Result resolveDetail(String input)
                throws NullPointerException, PlaceholderException, UnsupportedOperationException {
            if (valueMap == null) {
                throw new UnsupportedOperationException("Value map is null.");
            }
            return resolveDetail(input, valueMap);
        }

        @Override
        public Result resolveDetail(String input, Object... args)
                throws NullPointerException, PlaceholderException {
            return resolve(input, name -> getObject(name, args), i -> Character.isDigit((char)i));
        }

        @Override
        public Result resolveDetail(String input, Map<String, ? extends Object> valueMap)
                throws NullPointerException, PlaceholderException {
            return resolve(input, name -> getObject(name, valueMap),
                    i -> Character.isDigit((char)i) || Character.isLetter((char)i));
        }

        private Result resolve(String input, Function<String, Object> getObject,
                IntPredicate extNamePredicate) {
            if (isNoPlaceholder(Quicker.require(input))) {
                return new DefaultResult(input, Collections.emptyList());
            }
            StringBuilder sb = new StringBuilder();
            Deque<Object> stack = new LinkedList<>();
            List<Object> usedArguments = new ArrayList<>();
            int subStart = 0;
            int inPlaceholder = 0;
            int placeholderCount = 0;
            int offset = 0;
            for (; offset < input.length();) {
                if (startWith(input, offset, holders[HOLDER_ESC])) {
                    stack.addLast(input.substring(subStart, offset));
                    offset = addEscaped(input, offset + holders[HOLDER_ESC].length(), stack);
                    subStart = offset;
                } else if (startWith(input, offset, holders[HOLDER_PLC_PREFIX])) {
                    placeholderCount++;
                    inPlaceholder++;
                    stack.addLast(input.substring(subStart, offset));
                    if (holders[HOLDER_PLC_SUFFIX] != null) {
                        stack.addLast(PLACE_PREFIX);
                        offset = offset + holders[HOLDER_PLC_PREFIX].length();
                        subStart = offset;
                    } else {
                        int nameStart = offset + holders[HOLDER_PLC_PREFIX].length();
                        int nameEnd = 0;
                        boolean isDetermine = false;
                        for (int i = nameStart; i < input.length(); i++) {
                            int reservedNum = NOT_RESERVED;
                            if ((reservedNum = getReserved(input, i)) >= 0
                                    || !extNamePredicate.test((char)input.charAt(i))) {
                                nameEnd = i;
                                if (reservedNum == HOLDER_DET) {
                                    isDetermine = true;
                                }
                                break;
                            }
                        }
                        if (nameEnd == 0) {
                            nameEnd = input.length();
                        }
                        String name = input.substring(nameStart, nameEnd);
                        if (Checker.isEmpty(name)) {
                            name = Integer.toString(placeholderCount - 1);
                        }
                        Object value = getObject.apply(name.toString());
                        if (value == THROW_FLAG) {
                            throw new PlaceholderException(NO_MATCHED_VALUE, input, offset);
                        } else if (value == null) {
                            Object p = stack.pollLast();
                            while (p != DELIMITER_PREFIX) {
                                if (p == null) {
                                    throw new PlaceholderException(NO_MATCHED_VALUE, input, offset);
                                }
                                p = stack.pollLast();
                            }
                            stack.addLast(DELIMITER_DELETE);
                            int schIndex = offset;
                            for (; schIndex < input.length();) {
                                if (startWith(input, schIndex, holders[HOLDER_ESC])) {
                                    schIndex = addEscaped(input, schIndex + holders[HOLDER_ESC].length());
                                } else if (startWith(input, schIndex, holders[HOLDER_PLC_PREFIX])) {
                                    schIndex += holders[HOLDER_PLC_PREFIX].length();
                                } else if (startWith(input, schIndex, holders[HOLDER_DEL_PREFIX])) {
                                    stack.addLast(DELIMITER_PREFIX);
                                    schIndex += holders[HOLDER_DEL_PREFIX].length();
                                } else if (startWith(input, schIndex, holders[HOLDER_DEL_SUFFIX])) {
                                    p = stack.peekLast();
                                    if (p == DELIMITER_PREFIX) {
                                        stack.pollLast();
                                    } else if (p == DELIMITER_DELETE) {
                                        stack.pollLast();
                                        offset = schIndex + holders[HOLDER_DEL_SUFFIX].length();
                                        subStart = offset;
                                        break;
                                    }
                                    schIndex += holders[HOLDER_DEL_SUFFIX].length();
                                } else {
                                    schIndex++;
                                }
                            }
                            if (schIndex >= input.length()) {
                                throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, offset);
                            }
                        } else {
                            if (!isDetermine) {
                                stack.addLast(value);
                                usedArguments.add(value);
                                offset = nameEnd;
                            } else {
                                offset = nameEnd + holders[HOLDER_DET].length();
                            }
                            subStart = offset;
                        }
                        inPlaceholder--;
                    }
                } else if (startWith(input, offset, holders[HOLDER_PLC_SUFFIX])) {
                    inPlaceholder--;
                    StringBuilder name = new StringBuilder();
                    name.insert(0, input.substring(subStart, offset));
                    Object p = stack.pollLast();
                    while (p != PLACE_PREFIX) {
                        if (p == null || p == DELIMITER_PREFIX) {
                            throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, offset);
                        }
                        name.insert(0, p);
                        p = stack.pollLast();
                    }
                    if (Checker.isEmpty(name)) {
                        name.append(placeholderCount - 1);
                    }
                    boolean isDetermine = startWith(input, offset + holders[HOLDER_PLC_SUFFIX].length(),
                            holders[HOLDER_DET]);
                    Object value = getObject.apply(name.toString());
                    if (value == THROW_FLAG) {
                        throw new PlaceholderException(NO_MATCHED_VALUE, input, offset);
                    } else if (value == null) {
                        int meetPre = 0;
                        p = stack.pollLast();
                        while (p != DELIMITER_PREFIX) {
                            if (p == null) {
                                throw new PlaceholderException(NO_MATCHED_VALUE, input, offset);
                            }
                            if (p == PLACE_PREFIX) {
                                meetPre++;
                            }
                            p = stack.pollLast();
                        }
                        stack.addLast(DELIMITER_DELETE);
                        int schIndex = offset + holders[HOLDER_PLC_SUFFIX].length();
                        for (; schIndex < input.length();) {
                            if (startWith(input, schIndex, holders[HOLDER_ESC])) {
                                schIndex = addEscaped(input, schIndex + holders[HOLDER_ESC].length());
                            } else if (startWith(input, schIndex, holders[HOLDER_PLC_PREFIX])) {
                                stack.addLast(PLACE_PREFIX);
                                schIndex += holders[HOLDER_PLC_PREFIX].length();
                            } else if (startWith(input, schIndex, holders[HOLDER_DEL_PREFIX])) {
                                stack.addLast(DELIMITER_PREFIX);
                                schIndex += holders[HOLDER_DEL_PREFIX].length();
                            } else if (startWith(input, schIndex, holders[HOLDER_PLC_SUFFIX])) {
                                p = stack.peekLast();
                                if (p == PLACE_PREFIX) {
                                    stack.pollLast();
                                } else if (p == DELIMITER_PREFIX) {
                                    throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, schIndex);
                                } else if (p == DELIMITER_DELETE) {
                                    if (meetPre > 0) {
                                        meetPre--;
                                    } else {
                                        throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, schIndex);
                                    }
                                }
                                schIndex += holders[HOLDER_PLC_SUFFIX].length();
                            } else if (startWith(input, schIndex, holders[HOLDER_DEL_SUFFIX])) {
                                p = stack.peekLast();
                                if (p == DELIMITER_PREFIX) {
                                    stack.pollLast();
                                } else if (p == PLACE_PREFIX) {
                                    throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, schIndex);
                                } else if (p == DELIMITER_DELETE) {
                                    if (meetPre == 0) {
                                        stack.pollLast();
                                        offset = schIndex + holders[HOLDER_DEL_SUFFIX].length();
                                        subStart = offset;
                                        break;
                                    } else {
                                        throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, schIndex);
                                    }
                                }
                                schIndex += holders[HOLDER_DEL_SUFFIX].length();
                            } else {
                                schIndex++;
                            }
                        }
                        if (schIndex >= input.length()) {
                            throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, offset);
                        }
                    } else {
                        if (!isDetermine) {
                            stack.addLast(value);
                            usedArguments.add(value);
                            offset = offset + holders[HOLDER_PLC_SUFFIX].length();
                        } else {
                            offset = offset + holders[HOLDER_PLC_SUFFIX].length() + holders[HOLDER_DET].length();
                        }
                        subStart = offset;
                    }
                } else if (startWith(input, offset, holders[HOLDER_DEL_PREFIX])) {
                    stack.addLast(input.substring(subStart, offset));
                    stack.addLast(DELIMITER_PREFIX);
                    offset = offset + holders[HOLDER_DEL_PREFIX].length();
                    subStart = offset;
                } else if (startWith(input, offset, holders[HOLDER_DEL_SUFFIX])) {
                    StringBuilder str = new StringBuilder();
                    Object p = stack.pollLast();
                    while (p != DELIMITER_PREFIX) {
                        if (p == null || p == PLACE_PREFIX) {
                            throw new PlaceholderException(AFFIX_NOT_MATCHEDED, input, offset);
                        }
                        str.insert(0, p);
                        p = stack.pollLast();
                    }
                    str.append(input.substring(subStart, offset));
                    stack.addLast(str.toString());
                    offset = offset + holders[HOLDER_DEL_SUFFIX].length();
                    subStart = offset;
                } else if (startWith(input, offset, holders[HOLDER_DET])) {
                    throw new PlaceholderException("Determine token must follow a placeholder.", input, offset);
                } else {
                    if (inPlaceholder != 0 && !extNamePredicate.test(input.charAt(offset))) {
                        throw new PlaceholderException(
                                "Illegal character of placeholder name: " + input.charAt(offset) + ".", input, offset);

                    }
                    offset++;
                }
            }
            Object str = null;
            while ((str = stack.pollFirst()) != null) {
                sb.append(str);
            }
            if (subStart < input.length()) {
                sb.append(input.substring(subStart, input.length()));
            }
            return new DefaultResult(sb.toString(), Collections.unmodifiableList(usedArguments));
        }

        private boolean isNoPlaceholder(String input) {
            for (int i = 0; i < holders.length; i++) {
                if (input.indexOf(holders[i]) != -1) {
                    return false;
                }
            }
            return true;
        }

        private boolean startWith(String input, int offset, String prefix) {
            return prefix != null && input.startsWith(prefix, offset);
        }

        private int addEscaped(String input, int offset, Deque<Object> stack) {
            for (int i = 0; i < holders.length; i++) {
                if (startWith(input, offset, holders[i])) {
                    stack.addLast(holders[i]);
                    return offset + holders[i].length();
                }
            }
            stack.addLast(input.charAt(offset));
            return offset + 1;
        }

        private int addEscaped(String input, int offset) {
            for (int i = 0; i < holders.length; i++) {
                if (startWith(input, offset, holders[i])) {
                    return offset + holders[i].length();
                }
            }
            return offset + 1;
        }

        private static final int WHITE_SPACE = 10086;

        private static final int NOT_RESERVED = -1;

        private int getReserved(String input, int offset) {
            if (Character.isWhitespace(input.charAt(offset))) {
                return WHITE_SPACE;
            }
            for (int i = 0; i < holders.length; i++) {
                if (startWith(input, offset, holders[i])) {
                    return i;
                }
            }
            return NOT_RESERVED;
        }

        private Object getObject(String num, @Nullable Object... args) {
            Object value = null;
            if (args != null) {
                int index = Integer.parseInt(num);
                if (index >= 0 && index < args.length) {
                    value = args[index];
                }
            }
            if (value == null) {
                switch (notFoundPolicy) {
                    case THROW_EXCEPTION: {
                        return THROW_FLAG;
                    }
                    case AS_EMPTY: {
                        return "";
                    }
                    case AS_LITERAL: {
                        return num;
                    }
                    case AS_NULL: {
                        return "null";
                    }
                    case IGNORE: {
                        return null;
                    }
                    default: {
                        return null;
                    }
                }
            } else {
                return value;
            }
        }

        private Object getObject(String name, @Nullable Map<String, ? extends Object> map) {
            Object value = map == null ? null : map.get(name);
            if (value == null) {
                switch (notFoundPolicy) {
                    case THROW_EXCEPTION: {
                        return THROW_FLAG;
                    }
                    case AS_EMPTY: {
                        return "";
                    }
                    case AS_LITERAL: {
                        return name;
                    }
                    case AS_NULL: {
                        return "null";
                    }
                    case IGNORE: {
                        return null;
                    }
                    default: {
                        return null;
                    }
                }
            } else {
                return value;
            }
        }
    }

    private static class DefaultResult implements PlaceholderResolver.Result {

        private final String resultString;

        private final List<Object> usedArguments;

        private DefaultResult(String resultString, List<Object> usedArguments) {
            this.resultString = resultString;
            this.usedArguments = usedArguments;
        }

        @Override
        public String getResultString() {
            return resultString;
        }

        @Override
        public List<Object> getUsedArguments() {
            return usedArguments;
        }

    }
}
