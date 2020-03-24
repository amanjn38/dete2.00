package com.example.e_commerce_part_2;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private CategoryAdapter categoryAdapter;

    /////////// Banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    //////////

    //////Strip ad
    private ImageView stripAdImage;
    private ConstraintLayout strip_ad_container;
    ////// Strip ad

    ////Horizontal Product Layout
    private TextView layoutTitle;
    private Button btnViewAll;
    private RecyclerView horizontalrecyclerView;
    /////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        categoryModels.add(new CategoryModel("link", "Home"));
        categoryModels.add(new CategoryModel("link", "Electronics"));
        categoryModels.add(new CategoryModel("link", "Appliances"));
        categoryModels.add(new CategoryModel("link", "Furniture"));

        categoryAdapter = new CategoryAdapter(categoryModels);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //// Banner Slider
        bannerSliderViewPager = view.findViewById(R.id.bannerViewPager);
        sliderModelList = new ArrayList<SliderModel>();

        //Infinite rotation  Copy first two banners and paste at the last and last two banners at the starting
        sliderModelList.add(new SliderModel(R.drawable.cart, "077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.purses_bags,"077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.emailgreen,"077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.email,"077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.cart, "077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.purses_bags, "077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.emailgreen, "077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.email, "077AE4"));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);
        bannerSliderViewPager.setAdapter(sliderAdapter);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if( state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };

        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
        //

        ////////Strip Ad
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        strip_ad_container = view.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.drawable.emailgreen);
        stripAdImage.setBackgroundColor(Color.parseColor("#000000"));
        ////////

        //////Horizontal
        layoutTitle = view.findViewById(R.id.txt1);
        horizontalrecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerView);
        btnViewAll = view.findViewById(R.id.btnViewAll);

        List<HorizontalProductModel> horizontalProductModels = new ArrayList<>();
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));
        horizontalProductModels.add(new HorizontalProductModel(R.drawable.email, "Redmi", "SnapDragon", "Rs.6999"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductModels);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        horizontalrecyclerView.setLayoutManager(linearLayoutManager1);
        horizontalrecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        /////////


        ////// GridView
        TextView gridLayoutTitle = view.findViewById(R.id.gridTitle);
        Button btnGridView = view.findViewById(R.id.btnViewAllGrid);
        GridView gridView = view.findViewById(R.id.grid_layout);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductModels));
        ////////
        return view;
    }
    ///Banner
    private void pageLooper(){
        if(currentPage == sliderModelList.size()-2)
        {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
        if(currentPage == 1)
        {
            currentPage = sliderModelList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
    }

    private void startBannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }
}
