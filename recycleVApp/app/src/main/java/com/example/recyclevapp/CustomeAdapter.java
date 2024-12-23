package com.example.recyclevapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> implements Filterable {

    private ArrayList<DataModel> dataSet;
    private ArrayList<DataModel> dataSetFull; // A copy of the original dataset for filtering

    public CustomeAdapter(ArrayList<DataModel> dataSet) {
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet); // Create a copy of the original dataset
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewVersion = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        DataModel currentItem = dataSet.get(position);
        holder.textViewName.setText(currentItem.getName());
        holder.textViewVersion.setText(currentItem.getVersion());
        holder.imageView.setImageResource(currentItem.getImage());

        holder.itemView.setOnClickListener(v -> {
            // Show a Toast message when an item is clicked
            String name = currentItem.getName(); // Get the name of the clicked item
            Toast.makeText(v.getContext(), "Item clicked: " + name, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    // The filter logic to filter the dataset
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<DataModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                // If the query is empty, show the full list
                filteredList.addAll(dataSetFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                // Filter data based on name or version (you can modify the condition as needed)
                for (DataModel item : dataSetFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            // Return the filtered results
            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Update the dataset with the filtered list and notify adapter to update the UI
            dataSet.clear();
            if (filterResults.values != null) {
                dataSet.addAll((List) filterResults.values);
            }
            notifyDataSetChanged(); // Notify the adapter to refresh the view
        }
    };
}
