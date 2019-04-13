package com.jad.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

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
    public static String changeQuotation(Object obj) {
    	if(isStringInteger(obj)) {
    		return changeQuotation(String.valueOf(obj));
    	}

    	return "";
    }

    /*
     * 해당 Object가 String or Integer 이면 true
     * 아니면 false
     * @param obj
     * @return
     */
    public static boolean isStringInteger(Object obj) {
    	boolean flag = false;

    	if( obj instanceof String || obj instanceof Integer ) {
    		flag = true;
    	}
    	return flag;
    }
}
