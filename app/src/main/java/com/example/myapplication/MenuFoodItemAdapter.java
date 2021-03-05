package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MenuFoodItemAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private ArrayList<MenuFoodItem> originalList;
    private ArrayList<MenuFoodItem> displayedList;

    public MenuFoodItemAdapter(Context context, int layout, ArrayList<MenuFoodItem> menuFoodItems) {
        this.context = context;
        this.layout = layout;
        this.originalList = menuFoodItems;
        this.displayedList = menuFoodItems;

    }

    @Override
    public int getCount() {
        return displayedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    ///Lọc dữ liệu tìm kiếm
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                displayedList = (ArrayList<MenuFoodItem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<MenuFoodItem> FilteredArrList = new ArrayList<MenuFoodItem>();

                if (originalList == null) {
                    originalList = new ArrayList<MenuFoodItem>(displayedList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalList.size();
                    results.values = originalList;
                } else {
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < originalList.size(); i++) {
                        String data = originalList.get(i).getDish_name();
                        data = VNCharacterUtils.removeAccent(data);
                      //  if (data.toLowerCase().startsWith(constraint.toString())) {
                        if (data.toLowerCase().contains(VNCharacterUtils.removeAccent(constraint.toString()))) {
                            FilteredArrList.add(new MenuFoodItem(originalList.get(i).getDish_id(), originalList.get(i).getDish_name(),originalList.get(i).getGroup_id(), originalList.get(i).getPrice(), originalList.get(i).getImage()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    /// tạo ra view holder để ko phải ánh xạ lại những items đã lướt qua khi mình lướt lại
    private class ViewHolder{
        ImageView image;
        TextView name;
        TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewNameMenuFoodItem);
            holder.price = (TextView) convertView.findViewById(R.id.textViewPriceMenuFoodItem);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewMenuFoodItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MenuFoodItem menu = displayedList.get(position); // lay tung cai mot ra
        holder.name.setText(menu.getDish_name());
        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = String.valueOf(df.format(menu.getPrice()));
        holder.price.setText(priceString+"đ");
       byte[] hinhanh = menu.getImage();
        // muon lay hinh anh ra thi phai chuyen tu byte[] sang bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        holder.image.setImageBitmap(bitmap);

//       holder.image.setImageResource(menu.getHinhAnh());

        return convertView;
    }
}
