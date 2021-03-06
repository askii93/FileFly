package com.procom.filefly;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
import com.procom.filefly.model.Document;
import com.procom.filefly.util.FilesIntentHandler;

/**
 * The {@link android.widget.BaseAdapter} used to back the ListView hosted
 * by the {@link DocumentListFragment} using {@link Document}s as the data
 * model.
 * 
 * @author Peter Piech
 * @version 0.7b
 * @since 2014-10-12
 *
 */
public class DocumentListAdapter extends BaseAdapter
{
	/** 
	 * The {@link android.app.Activity} which hosts the a {@link android.widget.ListView} directly or indirectly
	 * in a {@link android.app.Fragment}
	 */
	private Context mContext;
	
	/**
	 * The {@link java.util.List} of {@link com.procom.filefly.model.Document}s that back the items in
	 * the {@link android.widget.ListView}
	 */
	private List<Document> mDocuments;
	
	/** The {@link com.procom.filefly.SqliteController} is used as an API for {@link android.database.sqlite.SQLiteDatabase} */
	private SqliteController mSqliteController;

	
	/**
	 * Constructs an instance of {@link com.procom.filefly.DocumentListAdapter}.
	 * 
	 * @param context The {@link android.app.Activity} which hosts the a {@link android.widget.ListView} directly or indirectly
	 * in a {@link android.app.Fragment}
	 * @param documents The {@link java.util.List} of {@link com.procom.filefly.model.Document}s that back the items in
	 * the {@link android.widget.ListView}
	 * @author Peter Piech
	 */
	public DocumentListAdapter(Context context, SqliteController sqlcontroller, List<Document> documents)
	{
		mContext = context;
		mDocuments = documents;
		mSqliteController = sqlcontroller;
	}

	/**
	 * Returns the value of the field of the number of items
	 * 
	 * @return The number of items in the {@link java.util.List} that backs the {@link android.widget.ListView}
	 * @author Peter Piech
	 */
	@Override
	public int getCount()
	{
		return mDocuments.size();
	}

	/**
	 * Returns a reference to the {@link java.lang.Object} at the given index of the backing structure
	 * 
	 * @param index The index of the item in the {@link java.util.List} to return
	 * @return A reference to the {@link com.procom.filefly.model.Document} at the given index in the {@link java.util.List}
	 * that backs the {@link android.widget.ListView}
	 * @author Peter Piech
	 */
	@Override
	public Object getItem(int index)
	{
		return mDocuments.get(index);
	}

	/**
	 * Returns the Id of the item in the {@link java.util.List}
	 * 
	 * @param index The index of the item in the {@link java.util.List} to return the Id of
	 * @return The Id of the item at the given index in the {@link java.util.List}
	 * that backs the {@link android.widget.ListView}
	 * @author Peter Piech
	 */
	@Override
	public long getItemId(int index)
	{
		return index;
	}

	/**
	 * Inflates the view for the {@link com.procom.filefly.model.Document} at the given index
	 * in the {@link java.util.List} that backs the {@link android.widget.ListView}.
	 * 
	 * @param index The index of the {@link com.procom.filefly.model.Document} in the {@link java.util.List}
	 * @param convertView
	 * @param parent
	 * 
	 * @return The inflated {@link android.widget.View}
	 * @author Peter Piech, Andy Kakkaramadam
	 * 
	 */
	@Override
	public View getView(final int index, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.document_list_item, parent, false);
		
		TextView fileName = (TextView) itemView.findViewById(R.id.filename);
		TextView fullName = (TextView) itemView.findViewById(R.id.fullname);
		TextView date = (TextView) itemView.findViewById(R.id.date);
		fileName.setText(mDocuments.get(index).getFilename());
		fullName.setText(mDocuments.get(index).getOwnerFirstName() + ' ' + mDocuments.get(index).getOwnerLastName());
		date.setText(mDocuments.get(index).getDateTransferredString());
		
		itemView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				mContext.startActivity(FilesIntentHandler.openFile(mContext, mDocuments.get(index).getFilename())); // Opens the file using FilesIntentHandler, the position inside the array mDocuments.
			}
		});
		
		itemView.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View view)
			{
				mSqliteController.deleteDocument(mDocuments.get(index).getFilename()); // Call SQLite statement to delete the document.
				mDocuments.remove(index); // Remove the Document representation also
				DocumentListAdapter.this.notifyDataSetChanged(); // Send notification that the data set has been altered.
				return true;
			}
		});
		
		return itemView;
	}
	
	/**
	 * Updates the {@link #mDocuments} member field with the provided {@link java.util.List}
	 * 
	 * @author Peter Piech
	 */
	public void updateDocuments(List<Document> newDocs)
	{
		mDocuments = newDocs;
		notifyDataSetChanged();
	}
}