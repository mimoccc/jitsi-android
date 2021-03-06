/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.service.osgi;

import android.app.*;
import android.support.v4.app.*;
import android.support.v4.app.DialogFragment;

import net.java.sip.communicator.util.*;

import org.osgi.framework.*;

/**
 * Class can be used to build {@link DialogFragment}s that require OSGI services
 * access.
 *
 * @author Pawel Domas
 */
public class OSGiDialogFragment
    extends DialogFragment
    implements OSGiUiPart
{
    /**
     * The logger
     */
    private final static Logger logger
            = Logger.getLogger(OSGiDialogFragment.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        OSGiActivity osGiActivity = (OSGiActivity) activity;
        osGiActivity.registerOSGiFragment(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach()
    {
        ((OSGiActivity)getActivity()).unregisterOSGiFragment(this);
        super.onDetach();
    }

    /**
     * {@inheritDoc}
     */
    public void start(BundleContext bundleContext) throws Exception
    {
    }

    /**
     * {@inheritDoc}
     */
    public void stop(BundleContext bundleContext) throws Exception
    {
    }

    /**
     * Tries to run given <tt>action</tt> on the UI thread.
     * If there's no <tt>Activity</tt> available warning will be logged.
     *
     * @param action <tt>Runnable</tt> action to execute on UI thread.
     */
    protected void runOnUiThread(Runnable action)
    {
        FragmentActivity activity = getActivity();
        if(activity == null)
        {
            logger.warn("Called runOnUiThread when Activity was null!",
                        new Throwable());
            return;
        }

        activity.runOnUiThread(action);
    }
}
