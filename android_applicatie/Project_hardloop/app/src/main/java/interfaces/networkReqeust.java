package interfaces;

import com.android.volley.VolleyError;
/**
 * Created by niek on 25-12-2015.
 * Interface die word gebruikt voor de callback van de netwerk reqeusts
 */
public interface NetworkReqeust {

    void onError(VolleyError response, String tag);

    void onSucces(String response, String tag);
}
