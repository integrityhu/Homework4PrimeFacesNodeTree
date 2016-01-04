/*
 * Copyright 2013 Integrity Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Zoltan Papp
 * 
 */
package hu.infokristaly.homework.nodetree.utils;

/**
 * String kezelő statikus metódusok
 * 
 */
public final class StringToolkit {

    public static final String NON_ASCII_HU = "áÁéÉíÍóÓöÖőŐúÚüÜűŰ";

    public static final String ASCII_HU = "aAeEiIoOoOoOuUuUuU";

    public static final String ASCII = ".-_+()abcdefghijklmnopqrstuvwxyz0123456789_ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @return boolean Character is compatible with XML
     */
    public static boolean isXMLCompliant(char c) {
        int type = Character.getType(c);
        boolean result = Character.isLetterOrDigit(c) || Character.isWhitespace(c) || type == Character.DASH_PUNCTUATION || type == Character.START_PUNCTUATION || type == Character.END_PUNCTUATION || type == Character.CONNECTOR_PUNCTUATION || type == Character.MATH_SYMBOL || type == Character.OTHER_PUNCTUATION;
        return result && (c != '&') && (c != '<');
    }

    /**
     * @param str encoding source
     * @return String HTML encoded output
     * 
     *         Encode string converting HTML specific characters using char
     *         order
     * 
     */
    public static String HTMLEnc(String str) {
        StringBuffer buff = new StringBuffer();
        int len = (str == null ? -1 : str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (isXMLCompliant(c))
                buff.append(c);
            else
                buff.append("&#" + (int) c + ";");
        }
        return buff.toString();
    }

    public static char getASCIIChar(char c) {
        char result = '_';
        int idx = NON_ASCII_HU.indexOf(c);
        if (idx > -1) {
            result = ASCII_HU.charAt(idx);
        }
        return result;
    }

    public static String getCFileName(String s) {
        StringBuffer buff = new StringBuffer();
        int len = (s == null ? -1 : s.length());
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (ASCII.indexOf(c) > -1)
                buff.append(c);
            else
                buff.append(getASCIIChar(c));
        }
        return buff.toString();
    }

    public static String getPairsElement(String source, String type) {
        String result = null;
        String[] pairs = source.split(";");
        for (String pair : pairs) {
            String[] typeAndValue = pair.split(":");
            if ((typeAndValue.length == 2) && typeAndValue[0].equalsIgnoreCase(type)) {
                result = typeAndValue[1];
                break;
            }
        }
        return result;
    }

    
    
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
