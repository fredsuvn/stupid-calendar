package com.cogician.quicker.util.lexer;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fred Suvn
 * @version 0.0.0, 2016-04-06T16:56:36+08:00
 * @since 0.0.0, 2016-04-06T16:56:36+08:00
 */
public class TestLexer {

    public static void main(String[] args) {
        Lexer parser = new RegularLexerBuilder().addToken(RegularLexToken.COMMENT)
                .addToken(RegularLexToken.LINE_COMMENT).addToken(RegularLexToken.BLOCK_COMMENT)
                .addToken(RegularLexToken.IDENTIFIER).addToken(RegularLexToken.SPACE).addToken(RegularLexToken.CRLF)
                .build();
        List<LexTetrad> list = parser.tokenize("/**\r\n\ras\ngdgds*/function\n abc //ddddd\rfsfafasf");
        list.forEach(l -> {
            System.out.println(l.toTetrad());
        });
    }

}
