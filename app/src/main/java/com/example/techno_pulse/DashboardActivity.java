package com.example.techno_pulse;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;
    private ViewPager2 viewPager;
    private int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        drawerLayout.setScrimColor(0x66000000);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(v -> {
        });

        List<NewsItem> sportsNews = Arrays.asList(
                new NewsItem(R.drawable.featured_news4, "Shining Bright at SLUSA  Colours Night..\uD83C\uDFC6 - S.S. Dahanayaka", "Nimantha Gunarathna","Congratulations to S. S. Dahanayake for bringing pride to the Faculty of Technology, University of Colombo with his outstanding performance in Elle!","April 1, 2025",""),
                new NewsItem(R.drawable.featured_news5, "Victory in Inter Faculty Championship Road Race Event", "Nimantha Gunarathn","A huge congratulations to all the participants from our faculty who represented us in the Inter-Faculty Road Race and came out victorious! \uD83C\uDFC1\uD83D\uDC4F\n" +
                        "\n" +
                        "Your dedication, effort, and hard work truly paid off, and it's amazing to see our faculty shine in such a competitive event. You’ve made us all proud, and this win is definitely something to celebrate. Keep pushing boundaries and achieving greatness—this is just the beginning!\n" +
                        "\n" +
                        "Well done, champs! \uD83D\uDCAA\uD83D\uDD25","May 18, 2025","caption"),

                new NewsItem(R.drawable.sport_news, "Sports News", "Author ","body","date","caption"),
                new NewsItem(R.drawable.sport_news, "Sports News", "Author ","body","date","caption"),
                new NewsItem(R.drawable.sport_news, "Sports News", "Author ","body","date","caption"),
                new NewsItem(R.drawable.sport_news, "Sports News", "Author ","body","date","caption"),
                new NewsItem(R.drawable.sport_news, "Sports News", "Author ","body","date","caption")

        );

        List<NewsItem> academicNews = Arrays.asList(
                new NewsItem(R.drawable.academic_news1, "Nature Talks 2025 In parallel with the Biomimicry Conference 2025", "Author","Nature Talks 2025\n" +
                        "In parallel with the Biomimicry Conference 2025\n" +
                        "The special webinar series\n" +
                        "exploring how nature’s intelligence can help us build a cleaner, smarter, and more sustainable future.\n" +
                        "\n" +
                        "✨ Online Sessions | \uD83C\uDF0D Global Experts | \uD83C\uDF93 Free E-Certificates | \uD83D\uDCBB Zoom\n" +
                        "\uD83D\uDD17 Ready to be inspired?\n" +
                        "\uD83D\uDC49 Click here to register – It’s free!\n" +
                        "\n" +
                        "\uD83C\uDF10 More: www.biomimicryconference.com\n" +
                        "Register now and be inspired.\uD83C\uDF31\n" +
                        "#NatureTalks2025 | #ICBS2025 | #InspiredByNature","May 24, 2025","caption"),
                new NewsItem(R.drawable.academic_news2, "New  Executive Board members of the Env Technology Society", "Author","Congratulations to Our New Leaders!\n" +
                        "\n" +
                        "We are proud to welcome the newly appointed Executive Board members of the Environmental Technology Society, Department of Environmental Technology.\n" +
                        "\n" +
                        "Your passion, vision, and commitment to environmental sustainability are truly inspiring. We look forward to a dynamic year ahead, filled with impactful initiatives and meaningful change.✨","June 4, 2025","caption"),
                new NewsItem(R.drawable.academic_news, "Academic News", "Author","body","date","caption"),
                new NewsItem(R.drawable.academic_news, "Academic News", "Author","body","date","caption"),
                new NewsItem(R.drawable.academic_news, "Academic News", "Author","body","date","caption"),
                new NewsItem(R.drawable.academic_news, "Academic News", "Author","body","date","caption"),
                new NewsItem(R.drawable.academic_news, "Academic News", "Author","body","date","caption")
                );

        List<NewsItem> eventsNews = Arrays.asList(
                new NewsItem(R.drawable.event_news1, "අයිස්ක්\u200Dරීම් දන්සල", "Nimantha Pramodya","කොළඹ විශ්වවිද්\u200Dයාලීය තාක්ෂණ පීඨ ශිෂ්\u200Dය සංගමය මගින් පොසොන් පොහොය නිමිතිකොට ගෙන ප්\u200Dරථම වරට සංවිධානය කරනු ලබන,\n" +
                        "\n" +
                        "අයිස්ක්\u200Dරීම් දන්සල,\n" +
                        "\n" +
                        "\uD83D\uDDD3\uFE0F ජුනි මස 10 වන දින\n" +
                        "\uD83D\uDD51 සවස 2.00 සිට\n" +
                        "\uD83D\uDCCC පාසල් හංදිය පරිශ්\u200Dරයේදී\n" +
                        "පැවැත් වේ.\n" +
                        "\n" +
                        "ඔබ සැමට ආරාධනා..............\n" +
                        "\n" +
                        "තාක්ෂණ පීඨ ශිෂ්\u200Dය සංගමය \n" +
                        "කොළඹ විශ්වවිද්\u200Dයාලය…","June 8, 2025","caption"),
                new NewsItem(R.drawable.event_news2, "Purawara Wasanthaya New Year Festival 2025 Celebrated with Faculty of Technology", "Isuru Udayanga","Purawara Wasanthaya – Aurudu Uthsawaya 2025\u2028Held on April 26th at the Faculty of Technology, University of Colombo\n" +
                        "Purawara Wasanthaya – Aurudu Uthsawaya 2025 went down in style on the 26th of April at the Faculty of Technology premises, University of Colombo. The event, organized by the Students Welfare Society, was all about bringing together the Uni fam to celebrate Sinhala and Tamil New Year in the most vibrant way possible.\n" +
                        "The whole place was buzzing with energy as students, lecturers, and guests joined in to enjoy a day full of traditional games, music, dance, and of course, some epic Aurudu food! Highlights of the day included fun events like kotta pora (pillow fighting), tug-of-war, and kana mutti bindeema, which had everyone cheering. And let’s not forget the iconic Aurudu Kumarand Kumariya contests","April 26, 2025","caption"),
                new NewsItem(R.drawable.event_news, "Event News", "Author","body","date","caption"),
                new NewsItem(R.drawable.event_news, "Event News", "Author","body","date","caption"),
                new NewsItem(R.drawable.event_news, "Event News", "Author","body","date","caption"),
                new NewsItem(R.drawable.event_news, "Event News", "Author","body","date","caption"),
                new NewsItem(R.drawable.event_news, "Event News", "Author","body","date","caption")
                );

        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        NewsAdapter newsAdapter = new NewsAdapter(this, new ArrayList<>(sportsNews));
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(newsAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        newsAdapter.updateNews(sportsNews);
                        break;
                    case 1:
                        newsAdapter.updateNews(academicNews);
                        break;
                    case 2:
                        newsAdapter.updateNews(eventsNews);
                        break;
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager2 viewPager = findViewById(R.id.featuredNewsPager);

        List<FeaturedNews> newsList = new ArrayList<>();
        newsList.add(new FeaturedNews(R.drawable.banner1, "පුරවර වසන්තය 2025 organized by Welfare Students Welfare Society, Faculty of Technology, University of Colombo was successfully held at Faculty Premises, leaving a meaningful impact on all who participated. ✨", "Isuru Udayanga"));
        newsList.add(new FeaturedNews(R.drawable.banner2, "නැණ යාත්\u200Dරා 1st Edition - Arduino & Robotics Workshop organized by Society of Instrumentation and Automation Technology", "Gayan Rajapaksha"));
        newsList.add(new FeaturedNews(R.drawable.banner3, "Annual General Meeting (AGM) 25/26 Students Welfare Society organized by Students Welfare Society of Faculty of Technology University of Colombo", "Anura Kumara"));

        FeaturedNewsPagerAdapter adapter = new FeaturedNewsPagerAdapter(newsList);
        viewPager.setAdapter(adapter);

        tabLayout.post(() -> {
            int marginDp = 8;
            float density = tabLayout.getContext().getResources().getDisplayMetrics().density;
            int left = (int) (density * marginDp);
            int right = (int) (density * marginDp);

            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                params.setMargins(left, 0, right, 0);
                tabView.setLayoutParams(params);
            }
        });

        NUM_PAGES = newsList.size();
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (viewPager.getCurrentItem() + 1) % NUM_PAGES;
                viewPager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 4000);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 4000);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 4000);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_user_details) {
            startActivity(new Intent(this, UserDetailsActivity.class));
        } else if (id == R.id.nav_developer_details) {
            startActivity(new Intent(this, DeveloperDetailsActivity.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_sign_out, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);

            if (dialog.getWindow() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideDownAnimation;
            }

            Button btnOk = dialogView.findViewById(R.id.btn_ok);
            Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

            btnOk.setOnClickListener(view -> {

                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                dialog.dismiss();
            });

            btnCancel.setOnClickListener(view -> dialog.dismiss());

            dialog.show();

            if (dialog.getWindow() != null) {
                int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.9);
                dialog.getWindow().setLayout(width, android.view.WindowManager.LayoutParams.WRAP_CONTENT);}
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
