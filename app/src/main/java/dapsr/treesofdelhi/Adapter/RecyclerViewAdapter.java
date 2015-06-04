package dapsr.treesofdelhi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dapsr.treesofdelhi.R;

/**
 * Created by rajanmaurya on 30/4/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private String[] mDataSet;
    private int icons[];
    private Context con;


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
       // private final TextView textView;


        public ViewHolder(View v) {
            super(v);


            imageView = (ImageView) v.findViewById(R.id.image);
           // textView  = (TextView) v.findViewById(R.id.textView2);

        }

        public ImageView getImageView() {
            return imageView;
        }

        /*public TextView getTextView(){
            return textView;
        }*/


    }


    /**
     * Initialize the dataset of the Adapter.
     *  @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     * @param icon
     * @param context
     */
    public RecyclerViewAdapter(String[] dataSet, int[] icon, Context context) {
        mDataSet = dataSet;
        icons = icon;
        this.con = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a saptaaa view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyccleview_griditem, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        /*
        *
        * initializing the ImageView and TextView with data
        *
        * */
        viewHolder.getImageView().setImageResource(icons[position]);
       // viewHolder.getTextView().setText(mDataSet[position]);

    }

    @Override
    public int getItemCount() {
        return icons.length;
    }

}
