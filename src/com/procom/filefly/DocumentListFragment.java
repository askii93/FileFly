package com.procom.filefly;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.procom.filefly.model.Document;

import android.app.Fragment;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * The {@link android.app.Fragment} that establishes the user interface
 * for the {@link android.widget.ListView} by connecting to a SQLite
 * database in internal storage and constructing a {@link java.util.List}
 * of {@link com.procom.filefly.model.Document}s managed by a
 * {@link android.widget.BaseAdapter} from the queried data.
 * 
 * @author Peter Piech
 * @version 0.7b
 * @since 2014-10-12
 *
 */
public class DocumentListFragment extends Fragment
{
	/** The {@link java.util.List} of {@link com.procom.filefly.model.Document}s that back the {@link com.procom.filefly.DocumentListFragment}'s {@link android.widget.ListView} */
	private List<Document> mDocuments;
	
	/** The {@link com.procom.filefly.DocumentListAdapter} that is used to back the {@link android.widget.ListView} */
	private DocumentListAdapter mListAdapter;
	
	/** The {@link com.procom.filefly.SqliteController} is used as an API for {@link android.database.sqlite.SQLiteDatabase} */
	private SqliteController mSqliteController;
	    
	public DocumentListFragment(SqliteController sqliteController)
	{
		mSqliteController = sqliteController;
	}
	
	/**
	 * @return The {@link android.widget.View} which is actually a {@link android.widget.ListView}
	 * seen in the main user interface.  This method is called early in the lifecycle of the
	 * {@link com.procom.filefly.DocumentListFragment}
	 * 
	 * @author Peter Piech
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.document_list_fragment, container, false);
		setHasOptionsMenu(true);
		ListView documentsList = (ListView) rootView.findViewById(R.id.documentlist);
		mDocuments =  mSqliteController.getAllDocuments();
		mListAdapter = new DocumentListAdapter(this.getActivity(), mSqliteController, mDocuments);
		documentsList.setAdapter(mListAdapter);
		return rootView;
	}
	
	/**
	 * This is called to notify the fragment that the SQLite database has been updated externally
	 * 
	 * @author Peter Piech
	 */
	public void notifyDataBaseChanged()
	{
		mDocuments = mSqliteController.getAllDocuments();
		mListAdapter.updateDocuments(mDocuments);
	}
}