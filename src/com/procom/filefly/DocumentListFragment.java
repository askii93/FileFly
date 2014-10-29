package com.procom.filefly;

import java.util.List;

import com.procom.filefly.model.Document;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The {@link android.app.Fragment} that establishes the user interface
 * for the {@link android.widget.ListView} by connecting to a SQLite
 * database in internal storage and constructing a {@link java.util.List}
 * of {@link com.procom.filefly.model.Document}s managed by a
 * {@link android.widget.BaseAdapter} from the queried data.
 * 
 * @author Peter Piech
 * @version 0.2a
 * @since 2014-10-12
 *
 */
public class DocumentListFragment extends Fragment
{
	/** The {@link java.util.List} of {@link com.procom.filefly.model.Document}s that back the
	 * {@link com.procom.filefly.DocumentListFragment}'s {@link android.widget.ListView}.
	 */
	private List<Document> documents;
	
	/** The {@link com.procom.filefly.DocumentListAdapter} that is used to back the {@link android.widget.ListView} */
	private DocumentListAdapter listadapter;
	
	/**
	 * @return The {@link android.widget.View} which is actually a {@link android.widget.ListView}
	 * seen in the main user interface.  This method is called early in the lifecycle of the
	 * {@link
	 * @author Peter Piech
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.document_list_fragment, container, false);
        return rootView;
	}
}