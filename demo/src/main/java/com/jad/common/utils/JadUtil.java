package com.jad.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class JadUtil {

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String)s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>)s).isEmpty();
        }
        if (s instanceof List) {
            return ((List<?>)s).isEmpty();
        }
        if (s instanceof Object[]) {
            return (((Object[])s).length == 0);
        }
        return false;
    }

    public boolean isNullOrZero(final Number number){
        return number == null ||
  			   (
  			    number instanceof Integer ? number.intValue() == 0 :
  				  number instanceof Long    ? number.longValue() == 0 :
  			    number instanceof Double  ? number.doubleValue() == 0 :
  			    number instanceof Short   ? number.shortValue() == 0 :
  				  number.floatValue() == 0
  				);
    }

    /*
     * String을 int형으로
     * @param value
     */
    public static int parseInt(String value) {
    	return parseInt(value, 0);
    }

    /*
     * Object를 int형으로
     * defaultValue는 0이다.
     * @param value
     */
    public static int parseInt(Object value) {
    	String valueStr = replaceNull(value);
    	return parseInt(valueStr, 0);
    }

    /*
     * String을 int형으로
     * String이 숫자 형식이 아니면 defaultValue return
     * @param value
     * @param defaultValue
     */
    public static int parseInt(String value, int defaultValue) {
    	int returnValue = 0;

    	if(isNull(value)) {
    		returnValue = defaultValue;
    	} else if(!IsNumeric(value)) {
    		returnValue = defaultValue;
    	} else {
    		returnValue = Integer.parseInt(value);
    	}
    	return returnValue;
    }

    /*
     * String을 long형으로
     * defaultValue는 0이다.
     * @param value
     */
    public static long parseLong(String value) {
    	return parseLong(value, 0);
    }

    /*
     * String을 long형으로
     * 잘못된 데이타 일시 return은 defaultValue
     *  @param value
     */
    public static long parseLong(String value, long defaultValue) {
    	long returnValue = 0;

    	if(isNull(value)) {
    		returnValue = defaultValue;
    	} else if(!IsNumeric(value)) {
    		returnValue = defaultValue;
    	} else {
    		returnValue = Long.parseLong(value);
    	}

    	return returnValue;
    }

    /*
     * Double 형으로 정상 파싱되면 숫자라고 봄
     */
    public static boolean IsNumeric(String str_num) {
    	try {
    		Double.parseDouble(str_num);
    		return true;
    	} catch (NumberFormatException e) {
    		return false;
    	}
    }

    /*
     * 해당 Object가 String or Integer 이면 true
     * 아니면 false
     */
    public static boolean isStringInteger(Object obj) {
    	boolean flag = false;

    	if(obj instanceof String || obj instanceof Integer) {
    		flag = true;
    	}
    	return flag;
    }

    /**
     * total과 success 로 % 구하고 소수점 1자리까지 계산
     * @param int success
     * @param int total
     * @return String %
     */
    public static String calculatePercent(int success, int total) {
    	String result = "0";

    	if(total != 0) {
    		Double tempSuccess = new Double(success+".0");
    		Double tempTotal = new Double(total+".0");
    		Double tempPercent = new Double(100+".0");

    		double cal = tempSuccess.doubleValue()+tempPercent.doubleValue()/tempTotal.doubleValue();

    		result = new java.text.DecimalFormat("#.#").format(cal);
    	}
    	return result;
    }

    /*
     * 백분율을 구한다.
     * %는 빼고 값만 리턴
     * @param value
     * @param total
     * @return
     */
    public static String percentValue(int value, int total) {
    	double val = Double.parseDouble(String.valueOf(value)) / Double.parseDouble(String.valueOf(total)) * 100;

    	DecimalFormat df = new DecimalFormat("##0.0");
    	return df.format(val);
    }

    /**
     * 특정문자를 HTML TAG형식으로 변경하는 메소드.
     *
     * <xmp>
     * & --> &amp;
     * < --> &lt;
     * > --> &gt;
     * " --> &quot;
     * ' --> &#039;
     *-----------------------------------------------------------------
     * <option type=radio  name=r value="xxxxxxxx"> yyyyyyy
     * <input  type=hidden name=h value="xxxxxxxx">
     * <input  type=text   name=t value="xxxxxxxx">
     * <textarea name=msg rows=20 cols=53>xxxxxxx</textarea>
     *-
     * 위와 같은 HTML 소스를 생성할 때, xxxxxxx 부분의 문자열 중에서
     * 아래에 있는 몇가지 특별한 문자들을 변환하여야 합니다.
     * 만약 JSP 라면 미리 변환하여 HTML 전체 TAG를 만들거나, 혹은 아래처럼 사용하세요.
     *-
     * <option type=radio  name=r value="<%= StringUtil.translate(s) %>"> yyyyyyy
     * <input  type=hidden name=n value="<%= StringUtil.translate(s) %>">
     * <input  type=text   name=n value="<%= StringUtil.translate(s) %>">
     * <textarea name=body rows=20 cols=53><%= StringUtil.translate(s) %></textarea>
     *-
     * 또 필요하다면 yyyyyyy 부분도  translate(s)를 할 필요가 있을 겁니다.
     * 필요할 때 마다 사용하세요.
     *-
     * </xmp>
     *
     * @return the translated string.
     * @param str java.lang.String
     */
    public static String translate(String str){
        if ( str == null ) return null;

        StringBuffer buf = new StringBuffer();
        char[] c = str.toCharArray();
        int len = c.length;

        for ( int i=0; i < len; i++){
            if      ( c[i] == '&' ) buf.append("&amp;");
            else if ( c[i] == '<' ) buf.append("&lt;");
            else if ( c[i] == '>' ) buf.append("&gt;");
            else if ( c[i] == '"' ) buf.append("&quot;"); // (char)34
            else if ( c[i] == '\'') buf.append("&#039;"); // (char)39
            else buf.append(c[i]);
        }
        return buf.toString();
    }

    /**
     * 문자열 좌측의 공백을 제거하는 메소드
     * @param  str 대상 문자열
     * @return trimed string with white space removed from the front.
     */
    public static String ltrim(String str){
        int len = str.length();
        int idx = 0;

        while ((idx < len) && (str.charAt(idx) <= ' '))
        {
            idx++;
        }
        return str.substring(idx, len);
    }

    /**
     * 문자열 우측의 공백을 제거하는 메소드
     * @param  str 대상 문자열
     * @return trimed string with white space removed from the end.
     */
    public static String rtrim(String str){
        int len = str.length();

        while ((0 < len) && (str.charAt(len-1) <= ' '))
        {
            len--;
        }
        return str.substring(0, len);
    }

    /*
     *
     */
    public static String nullToZero(String value) {
    	if(value == null || value.equals("")) {
    		value = "0";
    	}
    	return value;
    }

    /*
     * 숫자 0이 넘어오면 ""로 대치
     * @param  int 대상 숫자
     * @return java.lang.String
     */
    public static String isOneNull(int num) {
    	if(num == 0) return "";
    	else return Integer.toString(num);
    }

    /*
     * str이 null 이거나 "", "    " 일경우 return true
     * @param str
     * @return java.lang.String
     */
    public static boolean isNull(String str) {
    	return (str == null || (str.trim().length()) == 0);
    }

    /*
     * null이 아닐때.
     * @param str
     * @return / isNull이 true이면 false
     */
    public static boolean isNull(Object obj) {
    	String str = null;
    	if( obj instanceof String ) {
    		str = (String)obj;
    	} else {
    		return true;
    	}
    	return isNull(str);
    }

    /*
     * null이 아닐때.
     * @param str
     * @return boolean
     */
    public static boolean isNotNull(String str) {
    	/*
    	 * isNull이 true이면 false
    	 * false이면 true
    	 */
    	if(isNull(str)) {
    		return false;
    	} else {
    		return true;
    	}
    }

    public static boolean isNotNull(Object obj) {
    	String str = null;
    	if( obj instanceof String) {
    		str = (String)obj;
    	} else {
    		return false;
    	}
    	return isNotNull(str);
    }

    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param source
     *        원본 문자열
     * @param subject
     *        원본 문자열에 포함된 특정 문자열
     * @param object
     *        변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr = source;

        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr =
                srcStr.substring(srcStr.indexOf(subject) + subject.length(),
                    srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /*
     * 파라미터가 null 이거나 공백이 있을경우
     * "" 로 return
     * @param value
     * @return
     */
    public static String replaceNull(String value) {
    	return replaceNull(value, "");
    }
    /*
     * Object를 받아서 String 형이 아니거나 NULL이면 ""를 return
     * String 형이면 형 변환해서 넘겨준다.
     * @param value
     * @return
     */
    public static String replaceNull(Object value) {
    	Object rtnValue = value;
    	if(rtnValue == null || !"java.lang.String".equals(rtnValue.getClass().getName())) {
    		rtnValue = "";
    	}
    	return replaceNull((String)rtnValue, "");
    }

    /*
     * 파라미터로 넘어온 값이 null 이거나 공백이 포함된 문자라면
     * defaultValue를 return
     * 아니면 값을 trim해서 넘겨준다.
     * @param value
     * @param repStr
     * @return
     */
    public static String replaceNull(String value, String defaultValue) {
    	if(isNull(value)) {
    		return defaultValue;
    	}
    	return value.trim();
    }

    /*
     * Object를 받아서 String 형이 아니거나 NULL이면 defaultValue를 return
     * String 형이면 형 변환해서 넘겨준다.
     * @param value
     * @param repStr
     * @return
     */
    public static String replaceNull(Object value, String defaultValue) {
    	String valueStr = replaceNull(value);
    	if(isNull(valueStr)) {
    		return defaultValue;
    	}
    	return valueStr.trim();
    }

    /*
     * 파라미터로 넘어오는 String을 , 를 제거해준다.
     *
     * @param s java.lang.String
     * @return java.lang.String
     */
    public static String removeComma(String str) {
    	String rtnValue = str;
    	if( isNull(str) ) {
    		return "";
    	}
    	rtnValue = replace(rtnValue, ",", "");
    	return rtnValue;
    }

    /*
     * Method ksc2asc.
     * 8859-1를 euc-kr로 인코딩하는 함수
     * @param str - String
     * @return String
     */
    public static String ksc2asc(String str) {
    	String result = "";

    	if(isNull(str)) {
    		result = "";
    	} else {
    		try {
    			result = new String(str.getBytes("euc-kr"), "8859_1");
    		} catch(Exception e) {
    			result = "";
    		}
    	}
    	return result;
    }

    /*
     * Method asc2ksc.
     * euc-kr을 8859-1로 인코딩하는 함수
     * @param str - String
     * @return String
     */
    public static String asc2ksc(String str) {
    	String result = "";

    	if(isNull(str)) {
    		result = "";
    	} else {
    		try {
    			result = new String(str.getBytes("8859_1"), "euc-kr");
    		} catch(Exception e) {
    			result = "";
    		}
    	}
    	return result;
    }

    /*
     * Method ksc2asc.
     * euc-kr을 euc-kr로 인코딩하는 함수
     * @param str - String
     * @return String
     */
    public static String ksc2utf8(String str) {
    	String result = "";

    	if(isNull(str)) {
    		result = "";
    	} else {
    		try {
    			result = new String(str.getBytes("euc-kr"), "utf-8");
    		} catch(Exception e) {
    			result = "";
    		}
    	}
    	return result;
    }

    /*
     * Html 코드에서 &#60;br&#62; 태크 제거
     * @param comment
     * @return
     */
    public static String convertHtmlBr(String comment) {
    	String rtnValue = "";
    	if( isNull(comment) ) {
    		return "";
    	}
    	rtnValue = replace(comment, "\r\n", "<br>");
    	return rtnValue;
    }

    /*
     * XSS(Cross Site Scripting) 취약점 해결을 위한 처리
     *
     * @param sourceString String 원본문자열
     * @return String 변환문자열
     */
    public static String replaceXSS(String sourceString){
    	String rtnValue = null;
    	if(sourceString != null) {
    		rtnValue = sourceString;
    		if(rtnValue.indexOf("<x-") == -1){
    			rtnValue = rtnValue.replaceAll("< *(j|J)(a|A)(v|V)(a|A)(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)", "<x-javascript");
    			rtnValue = rtnValue.replaceAll("< *(v|V)(b|B)(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)", "<x-vbscript");
    			rtnValue = rtnValue.replaceAll("< *(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)", "<x-script");
    			rtnValue = rtnValue.replaceAll("< *(i|I)(f|F)(r|R)(a|A)(m|M)(e|E)", "<x-iframe");
    			rtnValue = rtnValue.replaceAll("< *(f|F)(r|R)(a|A)(m|M)(e|E)", "<x-frame");
    			rtnValue = rtnValue.replaceAll("(e|E)(x|X)(p|P)(r|R)(e|E)(s|S)(s|S)(i|I)(o|O)(n|N)", "x-expression");
    			rtnValue = rtnValue.replaceAll("(a|A)(l|L)(e|E)(r|R)(t|T)", "x-alert");
    			rtnValue = rtnValue.replaceAll(".(o|O)(p|P)(e|E)(n|N)", ".x-open");
    			rtnValue = rtnValue.replaceAll("< *(m|M)(a|A)(r|R)(q|Q)(u|U)(e|E)(e|E)", "<x-marquee");
    			rtnValue = rtnValue.replaceAll("&#", "&amp;#");
    		}
    	}
    	return rtnValue;
    }

    /*
     * Exception을 String으로 뽑아준다.
     * @param ex
     * @return
     */
    public static String stackTraceToString(Throwable e) {
    	try {
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		e.printStackTrace(pw);
    		return "------\r\n" + sw.toString() + "------\r\n";
    	} catch (Exception e2) {
    		return stackTraceToString2(e);
    	}
    }

    /*
     * Exception을 String으로 뽑아준다.
     * @param ex
     * @return
     */
    public static String stackTraceToString2(Throwable e) {
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
    	PrintStream p = new PrintStream(b);
    	e.printStackTrace(p);
    	p.close();
    	String strackTrace = b.toString();
    	try {
    		b.close();
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	return strackTrace;
    }

    /*
     * string에 있는 ', ", \r\n 를 HTML 코드로 변환한다.
     * @param str
     * @return
     */
    public static String changeQuotation(String str) {
    	String rtnValue = str;
    	rtnValue = replaceNull(rtnValue);
    	rtnValue = replace(replace(replace(rtnValue, "'", "&#39;"), "\"", "&#34;"), "\r\n", "<br>");

    	return rtnValue;
    }

    /*
     * String 배열을 List로 변환한다.
     */
    public static List<String> changeList(String[] values) {
    	List<String> list = new ArrayList<String>();

    	if(values == null) {
    		return list;
    	}
    	for(int i=0,n=values.length; i<n; i++) {
    		list.add(values[i]);
    	}

    	return list;
    }

    /*
     *
     */
    public static String[] toTokenArray(String str, String sep){
    	String[] temp = null;

    	try {
    		StringTokenizer st = new StringTokenizer(str, sep);
    		temp = new String[st.countTokens()];
    		int index = 0;
    		while(st.hasMoreTokens()) {
    			temp[index++] = st.nextToken();
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return temp;
    }

    /*
     *
     */
    public static String strip(String str, String str1){
    	if(str == null || "".equals(str.trim())) return "";
    	String temp = str;
    	int pos = -1;
    	while((pos = temp.indexOf(str1, pos)) != -1) {
    		String left = temp.substring(0, pos);
    		String right = temp.substring(pos + 1, temp.length());
    		temp = left + "" + right;
    		pos += 1;
    	}
    	return temp;
    }
}
