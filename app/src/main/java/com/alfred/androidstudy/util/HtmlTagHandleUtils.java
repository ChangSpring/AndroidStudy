package com.alfred.androidstudy.util;

import android.text.Editable;
import android.text.Html.TagHandler;

import org.xml.sax.XMLReader;

/**
 * Created by lagou on 2015/10/27.
 */
public class HtmlTagHandleUtils implements TagHandler {
    private int index = 0;
    //0：不是ol,1：ol开始，2：ol结束
    private int olState = 0;
    //0：不是ul,1：ul开始，2：ul结束
    private int ulState = 0;
    //-1：其他标签，0：ol标签，1：ul标签
    private int tagState = -1;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equalsIgnoreCase("olTag")) {
            // 1 2 3
            tagState = 0;
            if (opening) {
                index = 0;
                olState = 1;
            } else {
                olState = 2;
            }
        } else if (tag.equalsIgnoreCase("ulTag")) {
            // 点 ●
            tagState = 1;
            if (opening) {
                ulState = 1;
            } else {
                ulState = 2;
            }
        } else {
            if (tag.equalsIgnoreCase("liTag")) {
                if (opening) {
                    //移除li标签前面的换行 只保留一个
                    removeMultipleBr(output);
                    output.append("\n");
                    if (tagState == 0 && olState == 1) {
                        //是ol标签，并且该标签未结束
                        index++;
                        output.append("\t").append(String.valueOf(index)).append(". ");
                    } else if (tagState == 1 && ulState == 1) {
                        //是ul标签，并且该标签未结束
                        output.append("\t" + "• ");
                    }
                }

            } else {
                tagState = -1;
            }
        }
    }


    private void removeMultipleBr(Editable output){
        int length = output.length();
        if (length > 0) {
            char lastChar = output.charAt(length - 1);
            if (lastChar == '\n') {
                output.delete(length - 1, length);
                removeMultipleBr(output);
            }

        }
    }
}
