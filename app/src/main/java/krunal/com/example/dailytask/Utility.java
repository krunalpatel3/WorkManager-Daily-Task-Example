package krunal.com.example.dailytask;

import java.text.SimpleDateFormat;
import java.util.Date;

class Utility {

    static String getCurruntDateTime() {
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
