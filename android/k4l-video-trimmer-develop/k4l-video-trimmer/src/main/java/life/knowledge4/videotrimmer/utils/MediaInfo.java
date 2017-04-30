package life.knowledge4.videotrimmer.utils;

/**
 * Created by liwenfeng on 17/4/30.
 */

public class MediaInfo {


    public static final String[] MEDIA_AUDIO_FORMAT = {
            "mp3",
            "aac"
    };

    public static final String[] MEDIA_AAC_BITS = {
            "copy",
            "128k",
            "192k",
            "256k",
            "320k",
            "130k",
            "190k",
            "245k"
    };

    public  static  String getAACComment(int i) {
        if(i == 0) {
            return "copy (32kb/s)";
        } else  if (i <= 4) {
            return MediaInfo.MEDIA_AAC_BITS[i] + " " + "CBR";
        } else {
            return MediaInfo.MEDIA_AAC_BITS[i] + " " + "VBR(slow)";
        }
    }

    public static final String[] MEDIA_MP3_BITS = {
            "128k",
            "192k",
            "256k",
            "320k",
            "130k",
            "190k",
            "245k"
    };

    public  static  String getMp3Comment(int i) {
        if (i <= 3) {
            return MediaInfo.MEDIA_MP3_BITS[i] + " " + "CBR";
        } else {
            return MediaInfo.MEDIA_MP3_BITS[i] + " " + "VBR(slow)";
        }
    }
}
