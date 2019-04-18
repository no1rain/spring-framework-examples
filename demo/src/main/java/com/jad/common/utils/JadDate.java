package com.jad.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class JadDate {
	private final String DATE_FORMAT = "yyyyMMdd";
	private final String DATE_FORMT = "yyyy-MM-dd";
	private final String DATETIME_FORMT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * 현재 날짜 구하기
	 */
	public static String getCurrentDate() {
		int nYear;
		int nMonth;
		int nDay;

		Calendar calendar = new GregorianCalendar();
		nYear = calendar.get(calendar.YEAR);
		nMonth = calendar.get(calendar.MONTH);
		nDay = calendar.get(calendar.DAY_OF_MONTH);

		return "";
	}
	/*
	 * {@link SimpleDateFormat}의 instance를 반환한다.
	 *
	 * @param format 형식화 패턴
	 * @return java.text.SimpleDateFormat
	 */
	private static DateFormat getSimpleDateFormat(String format){
		return new SimpleDateFormat(format);
	}

	/*
	 * 특정 날짜 형식화 패턴을 기준으로 문자열로 구성된 날짜를 받아 {@link Date}객체를
	 * 생성하여 반환한다.
	 * 날짜문자열은 반드시 Date Formatter가 보유한 형식화 패턴과 일치하는 패턴이어야 한다.
	 *
	 * @param date 날짜문자열
	 * @param formatter 날짜문자열을 Parsing할 Date Formatter
	 * @return {@link java.util.Date}
	 * @throws ParseException
	 * java.util.Date로 변환할 날짜 문자열이 Date Formatter의 형식화 패턴과 일지하지 않을 때
	 * 발생한다.
	 */
	private static Date parseDate(String date, DateFormat formatter) throws ParseException{
		return formatter.parse(date);
	}

	/*
	 * 특정 날짜를 받아 {@link Date}객체를 생성하여 반환한다.
	 *
	 * @param date 날짜문자열
	 * @param format 날짜가 보유한 형식화 패턴
	 * @return {@link java.util.Date}
	 * @throws ParseException
	 * ava.util.Date로 변환할 날짜 문자열이 형식화 패턴과 일지하지 않을 때 발생한다.
	 */
	public static Date parseDate(String date, String format) throws ParseException{
		return getSimpleDateFormat(format).parse(date);
	}

	/*
	 * 특정날짜가 어플리케이션이 구동되고 있는 시스템의 현재 날짜에서 특정시간(밀리초)을
	 * 지났는가에 대한 검사를 수행한다.
	 *
	 *  @param date 비교할 날짜
	 *  @param format 비교할 날짜가 보유한 형식화 패턴
	 *  @param time 검사할 시간(밀리초)
	 *  @return true-특정 날짜가 시스템의 현재시간에서 특정 시간(밀리초)만큼 지났음.
	 *  ((시스템의 현재시간 - 비교날짜의 시간) > 비교시간)이다.
	 *  @throws ParseException 비교할 날짜가 형식화 패턴과 일치하지 않을 때 발생한다.
	 */
	public static boolean isLapse(String date, String format, long time) throws ParseException{
		DateFormat df = getSimpleDateFormat(format);
		return (System.currentTimeMillis() - parseDate(date, df).getTime()) > time;
	}

	/*
	 * 특정 날짜가 기준 날짜보다 큰가를 검사한다.
	 * 기준날짜와 비교할 날짜는 같은 패턴을 가져야 한다.
	 *
	 * @param basis 기준잘짜
	 * param target 비교할 날짜
	 * @param format 기준날짜와 비교할 날짜의 형식화 패턴
	 * @return true-비교할 날짜가 기준 날짜보다 크다.
	 * @throws ParseException
	 * 기준날짜나 비교할 날짜가 해당 형식화패턴과 일치하지 않을 때 발생한다.
	 */
	public static boolean isBiggerDate(String basis, String target, String format) throws ParseException{
		DateFormat df = getSimpleDateFormat(format);
		return parseDate(basis, df).getTime() < parseDate(target, df).getTime();
	}

	/*
	 * 특정 날짜가 기준 날짜보다 크거나 같은가를 검사한다.
	 * 기준날짜와 비교할 날짜는 같은 패턴을 가져야 한다.
	 *
	 * @param basis 기준잘짜
	 * param target 비교할 날짜
	 * @param format 기준날짜와 비교할 날짜의 형식화 패턴
	 * @return true-비교할 날짜가 기준 날짜보다 크거나 같다.
	 * @throws ParseException
	 * 기준날짜나 비교할 날짜가 해당 형식화패턴과 일치하지 않을 때 발생한다.
	 */
	public static boolean isEqualsOrBiggerDate(String basis, String target, String format) throws ParseException{
		DateFormat df = getSimpleDateFormat(format);
		return parseDate(basis, df).getTime() <= parseDate(target, df).getTime();
	}

	/*
	 * 특정 날짜가 어떤 날짜 구간에 존재하는 가를 검사한다.
	 * 비교할 날짜와 구간을 시작하는 날짜, 구간이 끝나는 날짜는 같은 패턴을 가져야 한다.
	 *
	 * @param startBasis 구간의 시작 날짜
	 * @param endBasis 구간의 끝날짜
	 * @param target 비교할 날짜
	 * @param 날짜의 형식화 패턴
	 * @return true-구간 사이에 존재한다. (startBasis <= target <= endBasis)이다.
	 * @throws ParseException
	 * 각 날짜가 형식화 패턴과 일치하지 않을 때 발생한다.
	 */
	public static boolean isBetweenOfStartDateAndEndDate(String startBasis, String endBasis,
			String target, String format)throws ParseException{
		DateFormat df = getSimpleDateFormat(format);
		Date td = parseDate(target, df);
		return parseDate(startBasis, df).getTime() <= td.getTime() &&
				td.getTime() <= parseDate(endBasis, df).getTime();
	}

	/*
	 * 어플리케이션이 구동되고 있는 시스템의 현재 날짜가 어떤 날짜 구간에
	 * 존재하는가를 검사한다.
	 * 구간을 시작하는 날짜, 구간이 끝나는 날짜는 같은 패턴을 가져야 한다.
	 *
	 * @param startBasis 구간의 시작 날짜
	 * @param endBasis 구간의 끝날짜
	 * @param format 날짜의 형식화 패턴
	 * @return true-구간 사이에 존재한다. (startBasis <= 시스템 현재시간 <= endBasis)이다.
	 * @throws ParseException
	 * 각 날짜가 형식화 패턴과 일치하지 않을 때 발생한다.
	 */
	public static boolean isBetweenOfStartDateAndEndDate(String startBasis, String endBasis, String format) throws ParseException{
		DateFormat df = getSimpleDateFormat(format);
		long ct = System.currentTimeMillis();
		return parseDate(startBasis, df).getTime() <= ct &&
				ct <= parseDate(endBasis, df).getTime();
	}
}
