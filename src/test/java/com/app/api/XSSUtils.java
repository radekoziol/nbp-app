package com.app.api;

//"<p><p>&lt;a href=&quot;#&quot; onclick=&quot;alert(1)&quot;&gt;PLEASE&lt;/a&gt;</p>";

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class XSSUtils {

    private static final List<String> maliciousDataSamples = Arrays.asList(
            "<body onload=alert('test1')>\n",
            "<b onmouseover=alert('Wufff!')>click me!</b>\n",
            "<img src=\"http://url.to.file.which/not.exist\" onerror=alert(document.cookie);>\n",
            "<IMG SRC=j&#X41vascript:alert('test2')>\n",
            "<META HTTP-EQUIV=\"refresh\"\n" +
                    "CONTENT=\"0;url=data:text/html;base64,PHNjcmlwdD5hbGVydCgndGVzdDMnKTwvc2NyaXB0Pg\">");

    public static String generateRandomDangerousQuery() {
        return  getRandomMaliciousDataSample();
    }

    private static String getRandomMaliciousDataSample() {
        return maliciousDataSamples.get(new Random().nextInt(maliciousDataSamples.size()));
    }
}
