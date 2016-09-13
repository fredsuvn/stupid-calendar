package com.cogician.quicker.log;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import com.cogician.quicker.Buildable;
import com.cogician.quicker.Quicker;
import com.cogician.quicker.Uniforms;
import com.cogician.quicker.util.placeholder.PlaceholderResolver;
import com.cogician.quicker.util.placeholder.PlaceholderException;

/**
 * <p>
 * A terse and simple logger.
 * </p>
 * <p>
 * This class has 7 predefined level with int type, from high to low are:
 *
 * <pre>
 * OFF -> FATAL -> ERROR -> WARN -> INFO -> DEBUG -> ALL
 * </pre>
 * 
 * Using {@linkplain #log(int, String)} or {@linkplain #log(int, String, Object...)} can log in specified level without
 * predefined level.
 * </p>
 * <p>
 * This interface can built by {@linkplain Builder}. The builder will built an instance which output one
 * place. Using {@linkplain #multiLogger(List)} can get an instance which can output to more places.
 * </p>
 * <p>
 * This logger is thread-safe.
 * </p>
 *
 * @author Fred Suvn
 * @version 0.0.0, 2015-07-17 11:24:47
 * @since 0.0.0
 */
public interface QuickLogger {

    /**
     * <p>
     * OFF level, highest ({@linkplain Integer.MAX_VALUE}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int OFF = Integer.MAX_VALUE;

    /**
     * <p>
     * FATAL level ({@value #FATAL}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int FATAL = 50000;

    /**
     * <p>
     * ERROR level ({@value #ERROR}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int ERROR = 40000;

    /**
     * <p>
     * WARN level ({@value #WARN}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int WARN = 30000;

    /**
     * <p>
     * INFO level ({@value #INFO}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int INFO = 20000;

    /**
     * <p>
     * DEBUG level ({@value #DEBUG}).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int DEBUG = 10000;

    /**
     * <p>
     * ALL level, lowest (0).
     * </p>
     * 
     * @since 0.0.0
     */
    public static final int ALL = 0;

    /**
     * <p>
     * Returns a logger combines given logger list. Returned logger will output all places where given loggers output
     * according to each logger's config.
     * </p>
     * 
     * @param list
     *            given logger list
     * @return a logger combines given logger list
     * @since 0.0.0
     */
    public static QuickLogger multiLogger(List<QuickLogger> list) {
        return new QuickLogger() {

            private List<QuickLogger> loggers = Collections.unmodifiableList(list);

            @Override
            public void log(int level, Object obj) {
                Quicker.each(loggers, l -> {
                    l.log(level, obj);
                });
            }

            @Override
            public void log(int level, String msg) {
                Quicker.each(loggers, l -> {
                    l.log(level, msg);
                });
            }

            @Override
            public void log(int level, String msg, Object... args) throws IllegalFormatException {
                Quicker.each(loggers, l -> {
                    l.log(level, msg, args);
                });
            }
        };
    }

    /**
     * <p>
     * Logs in {@linkplain #FATAL} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void fatal(@Nullable String msg) {
        log(FATAL, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #FATAL} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void fatal(@Nullable Object obj) {
        log(FATAL, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #FATAL} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void fatal(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(FATAL, msg, args);
    }

    /**
     * <p>
     * Logs in {@linkplain #ERROR} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void error(@Nullable String msg) {
        log(ERROR, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #ERROR} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void error(@Nullable Object obj) {
        log(ERROR, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #ERROR} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void error(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(ERROR, msg, args);
    }

    /**
     * <p>
     * Logs in {@linkplain #WARN} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void warn(@Nullable String msg) {
        log(WARN, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #WARN} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void warn(@Nullable Object obj) {
        log(WARN, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #WARN} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void warn(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(WARN, msg, args);
    }

    /**
     * <p>
     * Logs in {@linkplain #INFO} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void info(@Nullable String msg) {
        log(INFO, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #INFO} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void info(@Nullable Object obj) {
        log(INFO, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #INFO} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void info(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(INFO, msg, args);
    }

    /**
     * <p>
     * Logs in {@linkplain #DEBUG} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void debug(String msg) {
        log(DEBUG, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #DEBUG} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void debug(@Nullable Object obj) {
        log(DEBUG, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #DEBUG} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void debug(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(DEBUG, msg, args);
    }

    /**
     * <p>
     * Logs in {@linkplain #ALL} level.
     * </p>
     * 
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    default void all(@Nullable String msg) {
        log(ALL, msg);
    }

    /**
     * <p>
     * Logs given object (using String.valueOf) in {@linkplain #ALL} level.
     * </p>
     * 
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    default void all(@Nullable Object obj) {
        log(ALL, obj);
    }

    /**
     * <p>
     * Logs formatted using given format message and arguments in {@linkplain #ALL} level. Using
     * {@linkplain #getLocale()}.
     * </p>
     * 
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    default void all(@Nullable String msg, @Nullable Object... args) throws IllegalFormatException {
        log(ALL, msg, args);
    }

    /**
     * <p>
     * Logs in specified level level.
     * </p>
     * 
     * @param level
     *            specified logging level
     * @param msg
     *            message to be logged
     * @since 0.0.0
     */
    public void log(int level, @Nullable String msg);

    /**
     * <p>
     * Logs given object (using String.valueOf) in specified level level.
     * </p>
     * 
     * @param level
     *            specified logging level
     * @param obj
     *            obj to be logged
     * @since 0.0.0
     */
    public void log(int level, @Nullable Object obj);

    /**
     * <p>
     * Logs formatted using given format message and arguments in specified level. Using {@linkplain #getLocale()}.
     * </p>
     * 
     * @param level
     *            specified logging level
     * @param msg
     *            given format message
     * @param args
     *            given format arguments
     * @throws IllegalFormatException
     *             if formatting failed
     * @since 0.0.0
     */
    public void log(int level, @Nullable String msg, @Nullable Object... args) throws IllegalFormatException;

    /**
     * <p>
     * Builder of {@linkplain QuickLogger}. Default configurations are uniformed or null. Log format must be set if
     * specified output is non-null.
     * </p>
     *
     * @author Fred Suvn
     * @version 0.0.0, 2016-08-19T12:06:09+08:00
     * @since 0.0.0, 2016-08-19T12:06:09+08:00
     */
    public static class Builder implements Buildable<QuickLogger> {

        private static final PlaceholderResolver resolver = PlaceholderResolver.defaultLogResolver();

        private Locale locale = Uniforms.LOCALE;

        private ZoneOffset offset = Uniforms.ZONE_OFFSET;

        private Charset charset = Uniforms.CHARSET;

        private int level = QuickLogger.OFF;

        private OutputStream out = null;

        private DateTimeFormatter datetimeFormatter = Uniforms.DATETIME_FORMATTER;

        private String format = null;

        private static String toLevelString(int level) {
            switch (level) {
                case QuickLogger.ALL:
                    return "ALL";
                case QuickLogger.DEBUG:
                    return "DEBUG";
                case QuickLogger.ERROR:
                    return "ERROR";
                case QuickLogger.FATAL:
                    return "FATAL";
                case QuickLogger.INFO:
                    return "INFO";
                case QuickLogger.OFF:
                    return "OFF";
                case QuickLogger.WARN:
                    return "WARN";
                default: {
                    String preffix;
                    if (level < QuickLogger.DEBUG) {
                        preffix = "ALL";
                    } else if (level < QuickLogger.INFO) {
                        preffix = "DEBUG";
                    } else if (level < QuickLogger.WARN) {
                        preffix = "INFO";
                    } else if (level < QuickLogger.ERROR) {
                        preffix = "WARN";
                    } else if (level < QuickLogger.FATAL) {
                        preffix = "ERROR";
                    } else {
                        preffix = "FATAL";
                    }
                    return preffix + level;
                }
            }
        }

        /**
         * <p>
         * Sets specified locale.
         * </p>
         * 
         * @param locale
         *            specified locale
         * @return this
         * @throws NullPointerException
         *             if specified locale is null
         * @since 0.0.0
         */
        public Builder setLocale(Locale locale) throws NullPointerException {
            this.locale = Quicker.require(locale);
            return this;
        }

        /**
         * <p>
         * Sets specified zone offset.
         * </p>
         * 
         * @param offset
         *            specified zone offset
         * @return this
         * @throws NullPointerException
         *             if specified zone offset is null
         * @since 0.0.0
         */
        public Builder setOffset(ZoneOffset offset) throws NullPointerException {
            this.offset = Quicker.require(offset);
            return this;
        }

        /**
         * <p>
         * Sets specified charset.
         * </p>
         * 
         * @param charset
         *            specified charset
         * @return this
         * @throws NullPointerException
         *             if specified charset is null
         * @since 0.0.0
         */
        public Builder setCharset(Charset charset) throws NullPointerException {
            this.charset = Quicker.require(charset);
            return this;
        }

        /**
         * <p>
         * Sets specified level.
         * </p>
         * 
         * @param level
         *            specified level
         * @return this
         * @throws NullPointerException
         *             if specified level is null
         * @since 0.0.0
         */
        public Builder setLevel(int level) throws NullPointerException {
            this.level = level;
            return this;
        }

        /**
         * <p>
         * Sets specified date time formatter.
         * </p>
         * 
         * @param datetimeFormatter
         *            specified date time formatter
         * @return this
         * @throws NullPointerException
         *             if specified date time formatter is null
         * @since 0.0.0
         */
        public Builder setDateTimeFormatter(DateTimeFormatter datetimeFormatter) throws NullPointerException {
            this.datetimeFormatter = Quicker.require(datetimeFormatter);
            return this;
        }

        /**
         * <p>
         * Sets specified output stream, maybe null if no output.
         * </p>
         * 
         * @param out
         *            specified output stream
         * @return this
         * @since 0.0.0
         */
        public Builder setOut(OutputStream out) {
            this.out = out;
            return this;
        }

        /**
         * <p>
         * Sets specified output format.
         * </p>
         * <p>
         * Log format support placeholder {@linkplain PlaceholderResolver#defaultLogResolver()}. Here are all meanings:
         * <table>
         * <th>Placeholder</th>
         * <th>Meaning</th>
         * <tr>
         * <td>%m</td>
         * <td>logged message</td>
         * </tr>
         * <tr>
         * <td>%p</td>
         * <td>log period, that is level</td>
         * </tr>
         * <tr>
         * <td>%d</td>
         * <td>datetime</td>
         * </tr>
         * <tr>
         * <td>%t</td>
         * <td>thread name</td>
         * </tr>
         * <tr>
         * <td>%l</td>
         * <td>position info where the logger writes</td>
         * </tr>
         * </table>
         * </p>
         * 
         * @param format
         *            specified output format
         * @return this
         * @throws NullPointerException
         *             if specified output format is null
         * @since 0.0.0
         */
        public Builder setFormat(String format) throws NullPointerException {
            this.format = Quicker.require(format);
            return this;
        }

        /**
         * <p>
         * Builds a {@linkplain QuickLogger}. Log format must be set if specified output is non-null.
         * </p>
         * 
         * @return a {@linkplain QuickLogger}
         * @throws IllegalArgumentException
         *             if log format is null but specified output is non-null
         * @since 0.0.0
         */
        @Override
        public QuickLogger build() throws IllegalArgumentException {
            if (null == format && out != null) {
                throw new IllegalArgumentException("Log format must be set if specified output is non-null.");
            }
            return new QuickLogger() {

                private Locale locale = Builder.this.locale;

                private ZoneOffset offset = Builder.this.offset;

                private Charset charset = Builder.this.charset;

                private int level = Builder.this.level;

                private OutputStream out = Builder.this.out;

                private DateTimeFormatter datetimeFormatter = Builder.this.datetimeFormatter;

                private String format = Builder.this.format;

                public void writeMessage(String level, String msg) {
                    if (this.level == OFF || null == out) {
                        return;
                    }
                    try {
                        Map<String, String> args = new HashMap<>();
                        args.put("m", msg);
                        args.put("p", level);
                        args.put("d", datetimeFormatter.format(OffsetDateTime.now(offset)));
                        args.put("t", Thread.currentThread().getName());
                        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                        if (stack.length > 0) {
                            int cur = stack.length - 1;
                            args.put("l", stack[cur].getClassName() + "." + stack[cur].getMethodName() + "(line: "
                                    + stack[cur].getLineNumber() + ")");
                        }
                        byte[] bs = (resolver.resolve(format, args) + Uniforms.LINE_SEPARATOR).getBytes(charset);
                        out.write(bs);
                        out.flush();
                    } catch (PlaceholderException p) {
                        byte[] bs = ("Logger format error: " + format + "." + Uniforms.LINE_SEPARATOR)
                                .getBytes(charset);
                        try {
                            out.write(bs);
                            out.flush();
                        } catch (Throwable io) {

                        }
                    } catch (Throwable t) {
                        // throw new WriteException(t);
                    }
                }

                @Override
                public void log(int level, String msg) throws IllegalFormatException {
                    if (level >= this.level) {
                        writeMessage(Builder.toLevelString(level), msg);
                    }
                }

                @Override
                public void log(int level, Object obj) {
                    if (level >= this.level) {
                        writeMessage(Builder.toLevelString(level), String.valueOf(obj));
                    }
                }

                @Override
                public void log(int level, String msg, @Nullable Object... args) {
                    if (level >= this.level) {
                        writeMessage(Builder.toLevelString(level), String.format(this.locale, msg, args));
                    }
                }
            };
        }
    }
}
