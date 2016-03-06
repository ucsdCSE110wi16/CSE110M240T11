package group11.cse110.com.serviceforservice;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

/**
 * Created by David on 3/5/2016.
 */
public class LoginIdlingResource implements IdlingResource{
    private StartScreen startScreen;
    private StartScreen.StartScreenListener startScreenListener;
    private ResourceCallback resourceCallback;

    public LoginIdlingResource(StartScreen startScreen) {
        this.startScreen = startScreen;
        startScreenListener = new StartScreen.StartScreenListener() {
            @Override
            public void onProgressShown() {
            }
            @Override
            public void onProgressDismissed() {
                if (resourceCallback == null){
                    return ;
                }
                //Called when the resource goes from busy to idle.
                resourceCallback.onTransitionToIdle();
            }
        };

        startScreen.setStartScreenListener(startScreenListener);
    }
    @Override
    public String getName() {
        return LoginIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return resourceCallback != null && startScreen.isDone();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
