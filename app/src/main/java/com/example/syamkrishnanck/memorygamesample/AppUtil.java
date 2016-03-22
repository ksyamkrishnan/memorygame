package com.example.syamkrishnanck.memorygamesample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by syamkrishnanck on 3/21/16.
 */
public class AppUtil {
    public static List<String> getUrlList(List<Feed> feeds) {
        List<String> urlList = new ArrayList<String>();
        if (feeds != null && feeds.size() > 9) {
            for (int i = 0; i < 16; i++) {

                urlList.add(feeds.get(i).getMedia().getM());
            }
            Random random= new Random();
            for(int y=0;y<6;y++){
            int pos = random.nextInt(15 - 1 + 1) + 1;
            urlList.add(pos,"");
            }

        }
        return urlList.subList(1,16);
    }
}
