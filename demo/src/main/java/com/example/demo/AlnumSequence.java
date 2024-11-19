package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AlnumSequence {
    
    private static final Log LOG = LogFactory.getLog(App.class);

    static final String MASKING_STR = "*";

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("attr_lvl", 1);
        map.put("attr_cls", "AA");
        map.put("start_no", "95");
        map.put("end_no", "QQ");
        // map.put("end_no", "1A");

        String startNo = String.valueOf(map.get("start_no"));
        String endNo = String.valueOf(map.get("end_no"));

        int seq = getAlnumToNumber(map);
        System.out.println("seq=====" + seq);

        for(String s: endNo.split("")){
            System.out.println(s + " ");
        }
        for(int i=0; i < endNo.length(); i++) {
            LOG.info("i====" + endNo.charAt(i));
        }
    }

    public static int getAlnumToNumber(Map<String, Object> param) {
        String[] alpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        int idx = -1;
        int sequence = 0;

        String attrCls = String.valueOf(param.get("attr_cls"));
        String tNumber = String.valueOf(param.get("start_no"));

        System.out.println("attrCls=====" + attrCls);
        switch(attrCls) {
            case "A0":
            case "A9":
                if(isNumeric(tNumber)) return Integer.parseInt(tNumber);

                idx = Arrays.asList(alpha).indexOf(tNumber.substring(0, 1));    // 영문 추출
                if(idx > -1) {
                    sequence = 100 + (10*idx) + Integer.parseInt(tNumber.substring(1, 2));
                    //(9*idx) : A1~9까지 사용시, (10*idx) : A0~A9까지 사용시
                }
                break;
            case "0A":
            case "0Z":
                if(isNumeric(tNumber)) return Integer.parseInt(tNumber);

                idx = Arrays.asList(alpha).indexOf(tNumber.substring(1, 2));    // 영문 추출
                if(idx > -1) {
                    sequence = 100 + idx + (26 * (Integer.parseInt(tNumber.substring(0, 1)) -1));
                }
                break;
            case "AZ":
            case "AA":
            case "ZZ":
                if(isNumeric(tNumber)) return Integer.parseInt(tNumber);

                // 1. 00~ZZ (00~99,AA~ZZ)
                idx = Arrays.asList(alpha).indexOf(tNumber.substring(0, 1));    // 1번째 영문 추출
                if(idx == -1)
                    return idx;

                sequence = 100 + (26 * idx);

                idx = Arrays.asList(alpha).indexOf(tNumber.substring(1, 2));    // 2번째 영문 추출
                if(idx == -1)
                    return idx;
                
                sequence = sequence + idx;
                // 2. AA~ZZ
                break;
            case "A..Z":
                break;
            case "00":
            case "000":
            case "0000":
            case "00000":
            case "000000":
                if(isNumeric(tNumber)) return Integer.parseInt(tNumber);
                break;
            default:
                break;
        }
        return sequence;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[^0-9]");
        return ! pattern.matcher(str).find();
    }

    public static boolean isNumeric2(String str) {
        boolean result = true;
        for(char c : str.toCharArray()){
            if(!Character.isDigit(c)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
