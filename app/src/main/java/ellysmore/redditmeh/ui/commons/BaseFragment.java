package ellysmore.redditmeh.ui.commons;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import ellysmore.redditmeh.api.services.MyOkHttpSpiceService;

public class BaseFragment extends Fragment {

    protected SpiceManager spiceManager = new SpiceManager(MyOkHttpSpiceService.class);

    private String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "OnStart");
        spiceManager.start(getActivity());
        try {
            EventBus.getDefault().register(this);
        } catch (EventBusException e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        spiceManager.shouldStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (EventBusException e) {

        }
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }


    /**
     * Add fragment to backstack if backstackname is given.
     *
     * @param fragment      - Fragment to display
     * @param backStackName - Name required to put fragment in backstack
     */
    public void replaceFragment(Fragment fragment, String backStackName, int containerId) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        if (backStackName != null) {
            ft.addToBackStack(backStackName);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }


}
