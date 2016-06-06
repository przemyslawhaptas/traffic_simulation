package osm_processer;// $ANTLR 3.3 Nov 30, 2010 12:50:56 OSMLexer.g 2016-06-02 21:00:45

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class OSMLexer extends Lexer {
    public static final int EOF=-1;
    public static final int TAG_DECLARATION_OPEN=4;
    public static final int TAG_DECLARATION_CLOSE=5;
    public static final int TAG_START_OPEN=6;
    public static final int TAG_END_OPEN=7;
    public static final int TAG_CLOSE=8;
    public static final int TAG_EMPTY_CLOSE=9;
    public static final int ATTR_EQ=10;
    public static final int ATTR_VALUE=11;
    public static final int PCDATA=12;
    public static final int LETTER=13;
    public static final int NAMECHAR=14;
    public static final int GENERIC_ID=15;
    public static final int DIGIT=16;
    public static final int WS=17;

        boolean tagMode = false;


    // delegates
    // delegators

    public OSMLexer() {;} 
    public OSMLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public OSMLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "OSMLexer.g"; }

    // $ANTLR start "TAG_DECLARATION_OPEN"
    public final void mTAG_DECLARATION_OPEN() throws RecognitionException {
        try {
            int _type = TAG_DECLARATION_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:7:22: ( '<?' )
            // OSMLexer.g:7:24: '<?'
            {
            match("<?"); 

             tagMode = true; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_DECLARATION_OPEN"

    // $ANTLR start "TAG_DECLARATION_CLOSE"
    public final void mTAG_DECLARATION_CLOSE() throws RecognitionException {
        try {
            int _type = TAG_DECLARATION_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:8:23: ({...}? => '?>\\n' )
            // OSMLexer.g:8:25: {...}? => '?>\\n'
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "TAG_DECLARATION_CLOSE", " tagMode ");
            }
            match("?>\n"); 

             tagMode = false; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_DECLARATION_CLOSE"

    // $ANTLR start "TAG_START_OPEN"
    public final void mTAG_START_OPEN() throws RecognitionException {
        try {
            int _type = TAG_START_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:9:16: ( '<' )
            // OSMLexer.g:9:18: '<'
            {
            match('<'); 
             tagMode = true; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_START_OPEN"

    // $ANTLR start "TAG_END_OPEN"
    public final void mTAG_END_OPEN() throws RecognitionException {
        try {
            int _type = TAG_END_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:10:14: ( '</' )
            // OSMLexer.g:10:16: '</'
            {
            match("</"); 

             tagMode = true; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_END_OPEN"

    // $ANTLR start "TAG_CLOSE"
    public final void mTAG_CLOSE() throws RecognitionException {
        try {
            int _type = TAG_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:11:11: ({...}? => '>' )
            // OSMLexer.g:11:13: {...}? => '>'
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "TAG_CLOSE", " tagMode ");
            }
            match('>'); 
             tagMode = false; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_CLOSE"

    // $ANTLR start "TAG_EMPTY_CLOSE"
    public final void mTAG_EMPTY_CLOSE() throws RecognitionException {
        try {
            int _type = TAG_EMPTY_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:12:17: ({...}? => '/>' )
            // OSMLexer.g:12:19: {...}? => '/>'
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "TAG_EMPTY_CLOSE", " tagMode ");
            }
            match("/>"); 

             tagMode = false; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TAG_EMPTY_CLOSE"

    // $ANTLR start "ATTR_EQ"
    public final void mATTR_EQ() throws RecognitionException {
        try {
            int _type = ATTR_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:14:9: ({...}? => '=' )
            // OSMLexer.g:14:11: {...}? => '='
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "ATTR_EQ", " tagMode ");
            }
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ATTR_EQ"

    // $ANTLR start "ATTR_VALUE"
    public final void mATTR_VALUE() throws RecognitionException {
        try {
            int _type = ATTR_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:16:12: ({...}? => ( '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' ) )
            // OSMLexer.g:16:14: {...}? => ( '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' )
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "ATTR_VALUE", " tagMode ");
            }
            // OSMLexer.g:17:9: ( '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\"') ) {
                alt3=1;
            }
            else if ( (LA3_0=='\'') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // OSMLexer.g:17:11: '\"' (~ '\"' )* '\"'
                    {
                    match('\"'); 
                    // OSMLexer.g:17:15: (~ '\"' )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // OSMLexer.g:17:16: ~ '\"'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // OSMLexer.g:18:11: '\\'' (~ '\\'' )* '\\''
                    {
                    match('\''); 
                    // OSMLexer.g:18:16: (~ '\\'' )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='\u0000' && LA2_0<='&')||(LA2_0>='(' && LA2_0<='\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // OSMLexer.g:18:17: ~ '\\''
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ATTR_VALUE"

    // $ANTLR start "PCDATA"
    public final void mPCDATA() throws RecognitionException {
        try {
            int _type = PCDATA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:22:8: ({...}? => (~ '<' )+ )
            // OSMLexer.g:22:10: {...}? => (~ '<' )+
            {
            if ( !(( !tagMode )) ) {
                throw new FailedPredicateException(input, "PCDATA", " !tagMode ");
            }
            // OSMLexer.g:22:26: (~ '<' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\u0000' && LA4_0<=';')||(LA4_0>='=' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // OSMLexer.g:22:27: ~ '<'
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<=';')||(input.LA(1)>='=' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PCDATA"

    // $ANTLR start "GENERIC_ID"
    public final void mGENERIC_ID() throws RecognitionException {
        try {
            int _type = GENERIC_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:25:5: ({...}? => ( LETTER | '_' | ':' ) ( NAMECHAR )* )
            // OSMLexer.g:25:7: {...}? => ( LETTER | '_' | ':' ) ( NAMECHAR )*
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "GENERIC_ID", " tagMode ");
            }
            if ( input.LA(1)==':'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // OSMLexer.g:26:29: ( NAMECHAR )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='-' && LA5_0<='.')||(LA5_0>='0' && LA5_0<=':')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // OSMLexer.g:26:30: NAMECHAR
            	    {
            	    mNAMECHAR(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GENERIC_ID"

    // $ANTLR start "NAMECHAR"
    public final void mNAMECHAR() throws RecognitionException {
        try {
            // OSMLexer.g:30:5: ( LETTER | DIGIT | '.' | '-' | '_' | ':' )
            // OSMLexer.g:
            {
            if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<=':')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NAMECHAR"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // OSMLexer.g:34:5: ( '0' .. '9' )
            // OSMLexer.g:34:10: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // OSMLexer.g:38:5: ( 'a' .. 'z' | 'A' .. 'Z' )
            // OSMLexer.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OSMLexer.g:42:5: ({...}? => ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // OSMLexer.g:42:8: {...}? => ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( !(( tagMode )) ) {
                throw new FailedPredicateException(input, "WS", " tagMode ");
            }
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // OSMLexer.g:1:8: ( TAG_DECLARATION_OPEN | TAG_DECLARATION_CLOSE | TAG_START_OPEN | TAG_END_OPEN | TAG_CLOSE | TAG_EMPTY_CLOSE | ATTR_EQ | ATTR_VALUE | PCDATA | GENERIC_ID | WS )
        int alt6=11;
        alt6 = dfa6.predict(input);
        switch (alt6) {
            case 1 :
                // OSMLexer.g:1:10: TAG_DECLARATION_OPEN
                {
                mTAG_DECLARATION_OPEN(); 

                }
                break;
            case 2 :
                // OSMLexer.g:1:31: TAG_DECLARATION_CLOSE
                {
                mTAG_DECLARATION_CLOSE(); 

                }
                break;
            case 3 :
                // OSMLexer.g:1:53: TAG_START_OPEN
                {
                mTAG_START_OPEN(); 

                }
                break;
            case 4 :
                // OSMLexer.g:1:68: TAG_END_OPEN
                {
                mTAG_END_OPEN(); 

                }
                break;
            case 5 :
                // OSMLexer.g:1:81: TAG_CLOSE
                {
                mTAG_CLOSE(); 

                }
                break;
            case 6 :
                // OSMLexer.g:1:91: TAG_EMPTY_CLOSE
                {
                mTAG_EMPTY_CLOSE(); 

                }
                break;
            case 7 :
                // OSMLexer.g:1:107: ATTR_EQ
                {
                mATTR_EQ(); 

                }
                break;
            case 8 :
                // OSMLexer.g:1:115: ATTR_VALUE
                {
                mATTR_VALUE(); 

                }
                break;
            case 9 :
                // OSMLexer.g:1:126: PCDATA
                {
                mPCDATA(); 

                }
                break;
            case 10 :
                // OSMLexer.g:1:133: GENERIC_ID
                {
                mGENERIC_ID(); 

                }
                break;
            case 11 :
                // OSMLexer.g:1:144: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    static final String DFA6_eotS =
        "\1\uffff\1\15\1\12\1\17\1\12\1\21\2\12\1\27\1\31\4\uffff\1\12\1"+
        "\uffff\1\34\1\uffff\1\12\1\36\1\uffff\1\12\1\36\1\uffff\1\27\1\uffff"+
        "\1\41\11\uffff";
    static final String DFA6_eofS =
        "\44\uffff";
    static final String DFA6_minS =
        "\1\0\1\57\1\76\1\0\1\76\5\0\4\uffff\1\12\5\0\1\uffff\6\0\1\uffff"+
        "\1\0\1\uffff\1\0\2\uffff\1\0\2\uffff";
    static final String DFA6_maxS =
        "\1\uffff\1\77\1\76\1\uffff\1\76\5\uffff\4\uffff\1\12\1\0\1\uffff"+
        "\1\0\2\uffff\1\uffff\2\uffff\1\0\1\uffff\1\0\1\uffff\1\uffff\1\0"+
        "\1\uffff\1\0\2\uffff\1\0\2\uffff";
    static final String DFA6_acceptS =
        "\12\uffff\1\11\1\1\1\4\1\3\6\uffff\1\10\6\uffff\1\5\1\uffff\1\7"+
        "\1\uffff\1\12\1\13\1\uffff\1\6\1\2";
    static final String DFA6_specialS =
        "\1\13\1\uffff\1\27\1\5\1\15\1\16\1\24\1\23\1\7\1\12\4\uffff\1\0"+
        "\1\1\1\14\1\4\1\26\1\17\1\uffff\1\20\1\22\1\10\1\21\1\11\1\2\1\uffff"+
        "\1\3\1\uffff\1\6\2\uffff\1\25\2\uffff}>";
    static final String[] DFA6_transitionS = {
            "\11\12\2\11\1\12\2\11\22\12\1\11\1\12\1\6\4\12\1\7\7\12\1\4"+
            "\12\12\1\10\1\12\1\1\1\5\1\3\1\2\1\12\32\10\4\12\1\10\1\12\32"+
            "\10\uff85\12",
            "\1\14\17\uffff\1\13",
            "\1\16",
            "\74\12\1\uffff\uffc3\12",
            "\1\20",
            "\74\12\1\uffff\uffc3\12",
            "\42\22\1\23\31\22\1\24\uffc3\22",
            "\47\25\1\26\24\25\1\24\uffc3\25",
            "\55\12\2\30\1\12\13\30\1\12\1\uffff\4\12\32\30\4\12\1\30\1"+
            "\12\32\30\uff85\12",
            "\74\12\1\uffff\uffc3\12",
            "",
            "",
            "",
            "",
            "\1\32",
            "\1\uffff",
            "\74\12\1\uffff\uffc3\12",
            "\1\uffff",
            "\42\22\1\23\31\22\1\24\uffc3\22",
            "\74\12\1\uffff\uffc3\12",
            "",
            "\47\25\1\26\24\25\1\24\uffc3\25",
            "\74\12\1\uffff\uffc3\12",
            "\1\uffff",
            "\55\12\2\30\1\12\13\30\1\12\1\uffff\4\12\32\30\4\12\1\30\1"+
            "\12\32\30\uff85\12",
            "\1\uffff",
            "\74\12\1\uffff\uffc3\12",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TAG_DECLARATION_OPEN | TAG_DECLARATION_CLOSE | TAG_START_OPEN | TAG_END_OPEN | TAG_CLOSE | TAG_EMPTY_CLOSE | ATTR_EQ | ATTR_VALUE | PCDATA | GENERIC_ID | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_14 = input.LA(1);

                         
                        int index6_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_14=='\n') && ((( tagMode )||( !tagMode )))) {s = 26;}

                        else s = 10;

                         
                        input.seek(index6_14);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_15 = input.LA(1);

                         
                        int index6_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( tagMode )) ) {s = 27;}

                        else if ( (( !tagMode )) ) {s = 10;}

                         
                        input.seek(index6_15);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_26 = input.LA(1);

                         
                        int index6_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_26>='\u0000' && LA6_26<=';')||(LA6_26>='=' && LA6_26<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 33;

                         
                        input.seek(index6_26);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA6_28 = input.LA(1);

                         
                        int index6_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( tagMode )) ) {s = 34;}

                        else if ( (( !tagMode )) ) {s = 10;}

                         
                        input.seek(index6_28);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA6_17 = input.LA(1);

                         
                        int index6_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( tagMode )) ) {s = 29;}

                        else if ( (( !tagMode )) ) {s = 10;}

                         
                        input.seek(index6_17);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA6_3 = input.LA(1);

                         
                        int index6_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_3>='\u0000' && LA6_3<=';')||(LA6_3>='=' && LA6_3<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 15;

                         
                        input.seek(index6_3);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA6_30 = input.LA(1);

                         
                        int index6_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( tagMode )) ) {s = 20;}

                        else if ( (( !tagMode )) ) {s = 10;}

                         
                        input.seek(index6_30);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA6_8 = input.LA(1);

                         
                        int index6_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_8>='-' && LA6_8<='.')||(LA6_8>='0' && LA6_8<=':')||(LA6_8>='A' && LA6_8<='Z')||LA6_8=='_'||(LA6_8>='a' && LA6_8<='z')) && ((( tagMode )||( !tagMode )))) {s = 24;}

                        else if ( ((LA6_8>='\u0000' && LA6_8<=',')||LA6_8=='/'||LA6_8==';'||(LA6_8>='=' && LA6_8<='@')||(LA6_8>='[' && LA6_8<='^')||LA6_8=='`'||(LA6_8>='{' && LA6_8<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 23;

                         
                        input.seek(index6_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA6_23 = input.LA(1);

                         
                        int index6_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( !tagMode )) ) {s = 10;}

                        else if ( (( tagMode )) ) {s = 31;}

                         
                        input.seek(index6_23);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA6_25 = input.LA(1);

                         
                        int index6_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( !tagMode )) ) {s = 10;}

                        else if ( (( tagMode )) ) {s = 32;}

                         
                        input.seek(index6_25);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA6_9 = input.LA(1);

                         
                        int index6_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_9>='\u0000' && LA6_9<=';')||(LA6_9>='=' && LA6_9<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 25;

                         
                        input.seek(index6_9);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA6_0 = input.LA(1);

                         
                        int index6_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_0=='<') ) {s = 1;}

                        else if ( (LA6_0=='?') && ((( tagMode )||( !tagMode )))) {s = 2;}

                        else if ( (LA6_0=='>') && ((( tagMode )||( !tagMode )))) {s = 3;}

                        else if ( (LA6_0=='/') && ((( tagMode )||( !tagMode )))) {s = 4;}

                        else if ( (LA6_0=='=') && ((( tagMode )||( !tagMode )))) {s = 5;}

                        else if ( (LA6_0=='\"') && ((( tagMode )||( !tagMode )))) {s = 6;}

                        else if ( (LA6_0=='\'') && ((( tagMode )||( !tagMode )))) {s = 7;}

                        else if ( (LA6_0==':'||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) && ((( tagMode )||( !tagMode )))) {s = 8;}

                        else if ( ((LA6_0>='\t' && LA6_0<='\n')||(LA6_0>='\f' && LA6_0<='\r')||LA6_0==' ') && ((( tagMode )||( !tagMode )))) {s = 9;}

                        else if ( ((LA6_0>='\u0000' && LA6_0<='\b')||LA6_0=='\u000B'||(LA6_0>='\u000E' && LA6_0<='\u001F')||LA6_0=='!'||(LA6_0>='#' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='.')||(LA6_0>='0' && LA6_0<='9')||LA6_0==';'||LA6_0=='@'||(LA6_0>='[' && LA6_0<='^')||LA6_0=='`'||(LA6_0>='{' && LA6_0<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                         
                        input.seek(index6_0);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA6_16 = input.LA(1);

                         
                        int index6_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_16>='\u0000' && LA6_16<=';')||(LA6_16>='=' && LA6_16<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 28;

                         
                        input.seek(index6_16);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA6_4 = input.LA(1);

                         
                        int index6_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_4=='>') && ((( tagMode )||( !tagMode )))) {s = 16;}

                        else s = 10;

                         
                        input.seek(index6_4);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA6_5 = input.LA(1);

                         
                        int index6_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_5>='\u0000' && LA6_5<=';')||(LA6_5>='=' && LA6_5<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 17;

                         
                        input.seek(index6_5);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA6_19 = input.LA(1);

                         
                        int index6_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_19>='\u0000' && LA6_19<=';')||(LA6_19>='=' && LA6_19<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 30;

                         
                        input.seek(index6_19);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA6_21 = input.LA(1);

                         
                        int index6_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_21=='\'') && ((( tagMode )||( !tagMode )))) {s = 22;}

                        else if ( ((LA6_21>='\u0000' && LA6_21<='&')||(LA6_21>='(' && LA6_21<=';')||(LA6_21>='=' && LA6_21<='\uFFFF')) && ((( tagMode )||( !tagMode )))) {s = 21;}

                        else if ( (LA6_21=='<') && (( tagMode ))) {s = 20;}

                        else s = 10;

                         
                        input.seek(index6_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA6_24 = input.LA(1);

                         
                        int index6_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_24>='-' && LA6_24<='.')||(LA6_24>='0' && LA6_24<=':')||(LA6_24>='A' && LA6_24<='Z')||LA6_24=='_'||(LA6_24>='a' && LA6_24<='z')) && ((( tagMode )||( !tagMode )))) {s = 24;}

                        else if ( ((LA6_24>='\u0000' && LA6_24<=',')||LA6_24=='/'||LA6_24==';'||(LA6_24>='=' && LA6_24<='@')||(LA6_24>='[' && LA6_24<='^')||LA6_24=='`'||(LA6_24>='{' && LA6_24<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 23;

                         
                        input.seek(index6_24);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA6_22 = input.LA(1);

                         
                        int index6_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_22>='\u0000' && LA6_22<=';')||(LA6_22>='=' && LA6_22<='\uFFFF')) && (( !tagMode ))) {s = 10;}

                        else s = 30;

                         
                        input.seek(index6_22);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA6_7 = input.LA(1);

                         
                        int index6_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_7>='\u0000' && LA6_7<='&')||(LA6_7>='(' && LA6_7<=';')||(LA6_7>='=' && LA6_7<='\uFFFF')) && ((( tagMode )||( !tagMode )))) {s = 21;}

                        else if ( (LA6_7=='\'') && ((( tagMode )||( !tagMode )))) {s = 22;}

                        else if ( (LA6_7=='<') && (( tagMode ))) {s = 20;}

                        else s = 10;

                         
                        input.seek(index6_7);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA6_6 = input.LA(1);

                         
                        int index6_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_6>='\u0000' && LA6_6<='!')||(LA6_6>='#' && LA6_6<=';')||(LA6_6>='=' && LA6_6<='\uFFFF')) && ((( tagMode )||( !tagMode )))) {s = 18;}

                        else if ( (LA6_6=='\"') && ((( tagMode )||( !tagMode )))) {s = 19;}

                        else if ( (LA6_6=='<') && (( tagMode ))) {s = 20;}

                        else s = 10;

                         
                        input.seek(index6_6);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA6_33 = input.LA(1);

                         
                        int index6_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( tagMode )) ) {s = 35;}

                        else if ( (( !tagMode )) ) {s = 10;}

                         
                        input.seek(index6_33);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA6_18 = input.LA(1);

                         
                        int index6_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_18=='\"') && ((( tagMode )||( !tagMode )))) {s = 19;}

                        else if ( ((LA6_18>='\u0000' && LA6_18<='!')||(LA6_18>='#' && LA6_18<=';')||(LA6_18>='=' && LA6_18<='\uFFFF')) && ((( tagMode )||( !tagMode )))) {s = 18;}

                        else if ( (LA6_18=='<') && (( tagMode ))) {s = 20;}

                        else s = 10;

                         
                        input.seek(index6_18);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA6_2 = input.LA(1);

                         
                        int index6_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_2=='>') && ((( tagMode )||( !tagMode )))) {s = 14;}

                        else s = 10;

                         
                        input.seek(index6_2);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}